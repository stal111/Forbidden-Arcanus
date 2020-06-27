package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ConnectedBlockType;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class PillarBlock extends WaterloggedBlock {

	public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

	private static final VoxelShape[]
			SHAPE = {
			Block.makeCuboidShape(0, 14, 0, 16, 16, 16),
			Block.makeCuboidShape(1, 13, 1, 15, 14, 15),
			Block.makeCuboidShape(2, 0, 2, 14, 16, 14),
			Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
			Block.makeCuboidShape(1, 2, 1, 15, 3, 15) };

	private VoxelShape generateShape(BlockState state) {
		if (state.get(TYPE) == ConnectedBlockType.SINGLE) {
			return VoxelShapeHelper.rotateShapeAxis(VoxelShapeHelper.combineAll(SHAPE), state.get(AXIS));
		} else if (state.get(TYPE) == ConnectedBlockType.TOP) {
			return VoxelShapeHelper.rotateShapeAxis(VoxelShapeHelper.combineAll(SHAPE[0], SHAPE[1], SHAPE[2]), state.get(AXIS));
		} else if (state.get(TYPE) == ConnectedBlockType.CENTER) {
			return VoxelShapeHelper.rotateShapeAxis(SHAPE[2], state.get(AXIS));
		} else {
			return VoxelShapeHelper.rotateShapeAxis(VoxelShapeHelper.combineAll(SHAPE[2], SHAPE[3], SHAPE[4]), state.get(AXIS));
		}
	}

	public PillarBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y).with(TYPE, ConnectedBlockType.SINGLE).with(WATERLOGGED, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, AXIS, WATERLOGGED);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return this.generateShape(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = fluidstate.isTagged(FluidTags.WATER) && fluidstate.getLevel() == 8;
		return super.getStateForPlacement(context).with(TYPE, ConnectedBlockType.SINGLE).with(AXIS, context.getFace().getAxis()).with(WATERLOGGED, flag);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return state.with(TYPE, this.tryConnect(state, world, currentPos));
	}

	public ConnectedBlockType tryConnect(BlockState state, IWorld world, BlockPos pos) {
		Direction.Axis axis = state.get(AXIS);
		BlockState stateDown;
		BlockState stateUp;

		if (axis == Direction.Axis.X) {
			stateDown = world.getBlockState(pos.west());
			stateUp = world.getBlockState(pos.east());
		} else if (axis == Direction.Axis.Z) {
			stateDown = world.getBlockState(pos.south());
			stateUp = world.getBlockState(pos.north());
		} else {
			stateDown = world.getBlockState(pos.down());
			stateUp = world.getBlockState(pos.up());
		}

		boolean axisUpEqual = false;
		boolean axisDownEqual = false;

		if (stateUp.getBlock() instanceof PillarBlock) {
			axisUpEqual = stateUp.get(AXIS) == axis;
		}
		if (stateDown.getBlock() instanceof PillarBlock) {
			axisDownEqual = stateDown.get(AXIS) == axis;
		}

		if (axisUpEqual && axisDownEqual) {
			return ConnectedBlockType.CENTER;
		} else if (axisUpEqual) {
			return ConnectedBlockType.BOTTOM;
		} else if (axisDownEqual) {
			return ConnectedBlockType.TOP;
		} else {
			return ConnectedBlockType.SINGLE;
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(rotation) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch(state.get(AXIS)) {
					case X:
						return state.with(AXIS, Direction.Axis.Z);
					case Z:
						return state.with(AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}
}
