package com.stal111.forbidden_arcanus.block;

import java.util.Random;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class ModOreBlock extends OreBlock {

	public ModOreBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
	
	protected int func_220281_a(Random p_220281_1_) {
	      if (this == ModBlocks.arcane_crystal_ore) {
	         return MathHelper.nextInt(p_220281_1_, 2, 5);
	      } else if (this == ModBlocks.runestone) {
	         return MathHelper.nextInt(p_220281_1_, 4, 8);
	      } else if (this == ModBlocks.dark_runestone) {
	         return MathHelper.nextInt(p_220281_1_, 5, 9);
	      } else {
	         return 0;
	      }
	   }
	
	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		  return silktouch == 0 ? this.func_220281_a(RANDOM) : 0;
	}
}
