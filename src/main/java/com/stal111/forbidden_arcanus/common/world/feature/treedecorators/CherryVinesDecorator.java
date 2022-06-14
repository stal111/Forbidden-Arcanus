package com.stal111.forbidden_arcanus.common.world.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;

/**
 * Cherry Vines Decorator <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.treedecorators.CherryVinesDecorator
 *
 * @author stal111
 * @version 1.19 - 2.1.0
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
    public void place(@Nonnull Context context) {
        RandomSource random = context.random();
        LevelSimulatedReader level = context.level();

        context.leaves().stream()
                .map(BlockPos::below)
                .filter(pos -> context.isAir(pos) && random.nextDouble() < VINES_CHANCE)
                .forEach(pos -> {
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        if (level.isStateAtPosition(pos.relative(direction), state -> state.is(ModBlocks.CHERRY_FLOWER_VINES.get()))) {
                            return;
                        }
                    }
                    context.setBlock(pos, ModBlocks.CHERRY_FLOWER_VINES.get().defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, 21, 25)));
                });
    }
}
