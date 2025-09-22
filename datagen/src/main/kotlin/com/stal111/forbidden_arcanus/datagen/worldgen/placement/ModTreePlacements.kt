package com.stal111.forbidden_arcanus.datagen.worldgen.placement

import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInTreePlacements
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.placement.*
import net.valhelsia.dataforge.RegistryDataProvider

object ModTreePlacements : RegistryDataProvider<PlacedFeature> {

    val TREE_THRESHOLD: PlacementModifier = SurfaceWaterDepthFilter.forMaxDepth(0)

    override fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE)

        PlacementUtils.register(
            context,
            BuiltInTreePlacements.EDELWOOD_TREES,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.EDELWOOD),
            InSquarePlacement.spread(),
            TREE_THRESHOLD,
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            PlacementUtils.countExtra(18, 0.25f, 3),
            BiomeFilter.biome()
        )
        PlacementUtils.register(
            context,
            BuiltInTreePlacements.AURUM_TREES,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.AURUM),
            InSquarePlacement.spread(),
            TREE_THRESHOLD,
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            PlacementUtils.countExtra(1, 0.2f, 1),
            BiomeFilter.biome()
        )
    }
}
