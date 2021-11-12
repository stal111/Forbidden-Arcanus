package com.stal111.forbidden_arcanus.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Black Hole Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.BlackHoleBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class BlackHoleBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlackHoleBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        for(int i = 0; i < 3; i++) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            double x = pos.getX() + 0.5D + 0.25D * j;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D + 0.25D * k;
            double xSpeed = rand.nextFloat() * j;
            double ySpeed = (rand.nextFloat() - 0.5D) * 0.125D;
            double zSpeed = rand.nextFloat() * k;

            world.addParticle(ParticleTypes.PORTAL, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}