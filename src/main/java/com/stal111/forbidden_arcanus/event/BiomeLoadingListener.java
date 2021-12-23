package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.init.world.ModStructureFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber
public class BiomeLoadingListener {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceLocation name = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        Biome.ClimateSettings climate = event.getClimate();

        if (WorldGenConfig.ARCANE_CRYSTAL_ORE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.ARCANE_CRYSTAL_ORE);
        }
        if (WorldGenConfig.RUNESTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.RUNESTONE);
        }
        if (WorldGenConfig.DARKSTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.DARKSTONE);
        }
        if (WorldGenConfig.ARCANE_GILDED_DARKSTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.ARCANE_GILDED_DARKSTONE);
        }
        if (WorldGenConfig.DARK_RUNESTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.DARK_RUNESTONE);
        }
        if (WorldGenConfig.STELLA_ARCANUM_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.STELLA_ARCANUM);
        }
        if (WorldGenConfig.XPETRIFIED_ORE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.XPETRIFIED_ORE);
        }

        if (category == Biome.BiomeCategory.PLAINS && WorldGenConfig.CHERRYWOOD_TREE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHERRYWOOD_TREES);
        }

        if (Objects.equals(event.getName(), new ResourceLocation("flower_forest"))) {
            if (WorldGenConfig.MYSTERYWOOD_TREE_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MYSTERYWOOD_TREES);
            }
            if (WorldGenConfig.YELLOW_ORCHID_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.YELLOW_ORCHID);
            }
        }

        if (Objects.equals(name, new ResourceLocation("dark_forest")) || Objects.equals(name, new ResourceLocation("dark_forest_hills"))) {
            if (WorldGenConfig.EDELWOOD_TREE_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.EDELWOOD_TREES);
            }
        }

        if (WorldGenConfig.PETRIFIED_ROOT_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, ModConfiguredFeatures.PETRIFIED_ROOT);
        }

        if (WorldGenConfig.NIPA_GENERATE.get()) {
            if (climate.downfall != 0.0F && climate.temperature < 2.0F && category != Biome.BiomeCategory.BEACH && category != Biome.BiomeCategory.OCEAN && category != Biome.BiomeCategory.MUSHROOM) {
                event.getGeneration().addStructureStart(ModStructureFeatures.NIPA);
            } else if (category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NETHER) {
                event.getGeneration().addStructureStart(ModStructureFeatures.NIPA_ALWAYS_FLOATING);
            }
        }
    }
}
