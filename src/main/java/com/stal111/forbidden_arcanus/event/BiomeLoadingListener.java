package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
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

        if (WorldGenConfig.ARCANE_CRYSTAL_ORE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ARCANE_CRYSTAL_ORE);
        }
        if (WorldGenConfig.RUNIC_STONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.RUNIC_STONE);
        }
        if (WorldGenConfig.DARKSTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.DARKSTONE);
        }
        if (WorldGenConfig.ARCANE_GILDED_DARKSTONE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.ARCANE_GILDED_DARKSTONE);
        }
        if (WorldGenConfig.STELLA_ARCANUM_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.STELLA_ARCANUM);
        }
        if (WorldGenConfig.XPETRIFIED_ORE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModOrePlacements.XPETRIFIED_ORE);
        }

        if (category == Biome.BiomeCategory.PLAINS && WorldGenConfig.CHERRYWOOD_TREE_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.CHERRY_TREES_PLAINS);
        }

        if (Objects.equals(event.getName(), new ResourceLocation("flower_forest"))) {
//            if (WorldGenConfig.MYSTERYWOOD_TREE_GENERATE.get()) {
//                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MYSTERYWOOD_TREES);
//            }
            if (WorldGenConfig.YELLOW_ORCHID_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.YELLOW_ORCHID);
            }
        }

        if (Objects.equals(name, new ResourceLocation("dark_forest")) || Objects.equals(name, new ResourceLocation("dark_forest_hills"))) {
            if (WorldGenConfig.EDELWOOD_TREE_GENERATE.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModTreePlacements.EDELWOOD_TREES);
            }
        }

        if (WorldGenConfig.PETRIFIED_ROOT_GENERATE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModCavePlacements.PETRIFIED_ROOT);
        }
    }
}
