package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandleBlock extends CutoutBlock implements IWaterLoggable {

	private static final List<VoxelShape> SHAPES = Arrays.asList(
			Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D),
			Block.makeCuboidShape(4.0D, 0.0D, 3.0D, 12.0D, 10.0D, 12.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 14.0D, 10.0D, 13.0D));

	public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 3);;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public CandleBlock(Properties properties) {
		super(properties.doesNotBlockMovement().lightValue(15));
		this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, 1).with(LIT, true).with(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = context.getWorld().getBlockState(context.getPos());
		boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
		if (state.getBlock() == this) {
			return state.with(CANDLES, Math.min(3, state.get(CANDLES) + 1));
		} else if (state.getBlock() instanceof CandelabraBlock) {
			return state.with(CandelabraBlock.CANDLES, Math.min(3, state.get(CandelabraBlock.CANDLES) + 1));
		} else {
			return this.getDefaultState().with(WATERLOGGED, flag).with(LIT, !flag).with(CANDLES, 1);
		}
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return !this.isValidPosition(state, world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() instanceof FlintAndSteelItem && !state.get(LIT) && !state.get(WATERLOGGED)) {
			world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
			world.setBlockState(pos, state.with(BlockStateProperties.LIT, true), 11);
			stack.getItem().damageItem(stack, 1, player, (p_219998_1_) -> {
				player.sendBreakAnimation(hand);
			});
			return true;
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
		return context.getItem().getItem() == this.asItem() && state.get(CANDLES) < 4 ? true : super.isReplaceable(state, context);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(CANDLES) - 1);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		return (func_220055_a(world, pos.down(), Direction.UP)) && !CandelabraBlock.getCandelabraBlocks().contains(world.getBlockState(pos.down()).getBlock());
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(LIT) && !state.get(WATERLOGGED)) {
			if (state.get(CANDLES) == 1) {
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 0.52D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			} else if (state.get(CANDLES) == 2) {
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.41D, pos.getY() + 0.62D, pos.getZ() + 0.35D, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.62D, pos.getY() + 0.52D, pos.getZ() + 0.65D, 0.0D, 0.0D, 0.0D);
			} else {
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.36D, pos.getY() + 0.65D, pos.getZ() + 0.45D, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.76D, pos.getY() + 0.52D, pos.getZ() + 0.69D, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.69D, pos.getY() + 0.42D, pos.getZ() + 0.25D, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
		return state.get(LIT) && !state.get(WATERLOGGED) ? super.getLightValue(state, world, pos) : 0;
	}

	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(CANDLES, LIT, WATERLOGGED);
	}

	@Override
	public boolean receiveFluid(IWorld p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, IFluidState p_204509_4_) {
		if (!p_204509_3_.get(BlockStateProperties.WATERLOGGED) && p_204509_4_.getFluid() == Fluids.WATER) {
			p_204509_1_.setBlockState(p_204509_2_, (p_204509_3_.with(WATERLOGGED, true)).with(LIT, false), 3);
			p_204509_1_.getPendingFluidTicks().scheduleTick(p_204509_2_, p_204509_4_.getFluid(), p_204509_4_.getFluid().getTickRate(p_204509_1_));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IFluidState getFluidState(BlockState p_204507_1_) {
		return p_204507_1_.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(p_204507_1_);
	}
}
