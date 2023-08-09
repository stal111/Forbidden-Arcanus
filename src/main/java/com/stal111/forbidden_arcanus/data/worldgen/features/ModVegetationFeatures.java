package com.stal111.forbidden_arcanus.data.worldgen.features;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.List;

/**
 * Mod Vegetation Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures
 *
 * @author stal111
 * @since 2022-04-24
 */
public class ModVegetationFeatures extends DatapackRegistryClass<ConfiguredFeature<?, ?>> {

    public static final DatapackRegistryHelper<ConfiguredFeature<?, ?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_TREES_PLAINS = HELPER.createKey("cherry_trees_plains");


    public ModVegetationFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE);

        FeatureUtils.register(context, CHERRY_TREES_PLAINS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatureRegistry.getOrThrow(ModTreePlacements.SMALL_CHERRY_CHECKED), 0.2F)), placedFeatureRegistry.getOrThrow(ModTreePlacements.LARGE_CHERRY_CHECKED)));
    }
}
