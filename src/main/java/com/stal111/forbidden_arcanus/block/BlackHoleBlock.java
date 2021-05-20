package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.block.ValhelsiaContainerBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * Black Hole Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.BlackHoleBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class BlackHoleBlock extends ValhelsiaContainerBlock {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlackHoleBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlackHoleTileEntity();
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
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