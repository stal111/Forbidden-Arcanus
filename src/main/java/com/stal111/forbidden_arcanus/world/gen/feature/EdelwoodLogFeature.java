package com.stal111.forbidden_arcanus.world.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.stal111.forbidden_arcanus.block.ModBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EdelwoodLogFeature extends Feature<NoFeatureConfig> {

	public EdelwoodLogFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
			BlockPos pos, NoFeatureConfig config) {
		for (int lvt_6_1_ = 0; lvt_6_1_ < 10; ++lvt_6_1_) {
			final BlockPos lvt_7_1_ = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));
			if (world.isAirBlock(lvt_7_1_)) {
				for (int lvt_8_1_ = 1 + rand.nextInt(rand.nextInt(3) + 2), lvt_9_1_ = 0; lvt_9_1_ < lvt_8_1_; ++lvt_9_1_) {
					if (world.getBlockState(lvt_7_1_.down()).getBlock() == Blocks.GRASS_BLOCK) {
						world.setBlockState(lvt_7_1_.up(lvt_9_1_), ModBlocks.edelwood_log.getDefaultState(), 2);
					}
				}
			}
		}
		return true;
	}
}
