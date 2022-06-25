package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mod Structures
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructures
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-04-07
 */
public class ModStructures implements RegistryClass {

    public static final List<IValhelsiaStructure> MOD_STRUCTURES = new ArrayList<>();

    public static final RegistryHelper<Structure> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.STRUCTURE_REGISTRY);

    public static final Holder<? extends Structure> NIPA = HELPER.register("nipa", () -> new NipaStructure(structure(ModTags.Biomes.HAS_NIPA, TerrainAdjustment.NONE), false)).getHolder().get();
    public static final Holder<? extends Structure> NIPA_FLOATING = HELPER.register("nipa_floating", () -> new NipaStructure(structure(ModTags.Biomes.HAS_NIPA, TerrainAdjustment.NONE), true)).getHolder().get();

    private static Structure.StructureSettings structure(TagKey<Biome> tagKey, Map<MobCategory, StructureSpawnOverride> spawnOverrideMap, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return new Structure.StructureSettings(biomes(tagKey), spawnOverrideMap, decoration, terrainAdjustment);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> tagKey, GenerationStep.Decoration decoration, TerrainAdjustment terrainAdjustment) {
        return structure(tagKey, Map.of(), decoration, terrainAdjustment);
    }

    private static Structure.StructureSettings structure(TagKey<Biome> tagKey, TerrainAdjustment terrainAdjustment) {
        return structure(tagKey, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdjustment);
    }

    private static Holder<Structure> register(ResourceKey<Structure> resourceKey, Structure structure) {
        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, resourceKey, structure);
    }

    private static HolderSet<Biome> biomes(TagKey<Biome> tagKey) {
        return BuiltinRegistries.BIOME.getOrCreateTag(tagKey);
    }
}
