package com.stal111.forbidden_arcanus.world.gen;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.world.gen.feature.DimensionCompositeFeature;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.placement.CountRange;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

	public static void setupOregen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					new DimensionCompositeFeature<>(Feature.MINABLE,
							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.arcane_crystal_ore.getDefaultState(), 5),
							new CountRange(), new CountRangeConfig(4, 8, 8, 18), DimensionType.OVERWORLD));

			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					new DimensionCompositeFeature<>(Feature.MINABLE,
							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.runestone.getDefaultState(), 4),
							new CountRange(), new CountRangeConfig(3, 10, 10, 25), DimensionType.OVERWORLD));

			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					new DimensionCompositeFeature<>(Feature.MINABLE,
							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.dark_stone.getDefaultState(), 20),
							new CountRange(), new CountRangeConfig(70, 1, 1, 7), DimensionType.OVERWORLD));

			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					new DimensionCompositeFeature<>(Feature.MINABLE,
							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.dark_runestone.getDefaultState(), 3),
							new CountRange(), new CountRangeConfig(15, 3, 3, 6), DimensionType.OVERWORLD));

		}
	}

}
