package com.stal111.forbidden_arcanus.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

public class CandleLampBlock extends WaterloggedBlock {
	
	private static final VoxelShape[] SHAPE = {
			Block.box(5, 0, 5, 11, 2, 11),
			Block.box(4, 2, 4, 12, 3, 12),
			Block.box(3, 3, 3, 13, 13, 13),
			Block.box(4, 13, 4, 12, 14, 12),
			Block.box(5, 14, 5, 11, 16, 11)};

	public CandleLampBlock(Properties properties) {
		super(properties.strength(2.0F, 15.0F));
	}
	
	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return this.generateShape();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos,
			CollisionContext context) {
		return this.generateShape();
	}
}
