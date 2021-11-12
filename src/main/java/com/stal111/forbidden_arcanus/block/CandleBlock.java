package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class CandleBlock extends Block implements SimpleWaterloggedBlock {

	public static final IntegerProperty CANDLES = ModBlockStateProperties.CANDLES_1_3;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private static final List<VoxelShape> SHAPES = Arrays.asList(
			Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D),
			Block.box(4.0D, 0.0D, 3.0D, 12.0D, 10.0D, 12.0D),
			Block.box(3.0D, 0.0D, 2.0D, 14.0D, 10.0D, 13.0D));

	public CandleBlock(Properties properties) {
		super(properties.noCollission().lightLevel(state -> state.getValue(LIT) && !state.getValue(WATERLOGGED) ? 12 + state.getValue(CANDLES) : 0));
		this.registerDefaultState(this.stateDefinition.any().setValue(CANDLES, 1).setValue(LIT, true).setValue(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());

		if (state.is(this)) {
			return state.setValue(CANDLES, Math.min(3, state.getValue(CANDLES) + 1));
		} else if (state.is(ModTags.Blocks.STANDING_CANDELABRAS) || state.is(ModTags.Blocks.WALL_CANDELABRAS)) {
			return state.setValue(ModBlockStateProperties.CANDLES_0_3, Math.min(3, state.getValue(ModBlockStateProperties.CANDLES_0_3) + 1));
		} else if (state.is(ModTags.Blocks.HANGING_CANDELABRAS)) {
			return state.setValue(ModBlockStateProperties.CANDLE, true);
		}

		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(WATERLOGGED, flag).setValue(LIT, !flag);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return !state.canSurvive(world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return ModUtils.hasBlockEnoughSolidSite(SHAPES.get(state.getValue(CANDLES) - 1), world, pos.below(), Direction.UP);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof FlintAndSteelItem && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
			world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
			world.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 11);
			stack.getItem().damageItem(stack, 1, player, (p_219998_1_) -> {
				player.broadcastBreakEvent(hand);
			});
			return InteractionResult.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return context.getItemInHand().getItem() == this.asItem() && state.getValue(CANDLES) < 4 || super.canBeReplaced(state, context);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(CANDLES) - 1);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		if (state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
			if (state.getValue(CANDLES) == 1) {
				world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 0.52D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
			} else if (state.getValue(CANDLES) == 2) {
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
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CANDLES, LIT, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
