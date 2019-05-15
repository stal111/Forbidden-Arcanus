package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class EdelwoodLogBlock extends ModLogBlock implements IBucketPickupHandler, ILiquidContainer {

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
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, EnumFacing.Axis.Y).with(WATERLOGGED,
				Boolean.valueOf(false)));
	}

	private VoxelShape generateShape(IBlockState state) {
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
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(AXIS, WATERLOGGED);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.generateShape(state);
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader reader, BlockPos pos) {
		return this.generateShape(state);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8;
		return super.getStateForPlacement(context).with(AXIS, context.getNearestLookingDirection().getAxis())
				.with(WATERLOGGED, Boolean.valueOf(flag));

	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState updatePostPlacement(IBlockState stateIn, EnumFacing facing, IBlockState facingState,
			IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public Fluid pickupFluid(IWorld worldIn, BlockPos pos, IBlockState state) {
		if (state.get(WATERLOGGED)) {
			worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
			return Fluids.WATER;
		} else {
			return Fluids.EMPTY;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public IFluidState getFluidState(IBlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, IBlockState state, Fluid fluidIn) {
		return !state.get(WATERLOGGED) && fluidIn == Fluids.WATER;
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, IBlockState state, IFluidState fluidStateIn) {
		if (!state.get(WATERLOGGED) && fluidStateIn.getFluid() == Fluids.WATER) {
			if (!worldIn.isRemote()) {
				worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
				worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(),
						fluidStateIn.getFluid().getTickRate(worldIn));
			}

			return true;
		} else {
			return false;
		}
	}

}
