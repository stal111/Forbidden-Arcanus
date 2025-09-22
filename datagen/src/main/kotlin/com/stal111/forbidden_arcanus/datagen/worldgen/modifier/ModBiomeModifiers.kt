package com.stal111.forbidden_arcanus.datagen.worldgen.modifier

import com.stal111.forbidden_arcanus.common.world.BuiltInBiomeModifiers
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInOrePlacements
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInTreePlacements
import com.stal111.forbidden_arcanus.common.world.placement.BuiltInVegetationPlacements
import com.stal111.forbidden_arcanus.core.init.ModEntities
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.biome.MobSpawnSettings
import net.minecraft.world.level.levelgen.GenerationStep
import net.neoforged.neoforge.common.world.BiomeModifier
import net.valhelsia.dataforge.RegistryDataProvider
import net.valhelsia.valhelsia_core.api.datagen.worldgen.ValhelsiaBiomeModifierProvider

object ModBiomeModifiersWrapper : RegistryDataProvider<BiomeModifier> {
    override fun bootstrap(context: BootstrapContext<BiomeModifier>) {
        //TODO
//        val modifiers = ModBiomeModifiers(context)
//
//        modifiers.init(context)
//        modifiers.bootstrap(context)
    }
}

class ModBiomeModifiers(context: BootstrapContext<BiomeModifier>) : ValhelsiaBiomeModifierProvider(context) {
    override fun bootstrap(context: BootstrapContext<BiomeModifier>) {
        this.addFeature(
            BuiltInBiomeModifiers.ADD_ARCANE_CRYSTAL_ORE,
            this.isOverworld,
            this.directFeature(BuiltInOrePlacements.ARCANE_CRYSTAL_ORE),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        this.addFeature(
            BuiltInBiomeModifiers.ADD_RUNIC_STONE,
            this.isOverworld,
            this.directFeature(BuiltInOrePlacements.RUNIC_STONE),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        this.addFeature(
            BuiltInBiomeModifiers.ADD_DARKSTONE,
            this.isOverworld,
            this.directFeature(BuiltInOrePlacements.DARKSTONE),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
        this.addFeature(
            BuiltInBiomeModifiers.ADD_STELLA_ARCANUM,
            this.isOverworld,
            this.directFeature(BuiltInOrePlacements.STELLA_ARCANUM),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )

        this.addFeature(
            BuiltInBiomeModifiers.ADD_AURUM_TREES,
            this.directBiome(Biomes.FLOWER_FOREST),
            this.directFeature(BuiltInTreePlacements.AURUM_TREES),
            GenerationStep.Decoration.VEGETAL_DECORATION
        )
        this.addFeature(
            BuiltInBiomeModifiers.ADD_YELLOW_ORCHIDS,
            this.directBiome(Biomes.FLOWER_FOREST),
            this.directFeature(BuiltInVegetationPlacements.YELLOW_ORCHID),
            GenerationStep.Decoration.VEGETAL_DECORATION
        )

        this.addSpawn(
            BuiltInBiomeModifiers.ADD_LOST_SOUL_OVERWORLD,
            this.isOverworld,
            MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 35, 1, 3)
        )
    }
}
