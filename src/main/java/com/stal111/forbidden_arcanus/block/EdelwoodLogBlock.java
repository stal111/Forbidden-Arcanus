package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class EdelwoodLogBlock extends ModLogBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private static final VoxelShape[]
			SHAPE_X = {
					Block.makeCuboidShape(0, 0, 3, 16, 2, 13),  Block.makeCuboidShape(0, 14, 3, 16, 16, 13), Block.makeCuboidShape(0, 3, 0, 16, 13, 2), Block.makeCuboidShape(0, 3, 14, 16, 13, 16),
					Block.makeCuboidShape(0, 1, 1, 16, 4, 4), Block.makeCuboidShape(0, 1, 12, 16, 4, 15), Block.makeCuboidShape(0, 12, 1, 16, 15, 4), Block.makeCuboidShape(0, 12, 12, 16, 15, 15)},
			SHAPE_Y = {
					Block.makeCuboidShape(3, 0, 0, 13, 16, 2), Block.makeCuboidShape(3, 0, 14, 13, 16, 16), Block.makeCuboidShape(0, 0, 3, 2, 16, 13), Block.makeCuboidShape(14, 0, 3, 16, 16, 13),
					Block.makeCuboidShape(1, 0, 1, 4, 16, 4), Block.makeCuboidShape(12, 0, 1, 15, 16, 4), Block.makeCuboidShape(1, 0, 12, 4, 16, 15), Block.makeCuboidShape(12, 0, 12, 15, 16, 15)},
			SHAPE_Z = {
					Block.makeCuboidShape(3, 0, 0, 13, 2, 16), Block.makeCuboidShape(3, 14, 0, 13, 16, 16), Block.makeCuboidShape(0, 3, 0, 2, 13, 16), Block.makeCuboidShape(14, 3, 0, 16, 13, 16),
					Block.makeCuboidShape(1, 1, 0, 4, 4, 16), Block.makeCuboidShape(12, 1, 0, 15, 4, 16), Block.makeCuboidShape(1, 12, 0, 4, 15, 16), Block.makeCuboidShape(12, 12, 0, 15, 15, 16)};

	public EdelwoodLogBlock(String name, MaterialColor color, Properties properties) {
		super(name, color, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y).with(WATERLOGGED,
				Boolean.valueOf(false)));
	}

	private VoxelShape generateShape(BlockState state) {
		switch (state.get(AXIS)) {
		case X:
			return VoxelShapeHelper.combineAll(SHAPE_X);
		case Y:
			return VoxelShapeHelper.combineAll(SHAPE_Y);
		case Z:
			return VoxelShapeHelper.combineAll(SHAPE_Z);
		}
		return VoxelShapes.fullCube();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS, WATERLOGGED);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape(state);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return super.getStateForPlacement(context).with(AXIS, context.getFace().getAxis())
				.with(WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8));

	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState,
			IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
