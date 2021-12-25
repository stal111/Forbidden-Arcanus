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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraftforge.common.ToolActions;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Random;

/**
 * Edelwood Log Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.EdelwoodLogBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-27
 */
public class EdelwoodLogBlock extends Block implements SimpleWaterloggedBlock {

    private static final float RAIN_FILL_CHANCE = 0.15F;
    private static final float OILY_CHANCE = 0.025F;

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty OILY = ModBlockStateProperties.OILY;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape BASE_SHAPE = Shapes.join(Shapes.block(), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST);

    protected final EnumMap<Direction.Axis, VoxelShape> shapesCache;

    public EdelwoodLogBlock(Properties properties) {
        super(properties);
        if (this.getStateDefinition().getProperties().contains(AXIS)) {
            this.registerDefaultState(this.getStateDefinition().any().setValue(AXIS, Direction.Axis.Y).setValue(OILY, false).setValue(WATERLOGGED,false));
        }
        this.shapesCache = this.buildShapes();
    }

    private EnumMap<Direction.Axis, VoxelShape> buildShapes() {
        EnumMap<Direction.Axis, VoxelShape> map = new EnumMap<>(Direction.Axis.class);

        map.put(Direction.Axis.Y, BASE_SHAPE);
        map.put(Direction.Axis.X, VoxelShapeHelper.rotate(BASE_SHAPE, Direction.Axis.X));
        map.put(Direction.Axis.Z, VoxelShapeHelper.rotate(BASE_SHAPE, Direction.Axis.Z));

        return map;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return context.isHoldingItem(ModBlocks.EDELWOOD_LOG.get().asItem()) || context.isHoldingItem(ModBlocks.CARVED_EDELWOOD_LOG.get().asItem()) ? Shapes.block() : this.shapesCache.get(state.getValue(AXIS));
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, flag);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction direction, @Nonnull BlockState neighborState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (random.nextDouble() < OILY_CHANCE && level.isAreaLoaded(pos, 4)) {
            level.setBlock(pos, state.setValue(OILY, true), 2);
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        return !state.getValue(OILY);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.GLASS_BOTTLE) && state.getValue(OILY)) {
            ItemStack oil = new ItemStack(ModItems.EDELWOOD_OIL.get());

            ItemStackUtils.shrinkStack(player, stack);

            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

            if (stack.isEmpty()) {
                player.setItemInHand(hand, oil);
            } else if (!player.getInventory().add(oil)) {
                player.drop(oil, false);
            }

            level.setBlockAndUpdate(pos, state.setValue(OILY, false));

            return InteractionResult.sidedSuccess(level.isClientSide());
        } else if (stack.canPerformAction(ToolActions.AXE_STRIP) && !this.isCarved() && state.getValue(AXIS) == Direction.Axis.Y) {
            Direction direction = hit.getDirection().getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : hit.getDirection();

            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));

            if (!level.isClientSide()) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(pos, ModBlocks.CARVED_EDELWOOD_LOG.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(OILY, state.getValue(OILY)).setValue(WATERLOGGED, state.getValue(WATERLOGGED)));

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    protected boolean shouldHandlePrecipitation(BlockState state, Level level, Biome.Precipitation precipitation) {
        if (!state.getValue(WATERLOGGED) && (!state.hasProperty(AXIS) || state.getValue(AXIS) == Direction.Axis.Y) && precipitation == Biome.Precipitation.RAIN) {
            return level.getRandom().nextFloat() < RAIN_FILL_CHANCE;
        }
        return false;
    }

    @Override
    public void handlePrecipitation(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(state, level, precipitation)) {
            int i = 0;
            while (level.getBlockState(pos.below(i + 1)).is(ModTags.Blocks.EDELWOOD_LOGS) && level.getBlockState(pos.below(i + 1)).getValue(AXIS) == Direction.Axis.Y && !level.getBlockState(pos.below(i + 1)).getValue(WATERLOGGED)) {
                i++;
            }
            level.setBlockAndUpdate(pos.below(i), level.getBlockState(pos.below(i)).setValue(WATERLOGGED, true));
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos.below(i));
        }
    }

    @Nonnull
    public BlockState rotate(BlockState state, @Nonnull Rotation rotation) {
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
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, OILY, WATERLOGGED);
    }

    protected boolean isCarved() {
        return false;
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
