package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.config.OreGenConfig;
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

			if(OreGenConfig.GENERATE_ARCANE_CRYSTAL_ORE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
						Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.arcane_crystal_ore.getDefaultState(), OreGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get()),
						Placement.COUNT_RANGE, new CountRangeConfig(OreGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), 0, OreGenConfig.ARCANE_CRYSTAL_ORE_MIN_HEIGHT.get(), OreGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get())));
			}
			
			if(OreGenConfig.GENERATE_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
						Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.runestone.getDefaultState(), OreGenConfig.RUNESTONE_MAX_VEIN_SIZE.get()),
						Placement.COUNT_RANGE, new CountRangeConfig(OreGenConfig.RUNESTONE_COUNT.get(), 0, OreGenConfig.RUNESTONE_MIN_HEIGHT.get(), OreGenConfig.RUNESTONE_MAX_HEIGHT.get())));
			}
			
			if(OreGenConfig.GENERATE_DARK_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
						Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dark_runestone.getDefaultState(), OreGenConfig.DARK_RUNESTONE_MAX_VEIN_SIZE.get()),
						Placement.COUNT_RANGE, new CountRangeConfig(OreGenConfig.DARK_RUNESTONE_COUNT.get(), 0, OreGenConfig.DARK_RUNESTONE_MIN_HEIGHT.get(), OreGenConfig.DARK_RUNESTONE_MAX_HEIGHT.get())));
			}
			
			if(OreGenConfig.GENERATE_DARK_STONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(
						Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.dark_stone.getDefaultState(), OreGenConfig.DARK_STONE_MAX_VEIN_SIZE.get()),
						Placement.COUNT_RANGE, new CountRangeConfig(OreGenConfig.DARK_STONE_COUNT.get(), 0, OreGenConfig.DARK_STONE_MIN_HEIGHT.get(), OreGenConfig.DARK_STONE_MAX_HEIGHT.get())));
			}
		}
	}
}
