package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BeaconBaseBlock extends BasicBlock {

	public BeaconBaseBlock(String name, Properties properties) {
		super(name, properties);
	}
	
	@Override
	public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
		return true;
	}

}
