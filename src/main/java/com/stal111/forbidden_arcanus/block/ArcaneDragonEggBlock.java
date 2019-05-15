package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ArcaneDragonEggBlock extends FallingWaterloggedBlock {
	
	private static final VoxelShape[] SHAPE = { 
			Block.makeCuboidShape(3, 0, 3, 13, 13, 13),
			Block.makeCuboidShape(2, 1, 2, 14, 11, 14), 
			Block.makeCuboidShape(1, 3, 1, 15, 8, 15),
			Block.makeCuboidShape(5, 13, 5, 11, 15, 11),
			Block.makeCuboidShape(6, 15, 6, 10, 16, 10)};

	public ArcaneDragonEggBlock(String name, Properties properties) {
		super(name, properties);
	}
	
	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}
	
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.generateShape();
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.generateShape();
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return 9;
	}

}
