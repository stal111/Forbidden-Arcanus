package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Fungyss Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.FungyssBlock
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-02
 */
public class FungyssBlock extends BushBlock implements IGrowable {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);

    public FungyssBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(@Nonnull BlockState state, IWorldReader world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(Tags.Blocks.STONE);
    }

    @Override
    public boolean canGrow(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return rand.nextDouble() < 0.4D;
    }

    @Override
    public void grow(@Nonnull ServerWorld world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int xOffset = 0; xOffset >= -1; --xOffset) {
            for(int zOffset = 0; zOffset >= -1; --zOffset) {
                if (canMegaFungyssSpawnAt(state, world, pos, xOffset, zOffset)) {
                    this.growMegaFungyss(world, pos, state, rand, xOffset, zOffset);
                    return;
                }
            }
        }
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

        ConfiguredFeature<?, ?> configuredFeature = world.rand.nextBoolean() ? ModConfiguredFeatures.BIG_FUNGYSS_0 : ModConfiguredFeatures.BIG_FUNGYSS_1;

        if (!configuredFeature.generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos)) {
            world.setBlockState(pos, state, 4);
        }
    }

    private void growMegaFungyss(ServerWorld world, BlockPos pos, BlockState state, Random rand, int xOffset, int zOffset) {
        world.setBlockState(pos.add(xOffset, 0, zOffset), Blocks.AIR.getDefaultState(), 4);
        world.setBlockState(pos.add(xOffset + 1, 0, zOffset), Blocks.AIR.getDefaultState(), 4);
        world.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), Blocks.AIR.getDefaultState(), 4);
        world.setBlockState(pos.add(xOffset, 0, zOffset + 1), Blocks.AIR.getDefaultState(), 4);

        ConfiguredFeature<?, ?> configuredFeature = rand.nextBoolean() ? ModConfiguredFeatures.MEGA_FUNGYSS_0 : ModConfiguredFeatures.MEGA_FUNGYSS_1;

        if (!configuredFeature.generate(world, world.getChunkProvider().getChunkGenerator(), rand, pos.add(xOffset, 0, zOffset))) {
            world.setBlockState(pos.add(xOffset, 0, zOffset), state, 4);
            world.setBlockState(pos.add(xOffset + 1, 0, zOffset), state, 4);
            world.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), state, 4);
            world.setBlockState(pos.add(xOffset, 0, zOffset + 1), state, 4);
        }
    }

    private boolean canMegaFungyssSpawnAt(BlockState state, IBlockReader world, BlockPos pos, int xOffset, int zOffset) {
        Block block = state.getBlock();
        return block == world.getBlockState(pos.add(xOffset, 0, zOffset)).getBlock() && block == world.getBlockState(pos.add(xOffset + 1, 0, zOffset)).getBlock() && block == world.getBlockState(pos.add(xOffset, 0, zOffset + 1)).getBlock() && block == world.getBlockState(pos.add(xOffset + 1, 0, zOffset + 1)).getBlock();
    }
}