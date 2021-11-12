package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.item.IRitualStarterItem;
import com.stal111.forbidden_arcanus.item.MundabiturDustItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Hephaestus Forge Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.HephaestusForgeBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class HephaestusForgeBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Shapes.join(
            VoxelShapeHelper.combineAll(
                    box(1.0D, 0.0D, 1.0D, 15.0D, 3.0D, 15.0D),
                    box(2.0D, 3.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                    box(4.0D, 4.0D, 4.0D, 12.0D, 8.0D, 12.0D),
                    box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D)
            ),
            VoxelShapeHelper.combineAll(
                    box(0.0D, 15.0D, 3.0D, 16.0D, 16.0D, 13.0D),
                    box(3.0D, 15.0D, 0.0D, 13.0D, 16.0D, 16.0D)
            ),
            BooleanOp.ONLY_FIRST
    );

    public HephaestusForgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false).setValue(WATERLOGGED, false));
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
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
        this.updateState(state, world, pos);

        if (state.getValue(ACTIVATED)) {
            if (world.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
            BlockEntity tileEntity = world.getBlockEntity(pos);

            if (tileEntity instanceof HephaestusForgeTileEntity) {
                ItemStack stack = player.getItemInHand(hand);

                if (stack.getItem() instanceof IRitualStarterItem) {
                    ((HephaestusForgeTileEntity) tileEntity).getRitualManager().tryStartRitual(stack);
                } else {
                    player.openMenu((HephaestusForgeTileEntity) tileEntity);
                }
                return InteractionResult.CONSUME;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    public void updateState(BlockState state, Level world, BlockPos pos) {
        BlockPattern.BlockPatternMatch patternHelper = MundabiturDustItem.getBaseHephaestusPattern().find(world, pos.below());

        if (patternHelper == null || patternHelper.getUp() != Direction.DOWN) {
            if (state.getValue(ACTIVATED)) {
                world.setBlock(pos, state.setValue(ACTIVATED, false), 3);
            }
        } else if (!state.getValue(ACTIVATED)) {
            world.setBlock(pos, state.setValue(ACTIVATED, true), 3);
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, WATERLOGGED);
    }
}
