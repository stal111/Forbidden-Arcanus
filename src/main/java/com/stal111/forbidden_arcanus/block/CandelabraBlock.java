package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
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

import java.util.Random;

public class CandelabraBlock extends CutoutBlock implements IWaterLoggable {

    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 0, 3);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty X = BooleanProperty.create("x");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape[] BASE_SHAPES = {
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
                    Block.makeCuboidShape(6.5D, 1.0D, 6.5D, 9.5D, 4.0D, 9.5D)),
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(6.0D, 1.5D, 0.0D, 10.0D, 5.5D, 1.0D),
                    Block.makeCuboidShape(7.0D, 2.5D, 1.0D, 9.0D, 4.5D, 5.5D),
                    Block.makeCuboidShape(6.5D, 2.0D, 5.5D, 9.5D, 5.0D, 8.5D))
    };

    private static final VoxelShape[] FLOOR_SHAPES = {
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[0],
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[0],
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D),

                    Block.makeCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[0],
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(4.0D, 5.0D, 7.0D, 12.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(4.0D, 7.0D, 7.0D, 6.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(10.0D, 7.0D, 7.0D, 12.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(3.5D, 10.0D, 6.5D, 6.5D, 12.0D, 9.5D),
                    Block.makeCuboidShape(9.5D, 10.0D, 6.5D, 12.5D, 12.0D, 9.5D),

                    Block.makeCuboidShape(4.0D, 12.0D, 7.0D, 6.0D, 16.0D, 9.0D),
                    Block.makeCuboidShape(10.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[0],
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 11.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 11.0D, 6.5D, 9.5D, 13.0D, 9.5D),
                    Block.makeCuboidShape(2.0D, 5.0D, 7.0D, 14.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(2.0D, 7.0D, 7.0D, 4.0D, 9.0D, 9.0D),
                    Block.makeCuboidShape(12.0D, 7.0D, 7.0D, 14.0D, 9.0D, 9.0D),
                    Block.makeCuboidShape(1.5D, 9.0D, 6.5D, 4.5D, 11.0D, 9.5D),
                    Block.makeCuboidShape(11.5D, 9.0D, 6.5D, 14.5D, 11.0D, 9.5D),

                    Block.makeCuboidShape(2.0D, 11.0D, 7.0D, 4.0D, 14.0D, 9.0D),
                    Block.makeCuboidShape(12.0D, 11.0D, 7.0D, 14.0D, 14.0D, 9.0D),
                    Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D)
            )
    };

    public CandelabraBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, 0).with(LIT, true).with(X, true).with(WATERLOGGED, false));
    }

    private VoxelShape generateShape(BlockState state) {
        if (state.get(CANDLES) > 0) {
            if (!state.get(X)) {
                return VoxelShapeHelper.rotateShape(FLOOR_SHAPES[state.get(CANDLES)], VoxelShapeHelper.RotationAmount.NINETY);
            }
            return FLOOR_SHAPES[state.get(CANDLES)];
        } else {
            return FLOOR_SHAPES[0];
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == ModBlocks.CANDLE.getItem() && state.get(CANDLES) < 3 || super.isReplaceable(state, context);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            if (state.get(LIT)) {
                world.setBlockState(currentPos, state.with(LIT, false), 2);
            }
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return !this.isValidPosition(state, world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return (hasEnoughSolidSide(world, pos.down(), Direction.UP) && (world.getBlockState(pos.up()).getBlock() instanceof AirBlock || world.getFluidState(pos.up()).getFluid() instanceof WaterFluid) && !isCandelabraBlock(world.getBlockState(pos.down())));
    }

    public static boolean isCandelabraBlock(BlockState state) {
        return state.getBlock() instanceof CandelabraBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext context) {
        return generateShape(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        BlockState state = this.getDefaultState().with(X, context.getPlacementHorizontalFacing() == Direction.NORTH || context.getPlacementHorizontalFacing() == Direction.SOUTH).with(WATERLOGGED, flag).with(LIT, !flag);
        if (state.isValidPosition(context.getWorld(), context.getPos())) {
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof FlintAndSteelItem && !state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0) {
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
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0) {
            if (state.get(X)) {
                if (state.get(CANDLES) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.get(CANDLES) == 2) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.31D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.7D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.165D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.85D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (state.get(CANDLES) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.get(CANDLES) == 2) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.31D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.7D, 0.0D, 0.0D, 0.0D);
                } else {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.165D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.85D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0 ? super.getLightValue(state) + 12 + state.get(CANDLES) : 0;
    }

    @Override
    public PushReaction getPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(CANDLES, LIT, X, WATERLOGGED);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
