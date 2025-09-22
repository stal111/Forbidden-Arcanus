package com.stal111.forbidden_arcanus.datagen.worldgen.placement

import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInVegetationPlacements
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.levelgen.placement.RarityFilter
import net.valhelsia.dataforge.RegistryDataProvider

object ModVegetationPlacements : RegistryDataProvider<PlacedFeature> {
    override fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE)

        PlacementUtils.register(
            context,
            BuiltInVegetationPlacements.YELLOW_ORCHID,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.YELLOW_ORCHID),
            RarityFilter.onAverageOnceEvery(12),
            PlacementUtils.HEIGHTMAP,
            InSquarePlacement.spread(),
            BiomeFilter.biome()
        )
    }
}
