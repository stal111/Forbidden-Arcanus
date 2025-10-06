package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;

import java.util.EnumMap;

/**
 * Edelwood Log Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.EdelwoodLogBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-27
 */
public class EdelwoodLogBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty OILY = ModBlockStateProperties.OILY;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final EnumMap<Direction.Axis, VoxelShape> SHAPES = VoxelShapeHelper.rotateAxis(
            Shapes.join(Shapes.block(), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST)
    );
    private static final float RAIN_FILL_CHANCE = 0.15F;
    private static final float OILY_CHANCE = 0.025F;

    public EdelwoodLogBlock(Properties properties) {
        super(properties);
        if (this.getStateDefinition().getProperties().contains(AXIS)) {
            this.registerDefaultState(this.getStateDefinition().any().setValue(AXIS, Direction.Axis.Y).setValue(OILY, false).setValue(WATERLOGGED, false));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return context.isHoldingItem(ModBlocks.EDELWOOD_LOG.get().asItem()) || context.isHoldingItem(ModBlocks.CARVED_EDELWOOD_LOG.get().asItem()) ? Shapes.block() : SHAPES.get(state.getValue(AXIS));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, flag);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < OILY_CHANCE && level.isAreaLoaded(pos, 4)) {
            level.setBlock(pos, state.setValue(OILY, true), 2);
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(OILY);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (stack.is(Items.GLASS_BOTTLE) && state.getValue(OILY)) {
            ItemStack oil = new ItemStack(ModItems.EDELWOOD_OIL.get());

            stack.consume(1, player);

            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

            if (stack.isEmpty()) {
                player.setItemInHand(hand, oil);
            } else if (!player.getInventory().add(oil)) {
                player.drop(oil, false);
            }

            level.setBlockAndUpdate(pos, state.setValue(OILY, false));

            return InteractionResult.SUCCESS;
        } else if (stack.canPerformAction(ItemAbilities.AXE_STRIP) && !this.isCarved() && state.getValue(AXIS) == Direction.Axis.Y) {
            Direction direction = result.getDirection().getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : result.getDirection();

            stack.hurtAndBreak(1, player, hand.asEquipmentSlot());

            if (!level.isClientSide()) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, ModBlocks.CARVED_EDELWOOD_LOG.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(OILY, state.getValue(OILY)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)));

            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }

    protected boolean shouldHandlePrecipitation(BlockState state, Level level, Biome.Precipitation precipitation) {
        if (!state.getValue(WATERLOGGED) && (!state.hasProperty(AXIS) || state.getValue(AXIS) == Direction.Axis.Y) && precipitation == Biome.Precipitation.RAIN) {
            return level.getRandom().nextFloat() < RAIN_FILL_CHANCE;
        }
        return false;
    }

    @Override
    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (this.shouldHandlePrecipitation(state, level, precipitation)) {
            int i = 0;
            BlockState belowState = level.getBlockState(pos.below(i + 1));

            while (belowState.is(ModTags.Blocks.EDELWOOD_LOGS) && (!belowState.hasProperty(AXIS) || belowState.getValue(AXIS) == Direction.Axis.Y) && !belowState.getValue(WATERLOGGED)) {
                i++;
                belowState = level.getBlockState(pos.below(i + 1));
            }
            level.setBlockAndUpdate(pos.below(i), level.getBlockState(pos.below(i)).setValue(WATERLOGGED, true));
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos.below(i));
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, OILY, WATERLOGGED);
    }

    protected boolean isCarved() {
        return false;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
