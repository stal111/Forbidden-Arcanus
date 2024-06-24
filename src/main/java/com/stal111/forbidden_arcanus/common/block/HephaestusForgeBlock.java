package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.RitualStarterItem;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Hephaestus Forge Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock
 *
 * @author stal111
 */
public class HephaestusForgeBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    private static final String DESCRIPTION_ID = Util.makeDescriptionId("block", ForbiddenArcanus.location("hephaestus_forge"));
    private static final String TIER_ID = Util.makeDescriptionId("tooltip", ForbiddenArcanus.location("hephaestus_forge.tier"));

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

    private final HephaestusForgeLevel level;

    public HephaestusForgeBlock(HephaestusForgeLevel level, Properties properties) {
        super(properties);
        this.level = level;
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(ACTIVATED, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HephaestusForgeBlockEntity(pos, state);
    }

    @Override
    public String getDescriptionId() {
        return DESCRIPTION_ID;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable(TIER_ID, this.level.getAsInt()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.getBlockEntity(pos, ModBlockEntities.HEPHAESTUS_FORGE.get()).ifPresent(blockEntity -> {
            blockEntity.setForgeLevel(this.level);
        });
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        this.updateState(state, level, pos);

        if (state.getValue(ACTIVATED)) {
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
                player.openMenu(blockEntity);
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useWithoutItem(state, level, pos, player, result);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        this.updateState(state, level, pos);

        if (state.getValue(ACTIVATED) && stack.getItem() instanceof RitualStarterItem ritualStarterItem) {
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
                ritualStarterItem.tryStartRitual(blockEntity, level, stack, player);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }

    public void updateState(BlockState state, Level level, BlockPos pos) {
        BlockPattern.BlockPatternMatch patternHelper = ModBlockPatterns.BASE_HEPHAESTUS_PATTERN.find(level, pos.below());

        if (patternHelper == null) {
            if (state.getValue(ACTIVATED)) {
                level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, false));
            }
        } else if (!state.getValue(ACTIVATED)) {
            level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, true));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.HEPHAESTUS_FORGE.get(), HephaestusForgeBlockEntity::clientTick);
        }
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.HEPHAESTUS_FORGE.get(), HephaestusForgeBlockEntity::serverTick);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);

        BlockEntity blockEntity = level.getBlockEntity(pos);

        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (newState.getBlock() instanceof HephaestusForgeBlock) {
            return;
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public HephaestusForgeLevel getLevel() {
        return this.level;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, WATERLOGGED);
    }
}
