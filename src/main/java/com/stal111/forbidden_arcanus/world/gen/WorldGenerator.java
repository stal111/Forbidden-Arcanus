package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModFeatures;
import com.stal111.forbidden_arcanus.world.BiomeFeatures;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGenerator {
	
	public static void setupWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if (biome.getTempCategory() == Biome.TempCategory.MEDIUM && biome.getPrecipitation() == Biome.RainType.RAIN) {
				if(biome.getCategory() == Biome.Category.PLAINS) {
					if (WorldGenConfig.GENERATE_CHERRYWOOD_TREES.get()) {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.CHERRYWOOD_TREE.getFeature().func_225566_b_(BiomeFeatures.CHERRYWOOD_TREE).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig((int) 0.4, 0.1F, 1))));
					}
				}

				if (biome instanceof FlowerForestBiome) {
					if(WorldGenConfig.GENERATE_MYSTERYWOOD_TREES.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FANCY_TREE.func_225566_b_(BiomeFeatures.MYSTERYWOOD_TREE).func_227228_a_(Placement.COUNT_EXTRA_HEIGHTMAP.func_227446_a_(new AtSurfaceWithExtraConfig((int) 0.8, 0.1F, 1))));
					}
					if(WorldGenConfig.GENERATE_YELLOW_ORCHID.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227247_y_.func_225566_b_(BiomeFeatures.YELLOW_ORCHID).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(17))));
					}
				}

				if (biome instanceof DarkForestBiome || biome instanceof DarkForestHillsBiome) {
					if(WorldGenConfig.GENERATE_EDELWOOD.get())  {
						biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.func_225566_b_(BiomeFeatures.EDELWOOD).func_227228_a_(Placement.COUNT_HEIGHTMAP_DOUBLE.func_227446_a_(new FrequencyConfig(10))));
					}
				}
			}
		}
	}
}
