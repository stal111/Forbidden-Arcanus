package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractCandelabraBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AbstractCandelabraBlock(Properties properties) {
        super(properties);
    }

    public abstract VoxelShape getShapeForState(BlockState state);

    public abstract int maxCandles();
    public abstract int getCurrentCandles(BlockState state);

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == ModBlocks.CANDLE.getItem() && getCurrentCandles(state) < maxCandles() || super.isReplaceable(state, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShapeForState(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(BlockStateProperties.WATERLOGGED)) {
            if (state.get(BlockStateProperties.LIT)) {
                world.setBlockState(currentPos, state.with(BlockStateProperties.LIT, false), 2);
            }
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return !state.isValidPosition(world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);

    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof FlintAndSteelItem && !state.get(BlockStateProperties.LIT) && !state.get(BlockStateProperties.WATERLOGGED) && getCurrentCandles(state) != 0) {
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(pos, state.with(BlockStateProperties.LIT, true), 11);
            if (player instanceof ServerPlayerEntity) {
                stack.damageItem(1, player, (p_219999_1_) -> p_219999_1_.sendBreakAnimation(hand));
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
