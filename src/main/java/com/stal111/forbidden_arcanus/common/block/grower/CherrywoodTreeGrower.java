package com.stal111.forbidden_arcanus.common.block.grower;

import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

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
    protected Holder<ConfiguredFeature<TreeConfiguration, ?> > getConfiguredFeature(@Nonnull RandomSource random, boolean largeHive) {
        return random.nextDouble() < 0.3D ? ModConfiguredFeatures.LARGE_CHERRYWOOD : ModConfiguredFeatures.SMALL_CHERRYWOOD;
    }
}
