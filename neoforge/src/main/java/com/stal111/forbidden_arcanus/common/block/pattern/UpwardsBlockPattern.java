package com.stal111.forbidden_arcanus.common.block.pattern;

import com.google.common.base.Predicates;
import com.google.common.cache.LoadingCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Upwards Block Pattern <br>
 *
 * <p>
 * A special implementation of Block Pattern that only checks for the pattern in the UP {@link Direction}.
 * Precomputes per-orientation cell offsets and supports anchor coordinates and rotational symmetry
 * to avoid brute-force scanning.
 * </p>
 *
 * @author stal111
 * @since 2022-05-22
 */
public class UpwardsBlockPattern extends BlockPattern {

    private static final Direction[] SYMMETRIC_DIRECTIONS = {Direction.NORTH};
    private static final Direction[] HORIZONTAL_DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private final Predicate<BlockInWorld>[][][] pattern;
    @Nullable
    private final Predicate<BlockState>[][][] statePattern;
    private final boolean symmetric;
    private final Map<Direction, CellCheck[]> cellsByDirection;
    @Nullable
    private final List<CandidateOffset> candidates;

    public UpwardsBlockPattern(Predicate<BlockInWorld>[][][] pattern) {
        this(pattern, null, false, null);
    }

    private UpwardsBlockPattern(Predicate<BlockInWorld>[][][] pattern, @Nullable Predicate<BlockState>[][][] statePattern, boolean symmetric, @Nullable List<Vec3i> anchors) {
        super(pattern);
        this.pattern = pattern;
        this.statePattern = statePattern;
        this.symmetric = symmetric;
        this.cellsByDirection = buildCellsByDirection(pattern, statePattern, symmetric ? SYMMETRIC_DIRECTIONS : HORIZONTAL_DIRECTIONS);
        this.candidates = anchors != null ? buildCandidates(anchors, symmetric) : null;
    }

    public static UpwardsBlockPattern of(BlockPatternBuilder blockPatternBuilder) {
        return new UpwardsBlockPattern(blockPatternBuilder.createPattern(), null, false, null);
    }

    public static StateBuilder builder() {
        return new StateBuilder();
    }

    public static Builder builder(BlockPatternBuilder blockPatternBuilder) {
        return new Builder(blockPatternBuilder.createPattern());
    }

    private static Map<Direction, CellCheck[]> buildCellsByDirection(
            Predicate<BlockInWorld>[][][] pattern,
            @Nullable Predicate<BlockState>[][][] statePattern,
            Direction[] directions
    ) {
        int depth = pattern.length;
        int height = pattern[0].length;
        int width = pattern[0][0].length;
        int totalCells = width * height * depth;

        Map<Direction, CellCheck[]> map = new EnumMap<>(Direction.class);
        for (Direction direction : directions) {
            List<CellCheck> cells = new ArrayList<>(totalCells);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < depth; z++) {
                        Predicate<BlockInWorld> predicate = pattern[z][y][x];
                        if (predicate.equals(Predicates.alwaysTrue())) {
                            continue;
                        }

                        BlockPos offset = translateAndRotate(BlockPos.ZERO, direction, Direction.UP, x, y, z);
                        Predicate<BlockState> statePredicate = statePattern != null ? statePattern[z][y][x] : null;
                        cells.add(new CellCheck(offset.getX(), offset.getY(), offset.getZ(), predicate, statePredicate));
                    }
                }
            }

            map.put(direction, cells.toArray(CellCheck[]::new));
        }

        return map;
    }

    private static List<CandidateOffset> buildCandidates(List<Vec3i> anchors, boolean symmetric) {
        Direction[] directions = symmetric ? SYMMETRIC_DIRECTIONS : HORIZONTAL_DIRECTIONS;
        List<CandidateOffset> result = new ArrayList<>(anchors.size() * directions.length);

        for (Vec3i anchor : anchors) {
            for (Direction direction : directions) {
                BlockPos offset = translateAndRotate(BlockPos.ZERO, direction, Direction.UP, anchor.getX(), anchor.getY(), anchor.getZ());
                result.add(new CandidateOffset(
                        anchor.getX(),
                        anchor.getY(),
                        anchor.getZ(),
                        direction,
                        new Vec3i(-offset.getX(), -offset.getY(), -offset.getZ())
                ));
            }
        }

        return List.copyOf(result);
    }

    public boolean matches(@Nonnull LevelReader level, @Nonnull BlockPos pos) {
        if (this.candidates != null && this.candidates.size() == 1) {
            return this.matchesSingleCandidate(level, pos, this.candidates.getFirst());
        }

        return this.findWithCache(pos, createLevelCache(level, false)) != null;
    }

    @Nullable
    @Override
    public BlockPatternMatch find(@Nonnull LevelReader level, @Nonnull BlockPos pos) {
        if (this.candidates != null && this.candidates.size() == 1) {
            CandidateOffset candidate = this.candidates.getFirst();
            if (!this.matchesSingleCandidate(level, pos, candidate)) {
                return null;
            }

            BlockPos frontTopLeft = pos.offset(candidate.frontTopLeftOffset());
            return new BlockPatternMatch(frontTopLeft, candidate.direction(), Direction.UP, createLevelCache(level, false), this.getWidth(), this.getHeight(), this.getDepth());
        }

        LoadingCache<BlockPos, BlockInWorld> cache = createLevelCache(level, false);
        MatchHit hit = this.findWithCache(pos, cache);

        return hit == null ? null : new BlockPatternMatch(hit.frontTopLeft(), hit.direction(), Direction.UP, cache, this.getWidth(), this.getHeight(), this.getDepth());
    }

    @Nullable
    private MatchHit findWithCache(BlockPos pos, LoadingCache<BlockPos, BlockInWorld> cache) {
        if (this.candidates != null) {
            BlockInWorld clicked = cache.getUnchecked(pos);

            for (CandidateOffset candidate : this.candidates) {
                if (!this.pattern[candidate.z()][candidate.y()][candidate.x()].test(clicked)) {
                    continue;
                }

                BlockPos frontTopLeft = pos.offset(candidate.frontTopLeftOffset());
                if (this.matchesWithCache(frontTopLeft, candidate.direction(), cache)) {
                    return new MatchHit(frontTopLeft, candidate.direction());
                }
            }

            return null;
        }

        int i = Math.max(this.getWidth(), this.getDepth());
        Direction[] directions = this.symmetric ? SYMMETRIC_DIRECTIONS : HORIZONTAL_DIRECTIONS;

        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-i + 1, 0, -i + 1), pos.offset(i - 1, this.getHeight() - 1, i - 1))) {
            for (Direction direction : directions) {
                if (this.matchesWithCache(blockPos, direction, cache)) {
                    return new MatchHit(blockPos.immutable(), direction);
                }
            }
        }

        return null;
    }

    private boolean matchesSingleCandidate(LevelReader level, BlockPos pos, CandidateOffset candidate) {
        if (this.statePattern != null) {
            if (!this.statePattern[candidate.z()][candidate.y()][candidate.x()].test(level.getBlockState(pos))) {
                return false;
            }
        } else if (!this.pattern[candidate.z()][candidate.y()][candidate.x()].test(new BlockInWorld(level, pos, false))) {
            return false;
        }

        BlockPos frontTopLeft = pos.offset(candidate.frontTopLeftOffset());
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (CellCheck cell : this.cellsByDirection.get(candidate.direction())) {
            mutablePos.setWithOffset(frontTopLeft, cell.dx(), cell.dy(), cell.dz());

            if (cell.statePredicate() != null) {
                if (!cell.statePredicate().test(level.getBlockState(mutablePos))) {
                    return false;
                }
            } else if (!cell.predicate().test(new BlockInWorld(level, mutablePos, false))) {
                return false;
            }
        }

        return true;
    }

    private boolean matchesWithCache(BlockPos frontTopLeft, Direction forwards, LoadingCache<BlockPos, BlockInWorld> cache) {
        for (CellCheck cell : this.cellsByDirection.get(forwards)) {
            if (!cell.predicate().test(cache.getUnchecked(frontTopLeft.offset(cell.dx(), cell.dy(), cell.dz())))) {
                return false;
            }
        }

        return true;
    }

    private record MatchHit(BlockPos frontTopLeft, Direction direction) {
    }

    private record CellCheck(int dx, int dy, int dz, Predicate<BlockInWorld> predicate,
                             @Nullable Predicate<BlockState> statePredicate) {
    }

    private record CandidateOffset(int x, int y, int z, Direction direction, Vec3i frontTopLeftOffset) {
    }

    public static final class Builder {

        private final Predicate<BlockInWorld>[][][] pattern;
        private final List<Vec3i> anchors = new ArrayList<>();
        private boolean symmetric;

        private Builder(Predicate<BlockInWorld>[][][] pattern) {
            this.pattern = pattern;
        }

        public Builder anchor(int x, int y, int z) {
            this.anchors.add(new Vec3i(x, y, z));
            return this;
        }

        public Builder symmetric() {
            this.symmetric = true;
            return this;
        }

        public UpwardsBlockPattern build() {
            return new UpwardsBlockPattern(this.pattern, null, this.symmetric, this.anchors.isEmpty() ? null : this.anchors);
        }
    }

    public static final class StateBuilder {

        private final List<String[]> pattern = new ArrayList<>();
        private final Map<Character, Predicate<BlockState>> lookup = new HashMap<>();
        private final List<Vec3i> anchors = new ArrayList<>();
        private boolean symmetric;
        private int height;
        private int width;

        private StateBuilder() {
            this.lookup.put(' ', Predicates.alwaysTrue());
        }

        public StateBuilder aisle(String... aisle) {
            if (aisle.length == 0 || aisle[0].isEmpty()) {
                throw new IllegalArgumentException("Empty pattern for aisle");
            }

            if (this.pattern.isEmpty()) {
                this.height = aisle.length;
                this.width = aisle[0].length();
            } else if (aisle.length != this.height) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + aisle.length);
            }

            for (String row : aisle) {
                if (row.length() != this.width) {
                    throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.width + ", found one with " + row.length() + ")");
                }
            }

            this.pattern.add(aisle);
            return this;
        }

        public StateBuilder where(char symbol, Predicate<BlockState> predicate) {
            this.lookup.put(symbol, predicate);
            return this;
        }

        public StateBuilder anchor(int x, int y, int z) {
            this.anchors.add(new Vec3i(x, y, z));
            return this;
        }

        public StateBuilder symmetric() {
            this.symmetric = true;
            return this;
        }

        @SuppressWarnings("unchecked")
        public UpwardsBlockPattern build() {
            int depth = this.pattern.size();
            Predicate<BlockInWorld>[][][] blockPattern = new Predicate[depth][this.height][this.width];
            Predicate<BlockState>[][][] statePattern = new Predicate[depth][this.height][this.width];

            for (int z = 0; z < depth; z++) {
                for (int y = 0; y < this.height; y++) {
                    for (int x = 0; x < this.width; x++) {
                        char symbol = this.pattern.get(z)[y].charAt(x);
                        Predicate<BlockState> statePredicate = this.lookup.get(symbol);

                        if (statePredicate == null) {
                            throw new IllegalStateException("Predicate for character '" + symbol + "' is missing");
                        }

                        statePattern[z][y][x] = statePredicate;
                        blockPattern[z][y][x] = statePredicate.equals(Predicates.alwaysTrue()) ? Predicates.alwaysTrue() : BlockInWorld.hasState(statePredicate);
                    }
                }
            }

            return new UpwardsBlockPattern(blockPattern, statePattern, this.symmetric, this.anchors.isEmpty() ? null : this.anchors);
        }
    }
}
