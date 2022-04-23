package com.stal111.forbidden_arcanus.common.world.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Cherry Vines Decorator <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.treedecorators.CherryVinesDecorator
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-23
 */
public class CherryVinesDecorator extends TreeDecorator {

    public static final CherryVinesDecorator INSTANCE = new CherryVinesDecorator();

    public static final Codec<CherryVinesDecorator> CODEC = Codec.unit(() -> {
        return CherryVinesDecorator.INSTANCE;
    });

    public static final double VINES_CHANCE = 0.11D;

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.CHERRY_VINES_DECORATOR.get();
    }

    @Override
    public void place(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull Random random, @Nonnull List<BlockPos> logPositions, @Nonnull List<BlockPos> leafPositions) {
        leafPositions.stream()
                .map(BlockPos::below)
                .filter(pos -> Feature.isAir(level, pos) && random.nextDouble() < VINES_CHANCE)
                .forEach(pos -> {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.isStateAtPosition(pos.relative(direction), state -> state.is(ModBlocks.CHERRY_FLOWER_VINES.get()))) {
                            return;
                        }
                    }
                    blockSetter.accept(pos, ModBlocks.CHERRY_FLOWER_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, 21, 25)));
                });
    }
}
