//package com.stal111.forbidden_arcanus.block;
//
//import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockDirectional;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.fluid.IFluidState;
//import net.minecraft.init.Fluids;
//import net.minecraft.item.BlockItemUseContext;
//import net.minecraft.state.DirectionProperty;
//import net.minecraft.state.EnumProperty;
//import net.minecraft.state.StateContainer;
//import net.minecraft.tags.FluidTags;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.IStringSerializable;
//import net.minecraft.util.Rotation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.shapes.VoxelShape;
//import net.minecraft.util.math.shapes.VoxelShapes;
//import net.minecraft.world.IBlockReader;
//import net.minecraft.world.IWorld;
//
//public class PillarBlock extends WaterloggedBlock {
//
//	public static final EnumProperty<PillarType> TYPE = EnumProperty.create("type", PillarType.class);
//	public static final DirectionProperty FACING = BlockDirectional.FACING;
//
//	private static final VoxelShape[]
//			SHAPE_X = { 
//				Block.makeCuboidShape(0, 0, 0, 2, 16, 16), Block.makeCuboidShape(2, 1, 1, 3, 15, 15), Block.makeCuboidShape(0, 2, 2, 16, 14, 14), Block.makeCuboidShape(13, 1, 1, 14, 15, 15), Block.makeCuboidShape(14, 0, 0, 16, 16, 16) },
//			SHAPE_Y = { 
//				Block.makeCuboidShape(0, 14, 0, 16, 16, 16), Block.makeCuboidShape(1, 13, 1, 15, 14, 15), Block.makeCuboidShape(2, 0, 2, 14, 16, 14), Block.makeCuboidShape(0, 0, 0, 16, 2, 16), Block.makeCuboidShape(1, 2, 1, 15, 3, 15) },
//			SHAPE_Z = {
//				Block.makeCuboidShape(0, 0, 0, 16, 16, 2), Block.makeCuboidShape(1, 1, 2, 15, 15, 3), Block.makeCuboidShape(2, 2, 0, 14, 14, 16), Block.makeCuboidShape(1, 1, 13, 15, 15, 14), Block.makeCuboidShape(0, 0, 14, 16, 16, 16) };
//	
//	private VoxelShape generateShape(IBlockState state) {
//		switch (state.get(TYPE)) {
//		case SINGLE:
//			switch (state.get(FACING)) {
//			case EAST: return VoxelShapeHelper.combineAll(SHAPE_X);
//			case WEST: return VoxelShapeHelper.combineAll(SHAPE_X);
//			case NORTH: return VoxelShapeHelper.combineAll(SHAPE_Z);
//			case SOUTH: return VoxelShapeHelper.combineAll(SHAPE_Z);
//			default: return VoxelShapeHelper.combineAll(SHAPE_Y);
//			}
//		case TOP:
//			switch (state.get(FACING)) {
//			case EAST: return VoxelShapeHelper.combineAll(SHAPE_X[0], SHAPE_X[1], SHAPE_X[2]);
//			case WEST: return VoxelShapeHelper.combineAll(SHAPE_X[2], SHAPE_X[3], SHAPE_X[4]);
//			case NORTH: return VoxelShapeHelper.combineAll(SHAPE_Z[2], SHAPE_Z[3], SHAPE_Z[4]);
//			case SOUTH: return VoxelShapeHelper.combineAll(SHAPE_Z[0], SHAPE_Z[1], SHAPE_Z[2]);
//			default: return VoxelShapeHelper.combineAll(SHAPE_Y[2], SHAPE_Y[3], SHAPE_Y[4]);
//			}
//		case MIDDLE:
//			switch (state.get(FACING)) {
//			case EAST: return VoxelShapeHelper.combineAll(SHAPE_X[2]);
//			case WEST: return VoxelShapeHelper.combineAll(SHAPE_X[2]);
//			case NORTH: return VoxelShapeHelper.combineAll(SHAPE_Z[2]);
//			case SOUTH: return VoxelShapeHelper.combineAll(SHAPE_Z[2]);
//			default: return VoxelShapeHelper.combineAll(SHAPE_Y[2]);
//			}
//		case BOTTOM :
//			switch (state.get(FACING)) {
//			case EAST: return VoxelShapeHelper.combineAll(SHAPE_X[2], SHAPE_X[3], SHAPE_X[4]);
//			case WEST: return VoxelShapeHelper.combineAll(SHAPE_X[0], SHAPE_X[1], SHAPE_X[2]);
//			case NORTH: return VoxelShapeHelper.combineAll(SHAPE_Z[0], SHAPE_Z[1], SHAPE_Z[2]);
//			case SOUTH: return VoxelShapeHelper.combineAll(SHAPE_Z[2], SHAPE_Z[3], SHAPE_Z[4]);
//			default: return VoxelShapeHelper.combineAll(SHAPE_Y[0], SHAPE_Y[1], SHAPE_Y[2]);
//			}
//		}
//		return VoxelShapes.fullCube();
//	}
//
//	public PillarBlock(String name, Properties properties) {
//		super(name, properties);
//		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.UP)
//				.with(TYPE, PillarType.SINGLE).with(WATERLOGGED, Boolean.valueOf(false)));
//	}
//
//	@Override
//	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
//		builder.add(TYPE, FACING, WATERLOGGED);
//	}
//
//	@Override
//	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
//		return this.generateShape(state);
//	}
//	
//	@Override
//	public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
//		return this.generateShape(state);
//	}
//
//	@Override
//	public IBlockState getStateForPlacement(BlockItemUseContext context) {
//		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
//		boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
//		return super.getStateForPlacement(context).with(TYPE, PillarType.SINGLE)
//				.with(FACING, context.getFace())
//				.with(WATERLOGGED, Boolean.valueOf(flag));
//	}
//
//	@Override
//	public IBlockState updatePostPlacement(IBlockState state, EnumFacing facing, IBlockState facingState, IWorld world,
//			BlockPos currentPos, BlockPos facingPos) {
//		if (state.get(WATERLOGGED)) {
//			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
//		}
//		
//		return state.with(TYPE, this.tryConnect(state, world, currentPos));
//		
//	}
//	
//	public PillarType tryConnect(IBlockState state, IWorld world, BlockPos currentPos) {
//		IBlockState stateDown = world.getBlockState(currentPos.offset((EnumFacing) state.get(FACING)));
//		IBlockState stateUp = world.getBlockState(currentPos.offset((EnumFacing) state.get(FACING), -1));
//		if (stateUp.getBlock() == this && stateDown.getBlock() == this) {
//			boolean blockUp = stateUp.get(FACING).getAxis() == state.get(FACING).getAxis();
//			boolean blockDown = stateDown.get(FACING).getAxis() == state.get(FACING).getAxis();
//			if (blockUp && blockDown) {
//				return PillarType.MIDDLE;
//			} else if (blockUp && !blockDown) {
//				return PillarType.BOTTOM;
//			} else if (!blockUp && blockDown) {
//				return PillarType.TOP;
//			} else {
//				return PillarType.SINGLE;
//			}
//		} else if (stateUp.getBlock() == this && stateDown.getBlock() != this) {
//			boolean blockUp = stateUp.get(FACING).getAxis() == state.get(FACING).getAxis();
//			if (blockUp) {
//				return PillarType.BOTTOM;
//			} else {
//				return PillarType.SINGLE;
//			}
//		} else if (stateUp.getBlock() != this && stateDown.getBlock() == this) {
//			boolean blockDown = stateDown.get(FACING).getAxis() == state.get(FACING).getAxis();
//			if (blockDown) {
//				return PillarType.TOP;
//			} else {
//				return PillarType.SINGLE;
//			}
//		} else {
//			return PillarType.SINGLE;
//		}
//	}
//
//	@Override
//	public IBlockState rotate(IBlockState state, Rotation rot) {
//		switch (rot) {
//		case COUNTERCLOCKWISE_90:
//		case CLOCKWISE_90:
//			switch ((EnumFacing) state.get(FACING)) {
//			case NORTH:
//				return state.with(FACING, EnumFacing.WEST);
//			case EAST:
//				return state.with(FACING, EnumFacing.SOUTH);
//			case SOUTH:
//				return state.with(FACING, EnumFacing.EAST);
//			case WEST:
//				return state.with(FACING, EnumFacing.NORTH);
//			case UP:
//				return state.with(FACING, EnumFacing.UP);
//			case DOWN:
//				return state.with(FACING, EnumFacing.DOWN);
//			default:
//				return state;
//			}
//		default:
//			return state;
//		}
//	}
//
//	@Override
//	public boolean isTopSolid(IBlockState state) {
//		return true;
//	}
//	
//	public enum PillarType implements IStringSerializable {
//		SINGLE("single"),
//		TOP("top"),
//		MIDDLE("middle"),
//		BOTTOM("bottom");
//		
//		public final String type;
//		
//		private PillarType(String name) {
//			this.type = name;
//		}
//
//		@Override
//		public String getName() {
//			return type;
//		}
//		
//	}
//
//}
