package com.stal111.forbidden_arcanus.common.world.feature.treedecorators;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import javax.annotation.Nonnull;

/**
 * Leaf Carpet Decorator <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.treedecorators.LeafCarpetDecorator
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-04-11
 */
public class LeafCarpetDecorator extends TreeDecorator {

    public static final Codec<LeafCarpetDecorator> CODEC = BlockStateProvider.CODEC.fieldOf("provider").xmap(LeafCarpetDecorator::new, (decorator) -> {
        return decorator.provider;
    }).codec();

    private final BlockStateProvider provider;

    public LeafCarpetDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    @Nonnull
    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecorators.LEAF_CARPET_DECORATOR.get();
    }

    @Override
    public void place(@Nonnull Context context) {
        RandomSource random = context.random();
        LevelSimulatedReader level = context.level();

        context.leaves().forEach(pos -> {
            if (random.nextDouble() < 0.1D) {
                BlockPos groundPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);

                if (level.isStateAtPosition(groundPos, state -> state.getMaterial().isReplaceable())
                        && !level.isStateAtPosition(groundPos.below(), state -> state.is(provider.getState(random, groundPos).getBlock()))
                        && !context.logs().contains(groundPos.below())) {
                    context.setBlock(groundPos, provider.getState(random, groundPos));
                }
            }
        });
    }
}
