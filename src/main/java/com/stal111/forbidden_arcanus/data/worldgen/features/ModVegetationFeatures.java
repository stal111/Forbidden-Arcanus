package com.stal111.forbidden_arcanus.data.worldgen.features;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

import java.util.List;

/**
 * Mod Vegetation Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-24
 */
public class ModVegetationFeatures {

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CHERRY_TREES_PLAINS = register("cherry_trees_plains", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(ModTreePlacements.SMALL_CHERRY_CHECKED, 0.2F)), ModTreePlacements.LARGE_CHERRY_CHECKED));

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC configuration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString(), new ConfiguredFeature<>(feature, configuration));

    }
}
