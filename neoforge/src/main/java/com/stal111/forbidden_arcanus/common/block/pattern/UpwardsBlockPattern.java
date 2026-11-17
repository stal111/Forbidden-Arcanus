package com.stal111.forbidden_arcanus.common.block.pattern;

import com.google.common.base.Predicates;
import com.google.common.cache.LoadingCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
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
    private final boolean symmetric;
    private final Map<Direction, CellCheck[]> cellsByDirection;
    @Nullable
    private final List<CandidateOffset> candidates;

    public UpwardsBlockPattern(Predicate<BlockInWorld>[][][] pattern) {
        this(pattern, false, null);
    }

    private UpwardsBlockPattern(Predicate<BlockInWorld>[][][] pattern, boolean symmetric, @Nullable List<Vec3i> anchors) {
        super(pattern);
        this.pattern = pattern;
        this.symmetric = symmetric;
        this.cellsByDirection = buildCellsByDirection(pattern, symmetric ? SYMMETRIC_DIRECTIONS : HORIZONTAL_DIRECTIONS);
        this.candidates = anchors != null ? buildCandidates(anchors, symmetric) : null;
    }

    public static UpwardsBlockPattern of(BlockPatternBuilder blockPatternBuilder) {
        return new UpwardsBlockPattern(blockPatternBuilder.createPattern());
    }

    public static Builder builder(BlockPatternBuilder blockPatternBuilder) {
        return new Builder(blockPatternBuilder.createPattern());
    }

    private static Map<Direction, CellCheck[]> buildCellsByDirection(Predicate<BlockInWorld>[][][] pattern, Direction[] directions) {
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
                        cells.add(new CellCheck(offset.getX(), offset.getY(), offset.getZ(), predicate));
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

    @Nullable
    @Override
    public BlockPatternMatch find(@Nonnull LevelReader level, @Nonnull BlockPos pos) {
        if (this.candidates != null && this.candidates.size() == 1) {
            return this.findSingleCandidate(level, pos, this.candidates.getFirst());
        }

        LoadingCache<BlockPos, BlockInWorld> cache = createLevelCache(level, false);

        if (this.candidates != null) {
            BlockInWorld clicked = cache.getUnchecked(pos);

            for (CandidateOffset candidate : this.candidates) {
                if (!this.pattern[candidate.z()][candidate.y()][candidate.x()].test(clicked)) {
                    continue;
                }

                BlockPos frontTopLeft = pos.offset(candidate.frontTopLeftOffset());
                BlockPatternMatch patternMatch = this.matchesWithCache(frontTopLeft, candidate.direction(), cache);

                if (patternMatch != null) {
                    return patternMatch;
                }
            }

            return null;
        }

        int i = Math.max(this.getWidth(), this.getDepth());
        Direction[] directions = this.symmetric ? SYMMETRIC_DIRECTIONS : HORIZONTAL_DIRECTIONS;

        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-i + 1, 0, -i + 1), pos.offset(i - 1, this.getHeight() - 1, i - 1))) {
            for (Direction direction : directions) {
                BlockPatternMatch patternMatch = this.matchesWithCache(blockPos, direction, cache);

                if (patternMatch != null) {
                    return patternMatch;
                }
            }
        }

        return null;
    }

    @Nullable
    private BlockPatternMatch findSingleCandidate(LevelReader level, BlockPos pos, CandidateOffset candidate) {
        if (!this.pattern[candidate.z()][candidate.y()][candidate.x()].test(new BlockInWorld(level, pos, false))) {
            return null;
        }

        BlockPos frontTopLeft = pos.offset(candidate.frontTopLeftOffset());
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (CellCheck cell : this.cellsByDirection.get(candidate.direction())) {
            mutablePos.setWithOffset(frontTopLeft, cell.dx(), cell.dy(), cell.dz());

            if (!cell.predicate().test(new BlockInWorld(level, mutablePos, false))) {
                return null;
            }
        }

        return new BlockPatternMatch(frontTopLeft, candidate.direction(), Direction.UP, createLevelCache(level, false), this.getWidth(), this.getHeight(), this.getDepth());
    }

    @Nullable
    private BlockPatternMatch matchesWithCache(BlockPos frontTopLeft, Direction forwards, LoadingCache<BlockPos, BlockInWorld> cache) {
        for (CellCheck cell : this.cellsByDirection.get(forwards)) {
            if (!cell.predicate().test(cache.getUnchecked(frontTopLeft.offset(cell.dx(), cell.dy(), cell.dz())))) {
                return null;
            }
        }

        return new BlockPatternMatch(frontTopLeft, forwards, Direction.UP, cache, this.getWidth(), this.getHeight(), this.getDepth());
    }

    private record CellCheck(int dx, int dy, int dz, Predicate<BlockInWorld> predicate) {
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
            return new UpwardsBlockPattern(this.pattern, this.symmetric, this.anchors.isEmpty() ? null : this.anchors);
        }
    }
}
