package com.stal111.forbidden_arcanus.common.block;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author stal111
 * @since 29.10.2023
 */
public class DeskBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<DeskBlock> CODEC = simpleCodec(DeskBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape INSIDE_SHAPE = Block.box(4.0D, 0.0D, 1.0D, 12.0D, 9.0D, 12.0D);
    private static final VoxelShape BOTTOM_SHAPE = Shapes.join(Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D), INSIDE_SHAPE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape TOP_SHAPE = Block.box(0.0D, 9.0D, 0.0D, 16.0D, 12.0D, 16.0D);
    private static final VoxelShape SHAPE = Shapes.or(BOTTOM_SHAPE, TOP_SHAPE);

    private final Map<BlockState, VoxelShape> shapesCache;

    public DeskBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
        this.shapesCache = this.getShapeForEachState(DeskBlock::calculateShape);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    private static VoxelShape calculateShape(BlockState state) {
        return VoxelShapeHelper.rotateShapeHorizontal(SHAPE, state.getValue(FACING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (stack.is(ModBlocks.FORBIDDENOMICON.get().asItem())) {
            BlockState newState = ModBlocks.RESEARCH_DESK.get().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

            level.setBlockAndUpdate(pos, newState);

            ItemStackUtils.shrinkStack(player, stack);

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
