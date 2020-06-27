package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

	public static void setupOreGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if (WorldGenConfig.ARCANE_CRYSTAL_ORE_GENERATE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ARCANE_CRYSTAL_ORE.getState(), WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), 0, 0, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get()))));
			}
			
			if (WorldGenConfig.RUNESTONE_GENERATE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.RUNESTONE.getState(), WorldGenConfig.RUNESTONE_MAX_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(WorldGenConfig.RUNESTONE_COUNT.get(), 0, 0, WorldGenConfig.RUNESTONE_MAX_HEIGHT.get()))));
			}
			
			if (WorldGenConfig.DARK_RUNESTONE_GENERATE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.DARK_RUNESTONE.getState(), WorldGenConfig.DARK_RUNESTONE_MAX_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(WorldGenConfig.DARK_RUNESTONE_COUNT.get(), 0, 0, WorldGenConfig.DARK_RUNESTONE_MAX_HEIGHT.get()))));
			}
			
			if (WorldGenConfig.DARK_STONE_GENERATE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.DARK_STONE.getState(), WorldGenConfig.DARK_STONE_MAX_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(WorldGenConfig.DARK_STONE_COUNT.get(), 0, 0, WorldGenConfig.DARK_STONE_MAX_HEIGHT.get()))));
			}

			if (WorldGenConfig.STELLA_ARCANUM_GENERATE.get()) {
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.STELLA_ARCANUM.getState(), WorldGenConfig.STELLA_ARCANUM_MAX_VEIN_SIZE.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(WorldGenConfig.STELLA_ARCANUM_COUNT.get(), 0, 0, WorldGenConfig.STELLA_ARCANUM_MAX_HEIGHT.get()))));
			}

			if (WorldGenConfig.XPETRIFIED_ORE_GENERATE.get()) {
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.EMERALD_ORE.withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), ModBlocks.XPETRIFIED_ORE.getState())).withPlacement(Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
			}
		}
	}
}
