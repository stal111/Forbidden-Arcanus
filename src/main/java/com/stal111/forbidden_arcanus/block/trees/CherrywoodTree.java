package com.stal111.forbidden_arcanus.block.trees;

import java.util.Random;

import com.stal111.forbidden_arcanus.world.BiomeFeatures;
import com.stal111.forbidden_arcanus.world.gen.feature.CherrywoodTreeFeature;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;

public class CherrywoodTree extends Tree {

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> func_225546_b_(Random random) {
		return new CherrywoodTreeFeature(TreeFeatureConfig::func_227338_a_).func_225566_b_(BiomeFeatures.CHERRYWOOD_TREE);
	}
}
