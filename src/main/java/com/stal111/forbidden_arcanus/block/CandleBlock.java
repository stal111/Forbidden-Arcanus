package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandleBlock extends Block implements IWaterLoggable {

	public static final IntegerProperty CANDLES = ModBlockStateProperties.CANDLES_1_3;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final List<VoxelShape> SHAPES = Arrays.asList(
			Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D),
			Block.makeCuboidShape(4.0D, 0.0D, 3.0D, 12.0D, 10.0D, 12.0D),
			Block.makeCuboidShape(3.0D, 0.0D, 2.0D, 14.0D, 10.0D, 13.0D));

	public CandleBlock(Properties properties) {
		super(properties.doesNotBlockMovement().setLightLevel(state -> state.get(LIT) && !state.get(WATERLOGGED) ? 12 + state.get(CANDLES) : 0));
		this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, 1).with(LIT, true).with(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = context.getWorld().getBlockState(context.getPos());

		if (state.isIn(this)) {
			return state.with(CANDLES, Math.min(3, state.get(CANDLES) + 1));
		} else if (state.isIn(ModTags.Blocks.STANDING_CANDELABRAS) || state.isIn(ModTags.Blocks.WALL_CANDELABRAS)) {
			return state.with(ModBlockStateProperties.CANDLES_0_3, Math.min(3, state.get(ModBlockStateProperties.CANDLES_0_3) + 1));
		} else if (state.isIn(ModTags.Blocks.HANGING_CANDELABRAS)) {
			return state.with(ModBlockStateProperties.CANDLE, true);
		}

		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER;
		return super.getStateForPlacement(context).with(WATERLOGGED, flag).with(LIT, !flag);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return !state.isValidPosition(world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		return ModUtils.hasBlockEnoughSolidSite(SHAPES.get(state.get(CANDLES) - 1), world, pos.down(), Direction.UP);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() instanceof FlintAndSteelItem && !state.get(LIT) && !state.get(WATERLOGGED)) {
			world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
			world.setBlockState(pos, state.with(BlockStateProperties.LIT, true), 11);
			stack.getItem().damageItem(stack, 1, player, (p_219998_1_) -> {
				player.sendBreakAnimation(hand);
			});
			return ActionResultType.SUCCESS;
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
		return context.getItem().getItem() == this.asItem() && state.get(CANDLES) < 4 || super.isReplaceable(state, context);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPES.get(state.get(CANDLES) - 1);
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
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(CANDLES, LIT, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
