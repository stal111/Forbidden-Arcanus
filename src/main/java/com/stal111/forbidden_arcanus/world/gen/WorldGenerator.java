package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.world.gen.feature.CherrywoodTreeFeature;
import com.stal111.forbidden_arcanus.world.gen.feature.EdelwoodLogFeature;
import com.stal111.forbidden_arcanus.world.gen.feature.MysterywoodTreeFeature;

import com.stal111.forbidden_arcanus.world.gen.feature.YellowOrchidFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DarkForestBiome;
import net.minecraft.world.biome.DarkForestHillsBiome;
import net.minecraft.world.biome.FlowerForestBiome;
import net.minecraft.world.biome.PlainsBiome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGenerator {
	
	public static void setupWorldGen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			if(biome instanceof PlainsBiome) {
				biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new CherrywoodTreeFeature(NoFeatureConfig::deserialize, false), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig((int) 0.5, 0.1f, 1)));
			}
			
			if (biome instanceof FlowerForestBiome) {
				biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new MysterywoodTreeFeature(NoFeatureConfig::deserialize, false), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig((int) 0.5, 0.1f, 1)));
				biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new YellowOrchidFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_32, new FrequencyConfig(10)));
			}
			
			if (biome instanceof DarkForestBiome || biome instanceof DarkForestHillsBiome) {
				biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new EdelwoodLogFeature(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(9)));
			}
		}
	}
	
}
