package com.stal111.forbidden_arcanus.block.trees;

import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class MysterywoodTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean p_225546_2_) {
		return (ConfiguredFeature<BaseTreeFeatureConfig, ?>) ModConfiguredFeatures.MYSTERYWOOD;
	}
}
