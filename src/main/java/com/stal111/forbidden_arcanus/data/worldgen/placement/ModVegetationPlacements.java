package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * Mod Vegetation Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements
 *
 * @author stal111
 * @since 2021-12-29
 */
public class ModVegetationPlacements extends DatapackRegistryClass<PlacedFeature> {

    public static final DatapackRegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.PLACED_FEATURE);

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final ResourceKey<PlacedFeature> YELLOW_ORCHID = HELPER.createKey("yellow_orchid");
    public static final ResourceKey<PlacedFeature> CHERRY_TREES_PLAINS = HELPER.createKey("cherry_trees_plains");

    public ModVegetationPlacements(BootstapContext<PlacedFeature> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, YELLOW_ORCHID, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.YELLOW_ORCHID), RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome());
        PlacementUtils.register(context, CHERRY_TREES_PLAINS, configuredFeatureRegistry.getOrThrow(ModVegetationFeatures.CHERRY_TREES_PLAINS), PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.CHERRY_SAPLING.get().defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());
    }
}
