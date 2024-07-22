package com.stal111.forbidden_arcanus.data.worldgen.modifier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.datagen.worldgen.ValhelsiaBiomeModifierProvider;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import static com.stal111.forbidden_arcanus.ForbiddenArcanus.MOD_ID;

/**
 * @author stal111
 * @since 2022-06-15
 */
//TODO
public class ModBiomeModifiers extends ValhelsiaBiomeModifierProvider {

    public static final DatapackRegistryHelper<BiomeModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.BIOME_MODIFIERS);

    private final ResourceKey<Registry<BiomeModifier>> KEY = ForgeRegistries.Keys.BIOME_MODIFIERS;
    public ModBiomeModifiers(BootstapContext<BiomeModifier> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<BiomeModifier> context) {
        this.addFeature(HELPER.createKey("add_arcane_crystal_ore"), this.isOverworld, this.directFeature(ModOrePlacements.ARCANE_CRYSTAL_ORE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_runic_stone"), this.isOverworld, this.directFeature(ModOrePlacements.RUNIC_STONE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_darkstone"), this.isOverworld, this.directFeature(ModOrePlacements.DARKSTONE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_arcane_gilded_darkstone"), this.isOverworld, this.directFeature(ModOrePlacements.ARCANE_GILDED_DARKSTONE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_stella_arcanum"), this.isOverworld, this.directFeature(ModOrePlacements.STELLA_ARCANUM), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_xpetrified_ore"), this.isOverworld, this.directFeature(ModOrePlacements.XPETRIFIED_ORE), GenerationStep.Decoration.UNDERGROUND_ORES);
        this.addFeature(HELPER.createKey("add_petrified_roots"), this.isOverworld, this.directFeature(ModCavePlacements.PETRIFIED_ROOT), GenerationStep.Decoration.UNDERGROUND_ORES);

        this.addFeature(HELPER.createKey("add_edelwood_trees"), this.directBiome(Biomes.DARK_FOREST), this.directFeature(ModTreePlacements.EDELWOOD_TREES), GenerationStep.Decoration.VEGETAL_DECORATION);
        this.addFeature(HELPER.createKey("add_aurum_trees"), this.directBiome(Biomes.FLOWER_FOREST), this.directFeature(ModTreePlacements.AURUM_TREES), GenerationStep.Decoration.VEGETAL_DECORATION);
        this.addFeature(HELPER.createKey("add_yellow_orchids"), this.directBiome(Biomes.FLOWER_FOREST), this.directFeature(ModVegetationPlacements.YELLOW_ORCHID), GenerationStep.Decoration.VEGETAL_DECORATION);

        this.addSpawn(HELPER.createKey("add_lost_soul_overworld"), this.isOverworld, new MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 35, 1, 3));
        this.addNetherSpawn(HELPER.createKey("add_lost_soul_nether"), this.namedBiome(ModTags.Biomes.SPAWNS_CORRUPT_LOST_SOUL), MobCategory.MONSTER, 1.1D, 1.0D, new MobSpawnSettings.SpawnerData(ModEntities.LOST_SOUL.get(), 60, 1, 4));
    }
}
