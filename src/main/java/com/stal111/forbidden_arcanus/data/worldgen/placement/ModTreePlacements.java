package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

/**
 * Mod Tree Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements
 *
 * @author stal111
 * @since 2021-12-29
 */
public class ModTreePlacements extends DatapackRegistryClass<PlacedFeature> {

    public static final DatapackRegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(Registries.PLACED_FEATURE);

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final ResourceKey<PlacedFeature> EDELWOOD_TREES = HELPER.createKey("edelwood_trees");
    public static final ResourceKey<PlacedFeature> SMALL_CHERRY_CHECKED = HELPER.createKey("small_cherry_checked");
    public static final ResourceKey<PlacedFeature> LARGE_CHERRY_CHECKED = HELPER.createKey("large_cherry_checked");
    public static final ResourceKey<PlacedFeature> AURUM_TREES = HELPER.createKey("aurum_trees");

    public ModTreePlacements(DataProviderInfo info, BootstapContext<PlacedFeature> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, EDELWOOD_TREES, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.EDELWOOD), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(18, 0.25F, 3), BiomeFilter.biome());
        PlacementUtils.register(context, SMALL_CHERRY_CHECKED, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.SMALL_CHERRY), PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING.get()));
        PlacementUtils.register(context, LARGE_CHERRY_CHECKED, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.LARGE_CHERRY), PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRY_SAPLING.get()));
        PlacementUtils.register(context, AURUM_TREES, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.AURUM), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(1, 0.2F, 1), BiomeFilter.biome());
    }
}
