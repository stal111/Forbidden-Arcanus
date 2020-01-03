package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
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
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.ARCANE_CRYSTAL_ORE.getState(), WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), 0, 0, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get()))));
			}
			
			if(WorldGenConfig.GENERATE_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.RUNESTONE.getState(), WorldGenConfig.RUNESTONE_MAX_VEIN_SIZE.get())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(WorldGenConfig.RUNESTONE_COUNT.get(), 0, 0, WorldGenConfig.RUNESTONE_MAX_HEIGHT.get()))));
			}
			
			if(WorldGenConfig.GENERATE_DARK_RUNESTONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.DARK_RUNESTONE.getState(), WorldGenConfig.DARK_RUNESTONE_MAX_VEIN_SIZE.get())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(WorldGenConfig.DARK_RUNESTONE_COUNT.get(), 0, 0, WorldGenConfig.DARK_RUNESTONE_MAX_HEIGHT.get()))));
			}
			
			if(WorldGenConfig.GENERATE_DARK_STONE.get())  {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.DARK_STONE.getState(), WorldGenConfig.DARK_STONE_MAX_VEIN_SIZE.get())).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(WorldGenConfig.DARK_STONE_COUNT.get(), 0, 0, WorldGenConfig.DARK_STONE_MAX_HEIGHT.get()))));
			}
		}
	}
}
