package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ConnectedBlockType;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PillarBlock extends Block implements IWaterLoggable {

	public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape[] SHAPE = {
			Block.makeCuboidShape(0, 14, 0, 16, 16, 16),
			Block.makeCuboidShape(1, 13, 1, 15, 14, 15),
			Block.makeCuboidShape(2, 0, 2, 14, 16, 14),
			Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
			Block.makeCuboidShape(1, 2, 1, 15, 3, 15)
	};

	private VoxelShape getShapeForState(BlockState state) {
		List<VoxelShape> list = new ArrayList<>();
		list.add(SHAPE[2]);

		switch (state.get(TYPE)) {
			case TOP: list.addAll(Arrays.asList(SHAPE[0], SHAPE[1])); break;
			case BOTTOM: list.addAll(Arrays.asList(SHAPE[3], SHAPE[4])); break;
			case SINGLE: list = Arrays.asList(SHAPE); break;
		}

		return VoxelShapeHelper.rotateShapeAxis(VoxelShapeHelper.combineAll(list), state.get(AXIS));
	}

	public PillarBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y).with(TYPE, ConnectedBlockType.SINGLE).with(WATERLOGGED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return this.getShapeForState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER;
		return super.getStateForPlacement(context).with(TYPE, ConnectedBlockType.SINGLE).with(AXIS, context.getFace().getAxis()).with(WATERLOGGED, flag);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos).with(TYPE, tryConnect(state, world, currentPos));
	}

	public ConnectedBlockType tryConnect(BlockState state, IWorld world, BlockPos pos) {
		Direction.Axis axis = state.get(AXIS);

		BlockState stateDown = world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.NEGATIVE, axis)));
		BlockState stateUp = world.getBlockState(pos.offset(Direction.getFacingFromAxis(Direction.AxisDirection.POSITIVE, axis)));

		boolean axisUpEqual = stateUp.getBlock() instanceof PillarBlock && stateUp.get(AXIS) == axis;
		boolean axisDownEqual = stateDown.getBlock() instanceof PillarBlock && stateDown.get(AXIS) == axis;

		return ConnectedBlockType.getTypeForConnections(axisUpEqual, axisDownEqual, axis);
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

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, AXIS, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
