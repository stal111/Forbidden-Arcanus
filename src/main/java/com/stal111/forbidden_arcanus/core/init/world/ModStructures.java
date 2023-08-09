package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.Map;

/**
 * Mod Structures
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructures
 *
 * @author stal111
 * @since 2021-04-07
 */
public class ModStructures extends DatapackRegistryClass<Structure> {


    public static final DatapackRegistryHelper<Structure> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.STRUCTURE);

    public static final ResourceKey<Structure> NIPA = HELPER.createKey("nipa");
    public static final ResourceKey<Structure> NIPA_FLOATING = HELPER.createKey("nipa_floating");

    public ModStructures(BootstapContext<Structure> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);

        context.register(NIPA, new NipaStructure(structure(biomeRegistry.getOrThrow(ModTags.Biomes.HAS_NIPA), TerrainAdjustment.NONE), false));
        context.register(NIPA_FLOATING, new NipaStructure(structure(biomeRegistry.getOrThrow(ModTags.Biomes.HAS_NIPA), TerrainAdjustment.NONE), true));
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tagKey, Map<MobCategory, StructureSpawnOverride> spawnOverrideMap, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return new Structure.StructureSettings(tagKey, spawnOverrideMap, decoration, terrainAdjustment);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tagKey, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return structure(tagKey, Map.of(), decoration, terrainAdjustment);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> tagKey, TerrainAdjustment terrainAdjustment) {
        return structure(tagKey, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdjustment);
    }
}
