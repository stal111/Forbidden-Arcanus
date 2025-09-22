package com.stal111.forbidden_arcanus.datagen.worldgen.placement

import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInOrePlacements
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.placement.*
import net.valhelsia.dataforge.RegistryDataProvider

object ModOrePlacements : RegistryDataProvider<PlacedFeature> {
    private fun orePlacement(
        countModifier: PlacementModifier,
        placementModifier: PlacementModifier
    ): List<PlacementModifier> {
        return listOf(countModifier, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome())
    }

    private fun commonOrePlacement(count: Int, placementModifier: PlacementModifier): List<PlacementModifier> {
        return this.orePlacement(CountPlacement.of(count), placementModifier)
    }

    override fun bootstrap(context: BootstrapContext<PlacedFeature>) {
        val configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE)

        PlacementUtils.register(
            context,
            BuiltInOrePlacements.ARCANE_CRYSTAL_ORE,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.ARCANE_CRYSTAL_ORE),
            this.commonOrePlacement(
                3,
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(14))
            )
        )
        PlacementUtils.register(
            context,
            BuiltInOrePlacements.RUNIC_STONE,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.RUNIC_STONE),
            this.commonOrePlacement(
                3,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(2))
            )
        )
        PlacementUtils.register(
            context,
            BuiltInOrePlacements.RUNIC_STONE_LOWER,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.RUNIC_STONE_LOWER),
            this.commonOrePlacement(
                5,
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-85), VerticalAnchor.absolute(-20))
            )
        )
        PlacementUtils.register(
            context,
            BuiltInOrePlacements.DARKSTONE,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.DARKSTONE),
            this.commonOrePlacement(
                28,
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(13))
            )
        )
        PlacementUtils.register(
            context,
            BuiltInOrePlacements.STELLA_ARCANUM,
            configuredFeatureRegistry.getOrThrow(BuiltInFeatures.STELLA_ARCANUM),
            this.commonOrePlacement(
                2,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-44), VerticalAnchor.absolute(42))
            )
        )
    }
}
