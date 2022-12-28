package com.stal111.forbidden_arcanus.common.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mysterywood Tree Grower <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.grower.MysterywoodTreeGrower
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-23
 */
public class MysterywoodTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
        //TODO
        return null;
        //return ModConfiguredFeatures.AURUM.getHolder().get();
    }
}
