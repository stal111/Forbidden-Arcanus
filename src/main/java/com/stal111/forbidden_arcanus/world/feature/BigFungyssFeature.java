package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Big Fungyss Feature <br>
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

    private boolean canGenerate(LevelAccessor world, ChunkGenerator generator, BlockPos pos, int height, BlockPos.MutableBlockPos mutable) {
        if (pos.getY() < 1 || pos.getY() + height + 1 >= generator.getGenDepth()) {
            return false;
        }
        Block block = world.getBlockState(pos.below()).getBlock();

        if (!Tags.Blocks.STONE.contains(block)) {
            return false;
        }

        for (int i = 0; i <= height; i++) {
            BlockState state = world.getBlockState(mutable.setWithOffset(pos, 0, i, 0));

            if (!state.isAir()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<BigFungyssFeatureConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();

        int height = this.getRandomHeight(rand, context.config().variant);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!this.canGenerate(level, context.chunkGenerator(), pos, height, mutable)) {
            return false;
        }

        this.placeCap(level, rand, pos, height, mutable, context.config());
        this.placeStem(level, rand, pos, height, mutable, context.config());

        return true;
    }

    private void placeStem(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            mutable.set(pos).move(Direction.UP, i);

            if (!world.getBlockState(mutable).isSolidRender(world, mutable)) {
                this.setBlock(world, mutable, config.stemProvider.getState(random, pos));
            }
        }
    }

    private void placeCap(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        if (config.variant == 0) {
            int distanceToStem = 1;
            for (int i = height - 2; i <= height; i++) {
                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        if ((i < height && !(xOffset == 0 && zOffset == 0)) || !isCorner(xOffset, zOffset, distanceToStem)) {
                            mutable.setWithOffset(pos, xOffset, i, zOffset);
                            this.setBlock(world, mutable, config.capProvider.getState(random, pos));
                        }
                    }
                }
            }
            this.setBlock(world, pos.above(height), config.capProvider.getState(random, pos));
        } else {
            for (int i = height - 2; i <= height; i++) {
                int distanceToStem = i < height ? 2 : 1;

                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        if (i >= height || !isCorner(xOffset, zOffset, distanceToStem)) {
                            mutable.setWithOffset(pos, xOffset, i, zOffset);
                            this.setBlock(world, mutable, config.capProvider.getState(random, pos)
                                    .setValue(HugeMushroomBlock.UP, i >= height - 1).setValue(HugeMushroomBlock.WEST, xOffset < 0).setValue(HugeMushroomBlock.EAST, xOffset > 0).setValue(HugeMushroomBlock.NORTH, zOffset < 0).setValue(HugeMushroomBlock.SOUTH, zOffset > 0));
                        }
                    }
                }
            }

            for (Direction direction : Direction.values()) {
                if (direction.getAxis() != Direction.Axis.Y) {
                    mutable.setWithOffset(pos, 0, height - 4, 0);
                    mutable.move(direction);
                    this.setBlock(world, mutable, config.capProvider.getState(random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false));
                }
            }
        }
    }

    private boolean isCorner(int xOffset, int zOffset, int distanceToStem) {
        return (xOffset == -distanceToStem || xOffset == distanceToStem) == (zOffset == -distanceToStem || zOffset == distanceToStem);
    }
}