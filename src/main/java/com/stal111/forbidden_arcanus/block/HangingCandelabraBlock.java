package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
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
import java.util.Random;

public class HangingCandelabraBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty CANDLE = BooleanProperty.create("candle");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 12.0D, 6.0D, 10.0D, 13.0D, 10.0D),
            Block.makeCuboidShape(5.0D, 3.0D, 5.0D, 11.0D, 12.0D, 11.0D));

    public HangingCandelabraBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLE, false).with(LIT, true).with(WATERLOGGED, false));
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == ModBlocks.CANDLE.getItem() && !state.get(CANDLE) || super.isReplaceable(state, context);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return Block.hasEnoughSolidSide(world, pos.up(), Direction.DOWN);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        return this.getDefaultState().with(WATERLOGGED, flag).with(LIT, !flag);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof FlintAndSteelItem && !state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLE)) {
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(pos, state.with(LIT, true), 11);
            if (player instanceof ServerPlayerEntity) {
                stack.damageItem(1, player, (p_219999_1_) -> p_219999_1_.sendBreakAnimation(hand));
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            if (state.get(LIT)) {
                world.setBlockState(currentPos, state.with(LIT, false), 2);
            }
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return facing.getOpposite() == Direction.DOWN && !state.isValidPosition(world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);

    }

    //TODO
//    @Override
//    public int getLightValue(BlockState state) {
//        return state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLE) ? super.getLightValue(state) + 14 : 0;
//    }

    @Override
    public PushReaction getPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLE, LIT, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
