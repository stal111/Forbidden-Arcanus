package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ConnectedBlockType;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainBlock extends CutoutBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<ConnectedBlockType> TYPE = EnumProperty.create("type", ConnectedBlockType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);

    public ChainBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, ConnectedBlockType.SINGLE).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND) {
            if (getChainBlocks().contains(Block.byItem(stack.getItem()))) {
                if (pullDown(stack.getItem(), world, pos)) {
                    ItemStackUtils.shrinkStack(player, stack);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return !this.canSurvive(state, world, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState stateUp = world.getBlockState(pos.above());
        if (state.getValue(TYPE) == ConnectedBlockType.TOP || state.getValue(TYPE) == ConnectedBlockType.SINGLE) {
            return canSupportCenter(world, pos.above(), Direction.DOWN);
        } else {
            return stateUp.getBlock() instanceof ChainBlock;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean inWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
       return this.tryConnect(Block.byItem(context.getItemInHand().getItem()).defaultBlockState(), context.getLevel(), context.getClickedPos()).setValue(WATERLOGGED, inWater);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        world.setBlock(pos, this.tryConnect(state, world, pos), 2);
        super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
    }

    public static List<Block> getChainBlocks() {
        return new ArrayList<>(Arrays.asList(ModBlocks.IRON_CHAIN.getBlock(), ModBlocks.ARCANE_GOLDEN_CHAIN.getBlock()));
    }

    public boolean pullDown(Item item, Level level, BlockPos pos) {
        boolean can;
        boolean endChain = false;
        boolean wasAirAtEnd = false;

        do {
            pos = pos.below();
            if (!level.isInWorldBounds(pos))
                return false;

            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if(getChainBlocks().contains(block))
                continue;
            if(endChain) {
                can = wasAirAtEnd || level.isEmptyBlock(pos) || level.getBlockState(pos).getBlock() instanceof LanternBlock || level.getBlockState(pos).getBlock() instanceof HangingCandelabraBlock || state.getMaterial().isReplaceable();
                break;
            }
            endChain = true;
            wasAirAtEnd = level.isEmptyBlock(pos);
        } while(true);

        if(can) {
            BlockPos chainPos = pos.above();
            moveBlock(level, chainPos, pos);

            BlockState chainPosState = level.getBlockState(chainPos);

            if(level.isEmptyBlock(chainPos) || chainPosState.getMaterial().isReplaceable()) {
                level.setBlockAndUpdate(chainPos, this.tryConnect(Block.byItem(item).defaultBlockState(), level, chainPos));

                level.playSound(null, chainPos.getX(), chainPos.getY(), chainPos.getZ(), SoundEvents.CHAIN_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private void moveBlock(Level world, BlockPos srcPos, BlockPos dstPos) {
        BlockState state = world.getBlockState(srcPos);
        Block block = state.getBlock();

        if((state.getDestroySpeed(world, srcPos) == -1 || !state.canSurvive(world, dstPos) || state.isAir() || state.getPistonPushReaction() != PushReaction.NORMAL || block == Blocks.OBSIDIAN) && !(world.getBlockState(srcPos).getBlock() instanceof LanternBlock) && !(world.getBlockState(srcPos).getBlock() instanceof HangingCandelabraBlock)) {
            return;
        }

        BlockEntity tileEntity = world.getBlockEntity(srcPos);
        if(tileEntity != null) {
            tileEntity.setRemoved();
        }

        world.setBlockAndUpdate(srcPos, Blocks.AIR.defaultBlockState());
        world.setBlockAndUpdate(dstPos, state);

//        if(tileEntity != null) {
//            tileEntity.set(dstPos);
//            BlockEntity target = BlockEntity.loadStatic(state, tileEntity.save(new CompoundTag()));
//            if (target != null) {
//                world.setBlockEntity(dstPos, target);
//
//                target.clearCache();
//            }
//        }

        world.updateNeighborsAt(dstPos, state.getBlock());
    }

    private BlockState tryConnect(BlockState state, Level world, BlockPos pos) {
        BlockState stateUp = world.getBlockState(pos.above());
        BlockState stateDown = world.getBlockState(pos.below());
        boolean flagUp = stateUp.getBlock() instanceof ChainBlock;
        boolean flagDown = stateDown.getBlock() instanceof ChainBlock;

        if (flagUp && !flagDown && !(stateDown.getBlock() instanceof LanternBlock || stateDown.getBlock() instanceof HangingCandelabraBlock)) {
            return state.setValue(TYPE, ConnectedBlockType.BOTTOM);
        } else if (!flagUp && (flagDown || stateDown.getBlock() instanceof LanternBlock || stateDown.getBlock() instanceof HangingCandelabraBlock)) {
            return state.setValue(TYPE, ConnectedBlockType.TOP);
        } else if (flagUp || stateDown.getBlock() instanceof LanternBlock || stateDown.getBlock() instanceof HangingCandelabraBlock) {
            return state.setValue(TYPE, ConnectedBlockType.CENTER);
        }
        return state.setValue(TYPE, ConnectedBlockType.SINGLE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
