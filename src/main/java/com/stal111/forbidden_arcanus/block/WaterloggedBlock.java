package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class WaterloggedBlock extends CutoutBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public WaterloggedBlock(String name, Properties properties) {
		super(name, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return super.getStateForPlacement(context).with(WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world,
			BlockPos currentPos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
