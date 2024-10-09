package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Carved Edelwood Log Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.CarvedEdelwoodLogBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-27
 */
public class CarvedEdelwoodLogBlock extends EdelwoodLogBlock {

    public static final BooleanProperty LEAVES = ModBlockStateProperties.LEAVES;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CarvedEdelwoodLogBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LEAVES, false).setValue(OILY, false).setValue(WATERLOGGED,false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return context.isHoldingItem(ModBlocks.EDELWOOD_LOG.get().asItem()) || context.isHoldingItem(ModBlocks.CARVED_EDELWOOD_LOG.get().asItem()) ? Shapes.block() : SHAPES.get(Direction.Axis.Y);
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (stack.canPerformAction(ItemAbilities.SHEARS_HARVEST) && state.getValue(LEAVES)) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.SHEAR, pos);

            level.setBlockAndUpdate(pos, state.setValue(LEAVES, false));

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        } else if (stack.getItem() instanceof BoneMealItem && !state.getValue(LEAVES)) {
            ItemStackUtils.shrinkStack(player, stack);

            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            level.setBlockAndUpdate(pos, state.setValue(LEAVES, true));

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, @Nonnull Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OILY, LEAVES, WATERLOGGED);
    }

    @Override
    protected boolean isCarved() {
        return true;
    }
}
