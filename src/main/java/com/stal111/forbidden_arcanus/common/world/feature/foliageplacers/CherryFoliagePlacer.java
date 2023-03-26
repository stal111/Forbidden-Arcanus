package com.stal111.forbidden_arcanus.common.world.feature.foliageplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.ThinLogBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModFoliagePlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Cherry Foliage Placer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.foliageplacers.CherryFoliagePlacer
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-04-07
 */
public class CherryFoliagePlacer extends FoliagePlacer {

    public static final Codec<CherryFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return foliagePlacerParts(instance).apply(instance, CherryFoliagePlacer::new);
    });

    private static final List<Pair<Integer, Integer>> LAYOUT = ImmutableList.of(Pair.of(0, 3), Pair.of(-2, 4), Pair.of(-2, 4), Pair.of(-2, 4), Pair.of(0, 3));
    private static final List<Pair<Integer, Integer>> LAYOUT_TOP = ImmutableList.of(Pair.of(0, 1), Pair.of(-1, 2), Pair.of(0, 1));

    public CherryFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Nonnull
    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.CHERRY_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(@Nonnull LevelSimulatedReader level, @Nonnull FoliageSetter blockSetter, @Nonnull RandomSource random, @Nonnull TreeConfiguration config, int maxFreeTreeHeight, @Nonnull FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos pos = attachment.pos();
        List<Direction> directions = new ArrayList<>();
        Block trunk = ModBlocks.THIN_CHERRY_LOG.get();

        for (Direction direction : Direction.Plane.HORIZONTAL) {

            if (level.isStateAtPosition(pos.below().relative(direction), state -> state.is(trunk))) {
                directions.add(direction);
            }
        }

        if (directions.size() == 1 && level.isStateAtPosition(pos.below(2), state -> state.is(trunk) && state.getValue(ThinLogBlock.AXIS) == Direction.Axis.Y)) {
            BlockPos.MutableBlockPos mutable = pos.mutable();
            Direction direction = directions.get(0).getOpposite();

            mutable.move(Direction.DOWN).move(direction);
            tryPlaceLeaf(level, blockSetter, random, config, mutable);

            mutable.move(direction);
            tryPlaceLeaf(level, blockSetter, random, config, mutable);

            mutable.move(direction.getOpposite(), 2).move(direction.getClockWise());
            tryPlaceLeaf(level, blockSetter, random, config, mutable);

            mutable.move(direction);
            tryPlaceLeaf(level, blockSetter, random, config, mutable);

            mutable.move(direction.getOpposite()).move(direction.getCounterClockWise(), 2);
            tryPlaceLeaf(level, blockSetter, random, config, mutable);

            mutable.move(direction);
            tryPlaceLeaf(level, blockSetter, random, config, mutable);
        } else {
            BlockPos.MutableBlockPos mutable = pos.mutable();

            Direction direction = null;
            Direction.Axis axis;

            if (level.isStateAtPosition(mutable.move(Direction.DOWN, 2), state -> state.is(trunk) && state.getValue(ThinLogBlock.AXIS) == Direction.Axis.X)) {
                axis = Direction.Axis.X;
            } else {
                axis = Direction.Axis.Z;
            }

            for (Direction.AxisDirection axisDirection : Direction.AxisDirection.values()) {
                Direction offsetDirection = Direction.fromAxisAndDirection(axis, axisDirection);

                if (level.isStateAtPosition(mutable.move(offsetDirection), state -> state.is(trunk))) {
                    direction = offsetDirection.getOpposite();
                    break;
                }
            }

            if (direction != null) {
                this.placeFromLayout(level, blockSetter, random, config, LAYOUT, pos, direction);
                this.placeFromLayout(level, blockSetter, random, config, LAYOUT_TOP, pos.above(), direction);

                for (Direction direction1 : Direction.Plane.HORIZONTAL) {
                    mutable.set(pos.getX(), pos.getY() - 1, pos.getZ());

                    if (direction1 != direction.getOpposite()) {
                        tryPlaceLeaf(level, blockSetter, random, config, mutable.move(direction1));
                    }
                }
            }
        }
    }

    private void placeFromLayout(@Nonnull LevelSimulatedReader level, @Nonnull FoliageSetter blockSetter, @Nonnull RandomSource random, @Nonnull TreeConfiguration config, List<Pair<Integer, Integer>> layout, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        mutable.move(direction.getClockWise(), (layout.size() - 1) / 2);

        for (int row = 0; row < layout.size(); row++) {
            Pair<Integer, Integer> pair = layout.get(row);

            for (int i = pair.getFirst(); i < 1 + pair.getSecond(); i++) {
                if ((i == pair.getFirst() || i == 1 + pair.getSecond()) && random.nextBoolean() && !this.isMiddleRow(layout, row)) {
                    continue;
                }
                tryPlaceLeaf(level, blockSetter, random, config, mutable.relative(direction, i));
            }

            mutable.move(direction.getCounterClockWise());
        }
    }

    private boolean isMiddleRow(List<Pair<Integer, Integer>> layout, int i) {
        return layout.size() / 2 == i;
    }

    @Override
    public int foliageHeight(@Nonnull RandomSource random, int pHeight, @Nonnull TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(@Nonnull RandomSource random, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
