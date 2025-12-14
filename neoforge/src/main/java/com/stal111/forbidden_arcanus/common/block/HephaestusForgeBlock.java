package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.component.RitualStarter;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;
import org.jetbrains.annotations.Nullable;

/**
 * Hephaestus Forge Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock
 *
 * @author stal111
 */
public class HephaestusForgeBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final String DESCRIPTION_ID = Util.makeDescriptionId("block", ForbiddenArcanus.identifier("hephaestus_forge"));
    public static final String TIER_ID = Util.makeDescriptionId("block", ForbiddenArcanus.identifier("hephaestus_forge.tier"));

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;
    public static final EnumProperty<HephaestusForgeLevel> FORGE_TIER = ModBlockStateProperties.FORGE_TIER;

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
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FORGE_TIER, HephaestusForgeLevel.ONE)
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
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);

        stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(FORGE_TIER, state.getValue(FORGE_TIER)));

        return stack;
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
        level.scheduleTick(pos, this, 2);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.getBlockEntity(pos, ModBlockEntities.HEPHAESTUS_FORGE.get()).ifPresent(blockEntity -> {
            blockEntity.setForgeLevel(state.getValue(FORGE_TIER));
        });
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        this.updateState(state, level, pos);

        if (state.getValue(ACTIVATED)) {
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
                player.openMenu(blockEntity);
            }

            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, pos, player, result);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        this.updateState(state, level, pos);

        RitualStarter ritualStarter = stack.get(ModDataComponents.RITUAL_STARTER);

        if (state.getValue(ACTIVATED) && ritualStarter != null && level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
            level.playSound(player, pos, ritualStarter.soundEvent().value(), SoundSource.PLAYERS, 0.85F, level.getRandom().nextFloat() * 0.15F + 0.9F);

            if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer && blockEntity.getRitualManager().startRitual(serverLevel, pos, serverPlayer, blockEntity)) {
                stack.hurtAndBreak(ritualStarter.damagePerRitual(), player, hand.asEquipmentSlot());
            }

            return InteractionResult.SUCCESS;
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORGE_TIER, ACTIVATED, WATERLOGGED);
    }
}
