package com.stal111.forbidden_arcanus.data.worldgen.modifier;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;

import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 2022-06-15
 */
public class ModBiomeModifiers {

    public static Map<ResourceLocation, BiomeModifier> getBiomeModifiers(RegistryOps<JsonElement> ops) {
        Registry<Biome> biomeRegistry = ops.registry(Registry.BIOME_REGISTRY).get();

        HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(biomeRegistry, BiomeTags.IS_OVERWORLD);

        Registry<PlacedFeature> registry = ops.registry(Registry.PLACED_FEATURE_REGISTRY).get();

        return ImmutableMap.<ResourceLocation, BiomeModifier>builder()
                .put(location("add_arcane_crystal_ore"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.ARCANE_CRYSTAL_ORE.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_runic_stone"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.RUNIC_STONE.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_darkstone"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.DARKSTONE.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_arcane_gilded_darkstone"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.ARCANE_GILDED_DARKSTONE.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_stella_arcanum"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.STELLA_ARCANUM.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_xpetrified_ore"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModOrePlacements.XPETRIFIED_ORE.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_petrified_roots"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        overworldTag,
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModCavePlacements.PETRIFIED_ROOT.getKey())),
                        GenerationStep.Decoration.UNDERGROUND_ORES))
                .put(location("add_cherrywood_trees"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomeRegistry.getHolder(Biomes.PLAINS).get(), biomeRegistry.getHolder(Biomes.SUNFLOWER_PLAINS).get()),
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModVegetationPlacements.CHERRY_TREES_PLAINS.getKey())),
                        GenerationStep.Decoration.VEGETAL_DECORATION))
                .put(location("add_edelwood_trees"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomeRegistry.getHolder(Biomes.DARK_FOREST).get()),
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModTreePlacements.EDELWOOD_TREES.getKey())),
                        GenerationStep.Decoration.VEGETAL_DECORATION))
                .put(location("add_yellow_orchids"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomeRegistry.getHolder(Biomes.FLOWER_FOREST).get()),
                        HolderSet.direct(registry.getOrCreateHolderOrThrow(ModVegetationPlacements.YELLOW_ORCHID.getKey())),
                        GenerationStep.Decoration.VEGETAL_DECORATION)).
                put(location("add_lost_soul"), new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                        overworldTag,
                        List.of(new MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 50, 1, 1)))).build();
    }

    private static ResourceLocation location(String path) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, path);
    }
}
