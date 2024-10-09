package com.stal111.forbidden_arcanus.data.worldgen.modifier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;
import net.valhelsia.valhelsia_core.api.datagen.worldgen.ValhelsiaBiomeModifierProvider;

/**
 * @author stal111
 * @since 2022-06-15
 */
public class ModBiomeModifiers extends ValhelsiaBiomeModifierProvider {

    public static final DatapackRegistryHelper<BiomeModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.BIOME_MODIFIERS);

    public static final ResourceKey<BiomeModifier> ADD_ARCANE_CRYSTAL_ORE = HELPER.createKey("add_arcane_crystal_ore");
    public static final ResourceKey<BiomeModifier> ADD_RUNIC_STONE = HELPER.createKey("add_runic_stone");
    public static final ResourceKey<BiomeModifier> ADD_DARKSTONE = HELPER.createKey("add_darkstone");
    public static final ResourceKey<BiomeModifier> ADD_STELLA_ARCANUM = HELPER.createKey("add_stella_arcanum");

    public static final ResourceKey<BiomeModifier> ADD_AURUM_TREES = HELPER.createKey("add_aurum_trees");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_ORCHIDS = HELPER.createKey("add_yellow_orchids");

    public static final ResourceKey<BiomeModifier> ADD_LOST_SOUL_OVERWORLD = HELPER.createKey("add_lost_soul_overworld");
    public static final ResourceKey<BiomeModifier> ADD_LOST_SOUL_NETHER = HELPER.createKey("add_lost_soul_nether");

    public ModBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<BiomeModifier> context) {
        this.addFeature(ADD_ARCANE_CRYSTAL_ORE, this.isOverworld, this.directFeature(ModOrePlacements.ARCANE_CRYSTAL_ORE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(ADD_RUNIC_STONE, this.isOverworld, this.directFeature(ModOrePlacements.RUNIC_STONE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(ADD_DARKSTONE, this.isOverworld, this.directFeature(ModOrePlacements.DARKSTONE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(ADD_STELLA_ARCANUM, this.isOverworld, this.directFeature(ModOrePlacements.STELLA_ARCANUM), GenerationStep.Decoration.UNDERGROUND_ORES);

        this.addFeature(ADD_AURUM_TREES, this.directBiome(Biomes.FLOWER_FOREST), this.directFeature(ModTreePlacements.AURUM_TREES), GenerationStep.Decoration.VEGETAL_DECORATION);
        this.addFeature(ADD_YELLOW_ORCHIDS, this.directBiome(Biomes.FLOWER_FOREST), this.directFeature(ModVegetationPlacements.YELLOW_ORCHID), GenerationStep.Decoration.VEGETAL_DECORATION);

        this.addSpawn(ADD_LOST_SOUL_OVERWORLD, this.isOverworld, new MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 35, 1, 3));
        this.addNetherSpawn(ADD_LOST_SOUL_NETHER, this.namedBiome(ModTags.Biomes.SPAWNS_CORRUPT_LOST_SOUL), MobCategory.MONSTER, 1.1D, 1.0D, new MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 60, 1, 4));
    }
}
