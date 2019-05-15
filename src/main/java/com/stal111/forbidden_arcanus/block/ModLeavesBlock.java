package com.stal111.forbidden_arcanus.block;

import java.util.Random;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ModLeavesBlock extends BlockLeaves {

	public ModLeavesBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
	
	@Override
	public void tick(IBlockState state, World worldIn, BlockPos pos, Random random) {
		worldIn.setBlockState(pos, updateDistance(state, worldIn, pos), 3);
	}
	
	@Override
	public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState,
			IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		 int i = getDistance(facingState) + 1;
	      if (i != 1 || stateIn.get(DISTANCE) != i) {
	         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
	      }

	      return stateIn;
	}
	
	private static IBlockState updateDistance(IBlockState p_208493_0_, IWorld p_208493_1_, BlockPos p_208493_2_) {
	      int i = 7;

	      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
	         for(EnumFacing enumfacing : EnumFacing.values()) {
	            blockpos$pooledmutableblockpos.setPos(p_208493_2_).move(enumfacing);
	            i = Math.min(i, getDistance(p_208493_1_.getBlockState(blockpos$pooledmutableblockpos)) + 1);
	            if (i == 1) {
	               break;
	            }
	         }
	      }

	      return p_208493_0_.with(DISTANCE, Integer.valueOf(i));
	   }

	private static int getDistance(IBlockState neighbor) {
		if (BlockTags.LOGS.contains(neighbor.getBlock()) || neighbor.getBlock() instanceof ModLogBlock) {
			return 0;
		} else {
			return neighbor.getBlock() instanceof BlockLeaves || neighbor.getBlock() instanceof BlockLeaves ? neighbor.get(DISTANCE) : 7;
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
	      return updateDistance(this.getDefaultState().with(PERSISTENT, Boolean.valueOf(true)), context.getWorld(), context.getPos());

	}

}
