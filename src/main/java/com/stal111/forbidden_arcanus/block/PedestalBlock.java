package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Pedestal Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.PedestalBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-25
 */
public class PedestalBlock extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            box(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D),
            box(3.0D, 4.0D, 3.0D, 13.0D, 6.0D, 13.0D),
            box(4.0D, 6.0D, 4.0D, 12.0D, 11.0D, 12.0D),
            box(2.0D, 11.0D, 2.0D, 14.0D, 14.0D, 14.0D)
    );

    public static final BooleanProperty RITUAL = ModBlockStateProperties.RITUAL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PedestalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(RITUAL, false).setValue(WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        return this.defaultBlockState()
                .setValue(RITUAL, world.getBlockState(pos.below()).getBlock() == NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())
                .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor world, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        BlockEntity tileEntity = world.getBlockEntity(pos);

        if (!(tileEntity instanceof PedestalTileEntity)) {
            return InteractionResult.PASS;
        }
        PedestalTileEntity pedestalTileEntity = (PedestalTileEntity) tileEntity;

        if (pedestalTileEntity.hasStack()) {
            ItemStack pedestalStack = pedestalTileEntity.getStack();

            if (stack.isEmpty()) {
                player.setItemInHand(hand, pedestalStack);
            } else if (!player.addItem(pedestalStack)) {
                player.drop(pedestalStack, false);
            }

            pedestalTileEntity.clearStack();

        } else if (!stack.isEmpty() && !pedestalTileEntity.hasStack()) {
            pedestalTileEntity.setStack(stack.copy().split(1));

            ItemStackUtils.shrinkStack(player, stack);
            
        } else {
            return InteractionResult.PASS;
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);

        if (!fromPos.equals(pos.below())) {
            return;
        }

        BlockState newState = state.setValue(RITUAL, world.getBlockState(fromPos).getBlock() == NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get());

        if (state != newState) {
            world.setBlock(pos, newState, 3);
        }
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }

        BlockEntity tileEntity = world.getBlockEntity(pos);

        if (tileEntity instanceof PedestalTileEntity) {
            world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, ((PedestalTileEntity) tileEntity).getStack()));
        }

        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RITUAL, WATERLOGGED);
    }
}
