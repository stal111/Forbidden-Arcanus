package com.stal111.forbidden_arcanus.block.trees;

import java.util.Random;

import com.stal111.forbidden_arcanus.world.ModBiomeFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;

public class CherrywoodTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean p_225546_2_) {
		return Feature.field_236291_c_.withConfiguration(ModBiomeFeatures.CHERRYWOOD_TREE_CONFIG);
	}
}
