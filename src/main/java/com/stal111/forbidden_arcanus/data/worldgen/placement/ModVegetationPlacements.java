package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
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

    public ModVegetationPlacements(BootstapContext<PlacedFeature> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, YELLOW_ORCHID, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.YELLOW_ORCHID), RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome());
    }
}
