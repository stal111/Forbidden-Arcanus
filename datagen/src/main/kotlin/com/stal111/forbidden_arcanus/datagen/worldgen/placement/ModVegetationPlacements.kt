package com.stal111.forbidden_arcanus.datagen.worldgen.placement

import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInVegetationPlacements
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.placement.*
import net.valhelsia.dataforge.RegistryDataProvider

object ModVegetationPlacements : RegistryDataProvider<PlacedFeature> {
    override fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE)

        PlacementUtils.register(
            context,
            BuiltInVegetationPlacements.YELLOW_ORCHID,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.YELLOW_ORCHID),
            CountPlacement.of(3),
            RarityFilter.onAverageOnceEvery(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome(),
            CountPlacement.of(96),
            RandomOffsetPlacement.ofTriangle(6, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        )
    }
}
