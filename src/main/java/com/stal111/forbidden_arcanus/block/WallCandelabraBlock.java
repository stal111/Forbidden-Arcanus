package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class WallCandelabraBlock extends Block implements IWaterLoggable {

    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 0, 3);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty DIRECTION = HorizontalBlock.HORIZONTAL_FACING;
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

    private static final VoxelShape[] WALL_SHAPES = {
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[1],
                    Block.makeCuboidShape(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[1],
                    Block.makeCuboidShape(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D),

                    Block.makeCuboidShape(7.0D, 12.0D, 6.0D, 9.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[1],
                    Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 6.0D, 8.0D),
                    Block.makeCuboidShape(4.0D, 6.0D, 6.0D, 12.0D, 8.0D, 8.0D),
                    Block.makeCuboidShape(4.0D, 8.0D, 6.0D, 6.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(10.0D, 7.0D, 6.0D, 12.0D, 10.0D, 8.0D),

                    Block.makeCuboidShape(3.5D, 10.0D, 5.5D, 6.5D, 12.0D, 8.5D),
                    Block.makeCuboidShape(9.5D, 10.0D, 5.5D, 12.5D, 12.0D, 8.5D),

                    Block.makeCuboidShape(4.0D, 12.0D, 6.0D, 6.0D, 16.0D, 8.0D),
                    Block.makeCuboidShape(10.0D, 12.0D, 6.0D, 12.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPES[1],
                    Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 11.0D, 8.0D),
                    Block.makeCuboidShape(2.0D, 6.0D, 6.0D, 14.0D, 8.0D, 8.0D),

                    Block.makeCuboidShape(2.0D, 8.0D, 6.0D, 4.0D, 9.0D, 8.0D),
                    Block.makeCuboidShape(12.0D, 7.0D, 6.0D, 14.0D, 9.0D, 8.0D),

                    Block.makeCuboidShape(1.5D, 9.0D, 5.5D, 4.5D, 11.0D, 8.5D),
                    Block.makeCuboidShape(6.5D, 11.0D, 5.5D, 9.5D, 13.0D, 8.5D),
                    Block.makeCuboidShape(11.5D, 9.0D, 5.5D, 14.5D, 11.0D, 8.5D),

                    Block.makeCuboidShape(2.0D, 11.0D, 6.0D, 4.0D, 14.0D, 8.0D),
                    Block.makeCuboidShape(7.0D, 13.0D, 6.0D, 9.0D, 16.0D, 8.0D),
                    Block.makeCuboidShape(12.0D, 11.0D, 6.0D, 14.0D, 14.0D, 8.0D)
            )
    };

    public WallCandelabraBlock(Properties properties) {
        super(properties.setLightLevel(state -> state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0 ? 12 + state.get(CANDLES) : 0));
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, 0).with(LIT, true).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
    }

    private VoxelShape generateShape(BlockState state) {
        if (state.get(CANDLES) > 0) {
            if (state.get(DIRECTION) == Direction.SOUTH) {
                return WALL_SHAPES[state.get(CANDLES)];
            } else {
                return VoxelShapeHelper.getRotatedShapes(WALL_SHAPES[state.get(CANDLES)]).get(state.get(DIRECTION).getOpposite());
            }
        } else {
            return VoxelShapeHelper.getRotatedShapes(WALL_SHAPES[0]).get(state.get(DIRECTION).getOpposite());
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == ModBlocks.CANDLE.getItem() && state.get(CANDLES) < 3 || super.isReplaceable(state, context);
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public Item asItem() {
        return ForgeRegistries.BLOCKS.getValue(ModUtils.location(this.getRegistryName().getPath().substring(5))).asItem();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        Direction direction = state.get(DIRECTION);
        BlockPos blockpos = pos.offset(direction.getOpposite());
        BlockState blockstate = world.getBlockState(blockpos);
        return blockstate.isSolidSide(world, blockpos, direction) && !world.getBlockState(pos.up()).isSolid();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return generateShape(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        BlockState blockstate = this.getDefaultState().with(WATERLOGGED, flag).with(LIT, !flag);
        IWorldReader iworldreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(DIRECTION, direction1);
                if (blockstate.isValidPosition(iworldreader, blockpos)) {
                    return blockstate;
                }
            }
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
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            if (state.get(LIT)) {
                world.setBlockState(currentPos, state.with(LIT, false), 2);
            }
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return facing.getOpposite() == state.get(DIRECTION) && !state.isValidPosition(world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);

    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(DIRECTION, rot.rotate(state.get(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(DIRECTION)));
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLES, LIT, DIRECTION, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
