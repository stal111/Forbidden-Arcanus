package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ArcaneBaseBlockRodBlock extends RotatedPillarBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape[] 
			SHAPE_X = {
					Block.makeCuboidShape(0, 6, 6, 2, 10, 10),
					Block.makeCuboidShape(0, 6.5, 6.5, 16, 9.5, 9.5),
					Block.makeCuboidShape(14, 6, 6, 16, 10, 10) },
			SHAPE_Y = {
					Block.makeCuboidShape(6, 0, 6, 10, 2, 10),
					Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 16, 9.5),
					Block.makeCuboidShape(6, 14, 6, 10, 16, 10) },
			SHAPE_Z = { 
					Block.makeCuboidShape(6, 6, 0, 10, 10, 2),
					Block.makeCuboidShape(6.5, 6.5, 0, 9.5, 9.5, 16),
					Block.makeCuboidShape(6, 6, 14, 10, 10, 16) };

	public ArcaneBaseBlockRodBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
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
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return this.generateShape(state);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return this.generateShape(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
		return super.getStateForPlacement(context).with(AXIS, context.getNearestLookingDirection().getAxis())
				.with(WATERLOGGED, Boolean.valueOf(flag));

	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	@SuppressWarnings("deprecation")
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
