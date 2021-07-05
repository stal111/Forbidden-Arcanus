package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
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
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.block.ValhelsiaContainerBlock;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

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
public class PedestalBlock extends ValhelsiaContainerBlock implements IWaterLoggable {

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 4.0D, 15.0D),
            makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 6.0D, 13.0D),
            makeCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 11.0D, 12.0D),
            makeCuboidShape(2.0D, 11.0D, 2.0D, 14.0D, 14.0D, 14.0D)
    );

    public static final BooleanProperty BOUND = ModBlockStateProperties.BOUND;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PedestalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(BOUND, false).with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PedestalTileEntity();
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld world, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (!(tileEntity instanceof PedestalTileEntity)) {
            return ActionResultType.PASS;
        }
        PedestalTileEntity pedestalTileEntity = (PedestalTileEntity) tileEntity;

        if (pedestalTileEntity.hasStack()) {
            ItemStack pedestalStack = pedestalTileEntity.getStack();

            if (stack.isEmpty()) {
                player.setHeldItem(hand, pedestalStack);
            } else if (!player.addItemStackToInventory(pedestalStack)) {
                player.dropItem(pedestalStack, false);
            }

            pedestalTileEntity.clearStack();

        } else if (!stack.isEmpty() && !pedestalTileEntity.hasStack()) {
            pedestalTileEntity.setStack(stack.copy().split(1));

            ItemStackUtils.shrinkStack(player, stack);
            
        } else {
            return ActionResultType.PASS;
        }

        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);

        if (!fromPos.equals(pos.down())) {
            return;
        }

        if (state.get(BOUND) && world.getBlockState(fromPos).getBlock() != NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get()) {
            this.tryUnbindPedestal(world, state, pos, false);
        } else if (!state.get(BOUND) && world.getBlockState(fromPos).getBlock() == NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get()) {
            this.tryBindPedestal(world, state, pos);
        }
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        if (world.getBlockState(pos.down()).getBlock() == NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get()) {
            this.tryBindPedestal(world, state, pos);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.isIn(newState.getBlock())) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(pos);

        if (tileEntity instanceof PedestalTileEntity) {
            world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, ((PedestalTileEntity) tileEntity).getStack()));

            this.tryUnbindPedestal(world, state, pos, true);
        }

        super.onReplaced(state, world, pos, newState, isMoving);
    }

    private BlockPos getHephaestusForgePos(World world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                continue;
            }

            BlockPos offsetPos = pos.offset(direction, 2);

            if (world.getBlockState(offsetPos.offset(direction, 1)).getBlock() == ModBlocks.HEPHAESTUS_FORGE.getBlock()) {
                return offsetPos.offset(direction, 1);
            } else if (world.getBlockState(offsetPos.offset(direction.rotateY(), 2)).getBlock() == ModBlocks.HEPHAESTUS_FORGE.getBlock()) {
                return offsetPos.offset(direction.rotateY(), 2);
            }
        }

        return null;
    }

    private void tryBindPedestal(World world, BlockState state, BlockPos pos) {
        BlockPos forgePos = this.getHephaestusForgePos(world, pos);

        if (state.get(BOUND) || forgePos == null) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(forgePos);

        if (tileEntity instanceof HephaestusForgeTileEntity) {
            ((HephaestusForgeTileEntity) tileEntity).addPedestal(pos);

            world.setBlockState(pos, state.with(BOUND, true), 3);
        }
    }

    private void tryUnbindPedestal(World world, BlockState state, BlockPos pos, boolean replaced) {
        BlockPos forgePos = this.getHephaestusForgePos(world, pos);

        if (!state.get(BOUND) || forgePos == null) {
            return;
        }

        TileEntity tileEntity = world.getTileEntity(forgePos);

        if (tileEntity instanceof HephaestusForgeTileEntity) {
            ((HephaestusForgeTileEntity) tileEntity).removePedestal(pos);

            if (!replaced) {
                world.setBlockState(pos, state.with(BOUND, false), 3);
            }
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BOUND, WATERLOGGED);
    }
}
