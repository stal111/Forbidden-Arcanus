package com.stal111.forbidden_arcanus.block;

import java.util.Random;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModOreBlock extends OreBlock {

	public ModOreBlock(Properties properties) {
		super(properties);
	}
	
	protected int xpOnDrop(Random p_220281_1_) {
	      if (this == NewModBlocks.ARCANE_CRYSTAL_ORE.get()) {
	         return Mth.nextInt(p_220281_1_, 2, 5);
	      } else if (this == ModBlocks.RUNESTONE.getBlock()) {
	         return Mth.nextInt(p_220281_1_, 4, 8);
	      } else if (this == ModBlocks.DARK_RUNESTONE.getBlock()) {
	         return Mth.nextInt(p_220281_1_, 5, 9);
	      } else {
	         return 0;
	      }
	   }
	
	@Override
	public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
		  return silktouch == 0 ? this.xpOnDrop(RANDOM) : 0;
	}
}
