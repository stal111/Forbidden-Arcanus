package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

	public static void setupOreGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if(WorldGenConfig.GENERATE_ARCANE_CRYSTAL_ORE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.arcane_crystal_ore.getDefaultState(), WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), 0, WorldGenConfig.ARCANE_CRYSTAL_ORE_MIN_HEIGHT.get(), WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get())));
			}
			
			if(WorldGenConfig.GENERATE_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.runestone.getDefaultState(), WorldGenConfig.RUNESTONE_MAX_VEIN_SIZE.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.RUNESTONE_COUNT.get(), 0, WorldGenConfig.RUNESTONE_MIN_HEIGHT.get(), WorldGenConfig.RUNESTONE_MAX_HEIGHT.get())));
			}
			
			if(WorldGenConfig.GENERATE_DARK_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dark_runestone.getDefaultState(), WorldGenConfig.DARK_RUNESTONE_MAX_VEIN_SIZE.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.DARK_RUNESTONE_COUNT.get(), 0, WorldGenConfig.DARK_RUNESTONE_MIN_HEIGHT.get(), WorldGenConfig.DARK_RUNESTONE_MAX_HEIGHT.get())));
			}
			
			if(WorldGenConfig.GENERATE_DARK_STONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dark_stone.getDefaultState(), WorldGenConfig.DARK_STONE_MAX_VEIN_SIZE.get()), Placement.COUNT_RANGE, new CountRangeConfig(WorldGenConfig.DARK_STONE_COUNT.get(), 0, WorldGenConfig.DARK_STONE_MIN_HEIGHT.get(), WorldGenConfig.DARK_STONE_MAX_HEIGHT.get())));
			}
		}
	}
}
