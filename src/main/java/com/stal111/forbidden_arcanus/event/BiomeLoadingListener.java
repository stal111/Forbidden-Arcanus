package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BiomeLoadingListener {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceLocation name = event.getName();
        Biome.Category category = event.getCategory();

        if (WorldGenConfig.ARCANE_CRYSTAL_ORE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.ARCANE_CRYSTAL_ORE);
        }
        if (WorldGenConfig.RUNESTONE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.RUNESTONE);
        }
        if (WorldGenConfig.DARKSTONE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.DARKSTONE);
        }
        if (WorldGenConfig.ARCANE_GILDED_DARKSTONE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.ARCANE_GILDED_DARKSTONE);
        }
        if (WorldGenConfig.DARK_RUNESTONE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.DARK_RUNESTONE);
        }
        if (WorldGenConfig.STELLA_ARCANUM_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.STELLA_ARCANUM);
        }
        if (WorldGenConfig.XPETRIFIED_ORE_GENERATE.get()) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.XPETRIFIED_ORE);
        }

        if (category == Biome.Category.PLAINS) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHERRYWOOD_TREES);
        }

        if (event.getName().equals(new ResourceLocation("flower_forest"))) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MYSTERYWOOD_TREES);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.YELLOW_ORCHID);
        }

        if (name.equals(new ResourceLocation("dark_forest")) || name.equals(new ResourceLocation("dark_forest_hills"))) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.EDELWOOD_LOG);
        }

        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ModConfiguredFeatures.PETRIFIED_ROOT);
    }
}
