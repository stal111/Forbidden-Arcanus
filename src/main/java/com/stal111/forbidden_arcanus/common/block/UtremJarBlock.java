package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.UtremJarBlockEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utrem Jar Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.UtremJarBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-02-18
 */
public class UtremJarBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.box(3, 0, 3, 13, 13, 13),
            Block.box(5, 13, 5, 11, 15, 11)
    );

    public UtremJarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new UtremJarBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (!(level.getBlockEntity(pos) instanceof UtremJarBlockEntity blockEntity) || player.isShiftKeyDown()) {
            return super.use(state, level, pos, player, hand, hit);
        }

        IFluidHandler fluidHandler = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().get();

        if (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler)) {
            player.getInventory().setChanged();

            return InteractionResult.sidedSuccess(level.isClientSide());
        } else if (fluidHandler.getFluidInTank(0).isEmpty()) {
            BlockState newState = null;

            if (stack.is(ModItems.PIXIE.get())) {
                newState = ModBlocks.PIXIE_UTREM_JAR.get().defaultBlockState();
            } else if (stack.is(ModItems.CORRUPTED_PIXIE.get())) {
                newState = ModBlocks.CORRUPTED_PIXIE_UTREM_JAR.get().defaultBlockState();
            }

            if (newState != null) {
                ItemStackUtils.shrinkStack(player, stack);

                level.setBlockAndUpdate(pos, newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof UtremJarBlockEntity blockEntity) {
            FluidStack fluid = blockEntity.getTank().getFluid();

            return fluid.getFluid().getFluidType().getLightLevel(fluid);
        }
        return super.getLightEmission(state, world, pos);
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState state, @Nonnull Direction direction, @Nonnull BlockState neighborState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
