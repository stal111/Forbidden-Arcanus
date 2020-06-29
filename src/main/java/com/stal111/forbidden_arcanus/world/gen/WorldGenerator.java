package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModFeatures;
import com.stal111.forbidden_arcanus.world.BiomeFeatures;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.OptionalInt;

public class WorldGenerator {
	
	public static void setupWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if (biome.getTempCategory() == Biome.TempCategory.MEDIUM && biome.getPrecipitation() == Biome.RainType.RAIN) {
				if(biome.getCategory() == Biome.Category.PLAINS) {
					if (WorldGenConfig.CHERRYWOOD_TREE_GENERATE.get()) {
						//biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.CHERRYWOOD_TREE.getFeature().withConfiguration(BiomeFeatures.CHERRYWOOD_TREE).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig((int) 0.4, 0.1F, 1))));
						BaseTreeFeatureConfig CHERRYWOOD_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LEAVES.getState()), new AcaciaFoliagePlacer(2, 0, 0, 0), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2))).func_236700_a_().build();


						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(CHERRYWOOD_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig((int) 0.4F, 0.1F, 1))));
					}
				}

				if (biome instanceof FlowerForestBiome) {
					if(WorldGenConfig.MYSTERYWOOD_TREE_GENERATE.get())  {

						BaseTreeFeatureConfig MYSTERYWOOD_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LEAVES.getState()), new FancyFoliagePlacer(2, 0, 4, 0, 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).func_236700_a_().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();

						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(MYSTERYWOOD_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig((int) 0.8F, 0.1F, 1))));
					}
					if(WorldGenConfig.YELLOW_ORCHID_GENERATE.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(BiomeFeatures.YELLOW_ORCHID).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(17))));
					}
				}

				if (biome instanceof DarkForestBiome || biome instanceof DarkForestHillsBiome) {
					if(WorldGenConfig.EDELWOOD_TREE_GENERATE.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(BiomeFeatures.EDELWOOD_TREE).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(10))));
					}
				}
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ModFeatures.PETRIFIED_ROOT.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(100))));
			}
		}
	}
}
