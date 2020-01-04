package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ConnectedBlockType;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainBlock extends CutoutBlock implements IWaterLoggable {

    public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public ChainBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(TYPE, ConnectedBlockType.SINGLE).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (hand == Hand.MAIN_HAND) {
            if (getChainBlocks().contains(Block.getBlockFromItem(stack.getItem()))) {
                if (pullDown(stack.getItem(), world, pos)) {
                    ItemStackUtils.shrinkStack(player, stack);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.func_225533_a_(state, world, pos, player, hand, result);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return !this.isValidPosition(state, world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockState stateUp = world.getBlockState(pos.up());
        if (state.get(TYPE) == ConnectedBlockType.TOP || state.get(TYPE) == ConnectedBlockType.SINGLE) {
            return stateUp.getBlock().isSolid(stateUp);
        } else {
            return stateUp.getBlock() instanceof ChainBlock;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean inWater = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
       return this.tryConnect(Block.getBlockFromItem(context.getItem().getItem()).getDefaultState(), context.getWorld(), context.getPos()).with(WATERLOGGED, inWater);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        world.setBlockState(pos, this.tryConnect(state, world, pos), 2);
        super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
    }

    public static List<Block> getChainBlocks() {
        return new ArrayList<>(Arrays.asList(ModBlocks.IRON_CHAIN.getBlock(), ModBlocks.ARCANE_GOLDEN_CHAIN.getBlock()));
    }

    public boolean pullDown(Item item, World world, BlockPos pos) {
        boolean can;
        boolean endChain = false;
        boolean wasAirAtEnd = false;

        do {
            pos = pos.down();
            if (!World.isValid(pos))
                return false;

            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(getChainBlocks().contains(block))
                continue;
            if(endChain) {
                can = wasAirAtEnd || world.isAirBlock(pos) || world.getBlockState(pos).getBlock() instanceof LanternBlock || state.getMaterial().isReplaceable();
                break;
            }
            endChain = true;
            wasAirAtEnd = world.isAirBlock(pos);
        } while(true);

        if(can) {
            BlockPos chainPos = pos.up();
            moveBlock(world, chainPos, pos);

            BlockState chainPosState = world.getBlockState(chainPos);

            if(world.isAirBlock(chainPos) || chainPosState.getMaterial().isReplaceable()) {
                world.setBlockState(chainPos, this.tryConnect(Block.getBlockFromItem(item).getDefaultState(), world, chainPos));
                return true;
            }
        }
        return false;
    }

    private void moveBlock(World world, BlockPos srcPos, BlockPos dstPos) {
        BlockState state = world.getBlockState(srcPos);
        Block block = state.getBlock();

        if((state.getBlockHardness(world, srcPos) == -1 || !state.isValidPosition(world, dstPos) || block.isAir(state, world, srcPos) || state.getPushReaction() != PushReaction.NORMAL || block == Blocks.OBSIDIAN) && !(world.getBlockState(srcPos).getBlock() instanceof LanternBlock)) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(srcPos);
        if(tileEntity != null) {
            tileEntity.remove();
        }

        world.setBlockState(srcPos, Blocks.AIR.getDefaultState());
        world.setBlockState(dstPos, state);

        if(tileEntity != null) {
            tileEntity.setPos(dstPos);
            TileEntity target = TileEntity.create(tileEntity.write(new CompoundNBT()));
            if (target != null) {
                world.setTileEntity(dstPos, target);

                target.updateContainingBlockInfo();
            }
        }

        world.notifyNeighbors(dstPos, state.getBlock());
    }

    private BlockState tryConnect(BlockState state, World world, BlockPos pos) {
        BlockState stateUp = world.getBlockState(pos.up());
        BlockState stateDown = world.getBlockState(pos.down());
        boolean flagUp = stateUp.getBlock() instanceof ChainBlock;
        boolean flagDown = stateDown.getBlock() instanceof ChainBlock;

        if (flagUp && !flagDown && !(stateDown.getBlock() instanceof LanternBlock)) {
            return state.with(TYPE, ConnectedBlockType.BOTTOM);
        } else if (!flagUp && (flagDown || stateDown.getBlock() instanceof LanternBlock)) {
            return state.with(TYPE, ConnectedBlockType.TOP);
        } else if (flagUp || stateDown.getBlock() instanceof LanternBlock) {
            return state.with(TYPE, ConnectedBlockType.CENTER);
        }
        return state.with(TYPE, ConnectedBlockType.SINGLE);
    }

    @Override
    public boolean allowsMovement(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, IFluidState fluidState) {
        if (!state.get(BlockStateProperties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            world.setBlockState(pos, (state.with(WATERLOGGED, true)), 3);
            world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
