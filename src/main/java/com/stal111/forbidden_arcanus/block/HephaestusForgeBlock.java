package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.item.MundabiturDustItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.block.ValhelsiaContainerBlock;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Hephaestus Forge Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.HephaestusForgeBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class HephaestusForgeBlock extends ValhelsiaContainerBlock implements IWaterLoggable {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            VoxelShapeHelper.combineAll(
                    makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 3.0D, 15.0D),
                    makeCuboidShape(2.0D, 3.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                    makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 8.0D, 12.0D),
                    makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D)
            ),
            VoxelShapeHelper.combineAll(
                    makeCuboidShape(0.0D, 15.0D, 3.0D, 16.0D, 16.0D, 13.0D),
                    makeCuboidShape(3.0D, 15.0D, 0.0D, 13.0D, 16.0D, 16.0D)
            ),
            IBooleanFunction.ONLY_FIRST
    );

    public HephaestusForgeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(ACTIVATED, false).with(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HephaestusForgeTileEntity();
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
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, Hand hand, @Nonnull BlockRayTraceResult hit) {
        this.updateState(state, world, pos);

        if (state.get(ACTIVATED)) {
            if (world.isRemote()) {
                return ActionResultType.SUCCESS;
            }
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof HephaestusForgeTileEntity) {
                player.openContainer((HephaestusForgeTileEntity) tileEntity);
                return ActionResultType.CONSUME;
            }
        }

        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    public void updateState(BlockState state, World world, BlockPos pos) {
        BlockPattern.PatternHelper patternHelper = MundabiturDustItem.getBaseHephaestusPattern().match(world, pos.down());

        if (patternHelper == null || patternHelper.getUp() != Direction.DOWN) {
            if (state.get(ACTIVATED)) {
                world.setBlockState(pos, state.with(ACTIVATED, false), 3);
            }
        } else if (!state.get(ACTIVATED)) {
            world.setBlockState(pos, state.with(ACTIVATED, true), 3);
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, WATERLOGGED);
    }
}
