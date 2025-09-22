package com.stal111.forbidden_arcanus.datagen.worldgen.modifier

import com.stal111.forbidden_arcanus.common.world.BuiltInBiomeModifiers
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInOrePlacements
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInTreePlacements
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInVegetationPlacements
import com.stal111.forbidden_arcanus.core.init.ModEntities
import net.minecraft.core.HolderSet
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.GenerationStep
import net.neoforged.neoforge.common.world.BiomeModifier
import net.valhelsia.dataforge.worldgen.DataForgeBiomeModifierProvider

object ModBiomeModifiers : DataForgeBiomeModifierProvider() {
    override fun bootstrap(context: BootstrapContext<BiomeModifier>) {
        context.registerFeature(
            BuiltInBiomeModifiers.ADD_ARCANE_CRYSTAL_ORE,
            context.overWorldBiomes,
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInOrePlacements.ARCANE_CRYSTAL_ORE)),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        context.registerFeature(
            BuiltInBiomeModifiers.ADD_RUNIC_STONE,
            context.overWorldBiomes,
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInOrePlacements.RUNIC_STONE)),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        context.registerFeature(
            BuiltInBiomeModifiers.ADD_DARKSTONE,
            context.overWorldBiomes,
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInOrePlacements.DARKSTONE)),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        context.registerFeature(
            BuiltInBiomeModifiers.ADD_STELLA_ARCANUM,
            context.overWorldBiomes,
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInOrePlacements.STELLA_ARCANUM)),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )

        context.registerFeature(
            BuiltInBiomeModifiers.ADD_AURUM_TREES,
            HolderSet.direct(context.biomeLookup.getOrThrow(Biomes.FLOWER_FOREST)),
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInTreePlacements.AURUM_TREES)),
            GenerationStep.Decoration.VEGETAL_DECORATION
        )
        context.registerFeature(
            BuiltInBiomeModifiers.ADD_YELLOW_ORCHIDS,
            HolderSet.direct(context.biomeLookup.getOrThrow(Biomes.FLOWER_FOREST)),
            HolderSet.direct(context.featureLookup.getOrThrow(BuiltInVegetationPlacements.YELLOW_ORCHID)),
            GenerationStep.Decoration.VEGETAL_DECORATION
        )

        context.registerSpawn(
            BuiltInBiomeModifiers.ADD_LOST_SOUL_OVERWORLD,
            context.overWorldBiomes,
            MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 35, 1, 3)
        )
    }
}
