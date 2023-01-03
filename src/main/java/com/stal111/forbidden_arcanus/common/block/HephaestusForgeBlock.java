package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.RitualStarterItem;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Hephaestus Forge Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock
 *
 * @author stal111
 */
public class HephaestusForgeBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;
    public static final IntegerProperty TIER = ModBlockStateProperties.TIER;

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
                .setValue(ACTIVATED, false)
                .setValue(TIER, 1)
                .setValue(WATERLOGGED, false)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new HephaestusForgeBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        this.updateState(state, level, pos);

        if (state.getValue(ACTIVATED)) {
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return InteractionResult.SUCCESS;
            }
            if (level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
                ItemStack stack = player.getItemInHand(hand);

                if (stack.getItem() instanceof RitualStarterItem) {
                    blockEntity.getRitualManager().tryStartRitual((ServerLevel) level, stack, player);
                } else {
                    NetworkHooks.openScreen(serverPlayer, blockEntity, pos);
                }
                return InteractionResult.CONSUME;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.HEPHAESTUS_FORGE.get(), HephaestusForgeBlockEntity::clientTick);
        }
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.HEPHAESTUS_FORGE.get(), HephaestusForgeBlockEntity::serverTick);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return HephaestusForgeBlock.setTierOnStack(super.getCloneItemStack(state, target, level, pos, player), state.getValue(TIER));
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, TIER, WATERLOGGED);
    }

    public static ItemStack setTierOnStack(ItemStack stack, int tier) {
        if (tier != 1) {
            CompoundTag tag = new CompoundTag();
            tag.putString(TIER.getName(), String.valueOf(tier));
            stack.addTagElement("BlockStateTag", tag);
        }

        return stack;
    }
}
