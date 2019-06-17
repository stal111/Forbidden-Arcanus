package com.stal111.forbidden_arcanus.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

	public static void setupOregen() {
		for (Biome biome : ForgeRegistries.BIOMES) {

//			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
//					new DimensionCompositeFeature(Feature.ORE, new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), ModBlocks.arcane_crystal_ore.getDefaultState()), DimensionType.OVERWORLD));

//			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
//					new DimensionCompositeFeature<>(Feature.MINABLE,
//							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.runestone.getDefaultState(), 4),
//							new CountRange(), new CountRangeConfig(3, 10, 10, 25), DimensionType.OVERWORLD));
//
//			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
//					new DimensionCompositeFeature<>(Feature.MINABLE,
//							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.dark_stone.getDefaultState(), 20),
//							new CountRange(), new CountRangeConfig(70, 1, 1, 7), DimensionType.OVERWORLD));
//
//			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
//					new DimensionCompositeFeature<>(Feature.MINABLE,
//							new MinableConfig(MinableConfig.IS_ROCK, ModBlocks.dark_runestone.getDefaultState(), 3),
//							new CountRange(), new CountRangeConfig(15, 3, 3, 6), DimensionType.OVERWORLD));

		}
	}

}
