package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Big Fungyss Feature
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.feature.BigFungyssFeature
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-13
 */
public class BigFungyssFeature extends Feature<BigFungyssFeatureConfig> {

    public BigFungyssFeature(Codec<BigFungyssFeatureConfig> codec) {
        super(codec);
    }

    private int getRandomHeight(Random random, int variant) {
        if (variant == 0) {
            return random.nextInt(3) + 4;
        } else {
            return random.nextInt(2) + 6;
        }
    }

    private boolean canGenerate(IWorld world, ChunkGenerator generator, BlockPos pos, int height, BlockPos.Mutable mutable) {
        if (pos.getY() < 1 || pos.getY() + height + 1 >= generator.getMaxBuildHeight()) {
            return false;
        }
        Block block = world.getBlockState(pos.down()).getBlock();

        if (!Tags.Blocks.STONE.contains(block)) {
            return false;
        }

        for (int i = 0; i <= height; i++) {
            BlockState state = world.getBlockState(mutable.setAndOffset(pos, 0, i, 0));

            if (!state.isAir(world, pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, BigFungyssFeatureConfig config) {
        int height = this.getRandomHeight(rand, config.variant);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        if (!this.canGenerate(reader, generator, pos, height, mutable)) {
            return false;
        }

        this.placeCap(reader, rand, pos, height, mutable, config);
        this.placeStem(reader, rand, pos, height, mutable, config);
        return true;
    }

    private void placeStem(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            mutable.setPos(pos).move(Direction.UP, i);

            if (world.getBlockState(mutable).canBeReplacedByLogs(world, mutable)) {
                this.setBlockState(world, mutable, config.stemProvider.getBlockState(random, pos));
            }
        }
    }

    private void placeCap(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, BigFungyssFeatureConfig config) {
        if (config.variant == 0) {
            int distanceToStem = 1;
            for (int i = height - 2; i <= height; i++) {
                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        if ((i < height && !(xOffset == 0 && zOffset == 0)) || !isCorner(xOffset, zOffset, distanceToStem)) {
                            mutable.setAndOffset(pos, xOffset, i, zOffset);
                            this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos));
                        }
                    }
                }
            }
            this.setBlockState(world, pos.up(height), config.capProvider.getBlockState(random, pos));
        } else {
            for (int i = height - 2; i <= height; i++) {
                int distanceToStem = i < height ? 2 : 1;

                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        if (i >= height || !isCorner(xOffset, zOffset, distanceToStem)) {
                            mutable.setAndOffset(pos, xOffset, i, zOffset);
                            this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos)
                                    .with(HugeMushroomBlock.UP, i >= height - 1).with(HugeMushroomBlock.WEST, xOffset < 0).with(HugeMushroomBlock.EAST, xOffset > 0).with(HugeMushroomBlock.NORTH, zOffset < 0).with(HugeMushroomBlock.SOUTH, zOffset > 0));
                        }
                    }
                }
            }

            for (Direction direction : Direction.values()) {
                if (direction.getAxis() != Direction.Axis.Y) {
                    mutable.setAndOffset(pos, 0, height - 4, 0);
                    mutable.move(direction);
                    this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos).with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(direction.getOpposite()), false));
                }
            }
        }
    }

    private boolean isCorner(int xOffset, int zOffset, int distanceToStem) {
        return (xOffset == -distanceToStem || xOffset == distanceToStem) == (zOffset == -distanceToStem || zOffset == distanceToStem);
    }
}