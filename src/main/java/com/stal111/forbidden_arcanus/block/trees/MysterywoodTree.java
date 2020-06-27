package com.stal111.forbidden_arcanus.block.trees;

import java.util.Random;

import com.stal111.forbidden_arcanus.world.BiomeFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;

public class MysterywoodTree extends Tree {

//	@Nullable
//	@Override
//	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
//		return new FancyTreeFeature(TreeFeatureConfig::func_227338_a_).withConfiguration(BiomeFeatures.MYSTERYWOOD_TREE);
//	}

	@Nullable
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return Feature.field_236291_c_.withConfiguration(DefaultBiomeFeatures.ACACIA_TREE_CONFIG);
	}
}
