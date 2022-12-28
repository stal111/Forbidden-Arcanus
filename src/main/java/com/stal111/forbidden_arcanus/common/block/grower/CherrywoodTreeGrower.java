package com.stal111.forbidden_arcanus.common.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Cherrywood Tree Grower <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.grower.CherrywoodTreeGrower
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-23
 */
public class CherrywoodTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
        //TODO
        return null;
       // return random.nextDouble() < 0.3D ? ModConfiguredFeatures.LARGE_CHERRY.getHolder().get() : ModConfiguredFeatures.SMALL_CHERRY.getHolder().get();
    }
}
