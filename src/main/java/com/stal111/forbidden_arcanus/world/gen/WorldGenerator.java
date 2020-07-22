package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModFeatures;
import com.stal111.forbidden_arcanus.world.ModBiomeFeatures;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.OptionalInt;

public class WorldGenerator {
	
	public static void setupWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if (biome.getTempCategory() == Biome.TempCategory.MEDIUM && biome.getPrecipitation() == Biome.RainType.RAIN) {
				if(biome.getCategory() == Biome.Category.PLAINS) {
					if (WorldGenConfig.CHERRYWOOD_TREE_GENERATE.get()) {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(ModBiomeFeatures.CHERRYWOOD_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig((int) 0.4F, 0.1F, 1))));
					}
				}

				if (biome instanceof FlowerForestBiome) {
					if(WorldGenConfig.MYSTERYWOOD_TREE_GENERATE.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(ModBiomeFeatures.MYSTERYWOOD_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig((int) 0.8F, 0.1F, 1))));
					}
					if(WorldGenConfig.YELLOW_ORCHID_GENERATE.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(ModBiomeFeatures.YELLOW_ORCHID).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(17))));
					}
				}

				if (biome instanceof DarkForestBiome || biome instanceof DarkForestHillsBiome) {
					if(WorldGenConfig.EDELWOOD_TREE_GENERATE.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModBiomeFeatures.EDELWOOD_TREE).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(10))));
					}
				}
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ModFeatures.PETRIFIED_ROOT.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(100))));
			}
		}
	}
}
