package com.stal111.forbidden_arcanus.common.block.grower;

import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Cherrywood Tree Grower <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.grower.CherrywoodTreeGrower
 *
 * @author stal111
 * @since 2021-11-23
 */
public class CherrywoodTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean largeHive) {
        return random.nextDouble() < 0.3D ? ModConfiguredFeatures.LARGE_CHERRY : ModConfiguredFeatures.SMALL_CHERRY;
    }
}
