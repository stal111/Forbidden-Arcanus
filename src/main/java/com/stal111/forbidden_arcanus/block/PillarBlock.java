package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ConnectedBlockType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PillarBlock extends Block implements SimpleWaterloggedBlock {

	public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final VoxelShape[] SHAPE = {
			Block.box(0, 14, 0, 16, 16, 16),
			Block.box(1, 13, 1, 15, 14, 15),
			Block.box(2, 0, 2, 14, 16, 14),
			Block.box(0, 0, 0, 16, 2, 16),
			Block.box(1, 2, 1, 15, 3, 15)
	};

	private VoxelShape getShapeForState(BlockState state) {
		List<VoxelShape> list = new ArrayList<>();
		list.add(SHAPE[2]);

		switch (state.getValue(TYPE)) {
			case TOP: list.addAll(Arrays.asList(SHAPE[0], SHAPE[1])); break;
			case BOTTOM: list.addAll(Arrays.asList(SHAPE[3], SHAPE[4])); break;
			case SINGLE: list = Arrays.asList(SHAPE); break;
		}

		return VoxelShapeHelper.rotateShape(VoxelShapeHelper.combineAll(list), state.getValue(AXIS) == Direction.Axis.X ? Direction.EAST : Direction.SOUTH);
	}

	public PillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(TYPE, ConnectedBlockType.SINGLE).setValue(WATERLOGGED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return this.getShapeForState(state);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(TYPE, ConnectedBlockType.SINGLE).setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, flag);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos).setValue(TYPE, tryConnect(state, world, currentPos));
	}

	public ConnectedBlockType tryConnect(BlockState state, LevelAccessor world, BlockPos pos) {
		Direction.Axis axis = state.getValue(AXIS);

		BlockState stateDown = world.getBlockState(pos.relative(Direction.get(Direction.AxisDirection.NEGATIVE, axis)));
		BlockState stateUp = world.getBlockState(pos.relative(Direction.get(Direction.AxisDirection.POSITIVE, axis)));

		boolean axisUpEqual = stateUp.getBlock() instanceof PillarBlock && stateUp.getValue(AXIS) == axis;
		boolean axisDownEqual = stateDown.getBlock() instanceof PillarBlock && stateDown.getValue(AXIS) == axis;

		return ConnectedBlockType.getTypeForConnections(axisUpEqual, axisDownEqual, axis);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(rotation) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch(state.getValue(AXIS)) {
					case X:
						return state.setValue(AXIS, Direction.Axis.Z);
					case Z:
						return state.setValue(AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TYPE, AXIS, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
