package com.stal111.forbidden_arcanus.common.block.grower;

import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * Mysterywood Tree Grower <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.grower.MysterywoodTreeGrower
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-23
 */
public class MysterywoodTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nonnull Random random, boolean largeHive) {
        return ModConfiguredFeatures.MYSTERYWOOD;
    }
}
