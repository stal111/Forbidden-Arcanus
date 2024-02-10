package com.stal111.forbidden_arcanus.common.block.clibano;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Clibano Corner Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.clibano.ClibanoCornerBlock
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoCenterBlock extends AbstractClibanoFrameBlock {

    public static final MapCodec<ClibanoCenterBlock> CODEC = simpleCodec(ClibanoCenterBlock::new);

    private static final EnumProperty<ClibanoCenterType> TYPE = ModBlockStateProperties.CLIBANO_CENTER_TYPE;

    public ClibanoCenterBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(this.getFacingProperty(), Direction.NORTH).setValue(TYPE, ClibanoCenterType.SIDE));
    }

    @Override
    public DirectionProperty getFacingProperty() {
        return BlockStateProperties.FACING;
    }

    @Override
    public BlockState updateAppearance(BlockState state, boolean isLit, ClibanoFireType fireType) {
        if (state.getValue(TYPE).isFront()) {
            return state.setValue(TYPE, isLit ? ClibanoCenterType.valueOf("FRONT_" + fireType) : ClibanoCenterType.FRONT_OFF);
        }

        return super.updateAppearance(state, isLit, fireType);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(this.getFacingProperty(), context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getFacingProperty(), TYPE);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(TYPE).getFireType() != null) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY();
            double z = pos.getZ() + 0.5D;

            if (random.nextDouble() < 0.18D) {
                SoundEvent soundEvent = state.getValue(TYPE) == ClibanoCenterType.FRONT_FIRE ? ModSounds.CLIBANO_FIRE_CRACKLE.get() : ModSounds.CLIBANO_SOUL_FIRE_CRACKLE.get();

                level.playLocalSound(x, y, z, soundEvent, SoundSource.BLOCKS, 0.2F, 1.0F, false);
            }

            Direction direction = state.getValue(this.getFacingProperty());
            Direction.Axis axis = direction.getAxis();

            double d4 = random.nextDouble() * 0.6D - 0.3D;
            double d5 = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : d4;
            double d6 = random.nextDouble() * 9.0D / 16.0D;
            double d7 = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : d4;

            level.addParticle(ParticleTypes.SMOKE, x + d5, y + d6, z + d7, 0.0D, 0.0D, 0.0D);
        }
    }
}
