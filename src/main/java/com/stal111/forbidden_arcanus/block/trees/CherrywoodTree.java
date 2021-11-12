package com.stal111.forbidden_arcanus.block.trees;

import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class CherrywoodTree extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random random, boolean p_225546_2_) {
		return ModConfiguredFeatures.CHERRYWOOD;
	}
}
