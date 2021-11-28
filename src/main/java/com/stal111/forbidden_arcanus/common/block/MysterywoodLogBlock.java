package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Mysterywood Log Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.MysterywoodLogBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-28
 */
public class MysterywoodLogBlock extends RotatedPillarBlock {

    private static final double PARTICLE_SPEED_MODIFIER = 0.3D;

    public MysterywoodLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random random) {
        double xSpeed = (random.nextFloat() - 0.5) * PARTICLE_SPEED_MODIFIER;
        double ySpeed = (random.nextFloat() - 0.5) * PARTICLE_SPEED_MODIFIER;
        double zSpeed = (random.nextFloat() - 0.5) * PARTICLE_SPEED_MODIFIER;
        level.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), xSpeed, ySpeed, zSpeed);
    }
}
