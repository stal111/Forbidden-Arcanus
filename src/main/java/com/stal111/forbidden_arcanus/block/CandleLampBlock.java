package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CandleLampBlock extends WaterloggedBlock {
	
	private static final VoxelShape[] SHAPE = {
			Block.makeCuboidShape(5, 0, 5, 11, 2, 11),
			Block.makeCuboidShape(4, 2, 4, 12, 3, 12),
			Block.makeCuboidShape(3, 3, 3, 13, 13, 13),
			Block.makeCuboidShape(4, 13, 4, 12, 14, 12),
			Block.makeCuboidShape(5, 14, 5, 11, 16, 11)};

	public CandleLampBlock(Properties properties) {
		super(properties.hardnessAndResistance(2.0F, 15.0F).lightValue(15));
	}
	
	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape();
	}
}
