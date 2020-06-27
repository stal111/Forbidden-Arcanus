//package com.stal111.forbidden_arcanus.world.gen.feature;
//
//import java.util.Random;
//import java.util.function.Function;
//
//import com.mojang.datafixers.Dynamic;
//
//import com.stal111.forbidden_arcanus.block.EdelwoodLogBlock;
//import com.stal111.forbidden_arcanus.init.ModBlocks;
//import net.minecraft.block.AirBlock;
//import net.minecraft.block.Blocks;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IWorld;
//import net.minecraft.world.gen.ChunkGenerator;
//import net.minecraft.world.gen.GenerationSettings;
//import net.minecraft.world.gen.feature.Feature;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//
//public class EdelwoodLogFeature extends Feature<NoFeatureConfig> {
//
//	public EdelwoodLogFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
//		super(configFactoryIn);
//	}
//
//	@Override
//	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config) {
//		for (int lvt_6_1_ = 0; lvt_6_1_ < 10; ++lvt_6_1_) {
//			BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
//			if (world.isAirBlock(pos1)) {
//				int lvt_8_1_ = 2 + random.nextInt(random.nextInt(2) + 1);
//
//				for (int lvt_9_1_ = 0; lvt_9_1_ < lvt_8_1_; ++lvt_9_1_) {
//					if (world.getBlockState(pos1.down()).getBlock() == Blocks.GRASS_BLOCK) {
//						if (world.getBlockState(pos1.up((lvt_9_1_ + 1))).getBlock() instanceof AirBlock) {
//							world.setBlockState(pos1.up(lvt_9_1_), ModBlocks.EDELWOOD_LOG.getState().with(EdelwoodLogBlock.LEAVES, true), 2);
//						}
//					}
//				}
//			}
//		}
//		return true;
//	}
//}
