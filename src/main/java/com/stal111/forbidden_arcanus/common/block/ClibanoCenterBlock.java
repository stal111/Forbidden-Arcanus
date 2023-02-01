package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Clibano Corner Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ClibanoCornerBlock
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoCenterBlock extends DirectionalBlock implements ClibanoPart {

    private static final EnumProperty<ClibanoCenterType> TYPE = ModBlockStateProperties.CLIBANO_CENTER_TYPE;

    public ClibanoCenterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ClibanoCenterType.SIDE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ClibanoBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        return this.openScreen(level, pos, player);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        this.onRemove(state, level, pos, newState);
    }

    @Nonnull
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(TYPE).getLightLevel() != 0) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY();
            double z = pos.getZ() + 0.5D;

            if (random.nextDouble() < 0.18D) {
                SoundEvent soundEvent = state.getValue(TYPE) == ClibanoCenterType.FRONT_FIRE ? ModSounds.CLIBANO_FIRE_CRACKLE.get() : ModSounds.CLIBANO_SOUL_FIRE_CRACKLE.get();

                level.playLocalSound(x, y, z, soundEvent, SoundSource.BLOCKS, 0.2F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();

            double d4 = random.nextDouble() * 0.6D - 0.3D;
            double d5 = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : d4;
            double d6 = random.nextDouble() * 9.0D / 16.0D;
            double d7 = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : d4;

            level.addParticle(ParticleTypes.SMOKE, x + d5, y + d6, z + d7, 0.0D, 0.0D, 0.0D);
        }
    }
}
