package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mega Fungyss Feature
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.feature.MegaFungyssFeature
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-13
 */
public class MegaFungyssFeature extends Feature<BigFungyssFeatureConfig> {

    public MegaFungyssFeature(Codec<BigFungyssFeatureConfig> codec) {
        super(codec);
    }

    private int getRandomHeight(Random random) {
        return random.nextInt(4) + 11;
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
        LevelAccessor level = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();

        int height = this.getRandomHeight(context.random());

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!this.canGenerate(level, context.chunkGenerator(), pos, height, mutable)) {
            return false;
        }

        this.placeStem(level, rand, pos, height, mutable, context.config());
        this.placeCap(level, rand, pos, height, mutable, context.config());
        return true;
    }


    private void placeStem(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            this.placeStemBlock(world, config.stemProvider.getState(random, pos), mutable, pos, 0, i, 0);
            this.placeStemBlock(world, config.stemProvider.getState(random, pos), mutable, pos, 1, i, 0);
            this.placeStemBlock(world, config.stemProvider.getState(random, pos), mutable, pos, 1, i, 1);
            this.placeStemBlock(world, config.stemProvider.getState(random, pos), mutable, pos, 0, i, 1);
        }
    }

    private void placeStemBlock(LevelAccessor world, BlockState state, BlockPos.MutableBlockPos mutable, BlockPos pos, int xOffset, int yOffset, int zOffset) {
        mutable.set(pos).move(xOffset, yOffset, zOffset);

        if (!world.getBlockState(mutable).isSolidRender(world, mutable)) {
            this.setBlock(world, mutable, state);
        }
    }

    private void placeCap(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        for (int i = height - 3; i <= height; i++) {
            int distanceToStem = 2;

            for (int xOffset = -distanceToStem; xOffset <= distanceToStem + 1; xOffset++) {
                for (int zOffset = -distanceToStem; zOffset <= distanceToStem + 1; zOffset++) {
                    boolean flag1 = i >= height && xOffset != -distanceToStem && xOffset != distanceToStem + 1 && zOffset != -distanceToStem && zOffset != distanceToStem + 1;
                    boolean flag2 = i < height && (xOffset == -distanceToStem || xOffset == distanceToStem + 1 || zOffset == -distanceToStem || zOffset == distanceToStem + 1) && !((xOffset == -distanceToStem || xOffset == distanceToStem + 1) && (zOffset == -distanceToStem || zOffset == distanceToStem + 1));

                    if (flag1 || flag2) {
                        mutable.setWithOffset(pos, xOffset, i, zOffset);

                        boolean moveDown = (xOffset == -1 && zOffset == -1) || (xOffset == -1 && zOffset == 2) || (xOffset == 2 && zOffset == -1) || (xOffset == 2 && zOffset == 2);
                        if (moveDown) {
                            mutable.move(Direction.DOWN);
                        }
                        BlockState state = i == height && !moveDown ? config.capProvider.getState(random, pos) : config.capProvider.getState(random, pos).setValue(HugeMushroomBlock.WEST, xOffset < 0).setValue(HugeMushroomBlock.EAST, xOffset > 0).setValue(HugeMushroomBlock.NORTH, zOffset < 0).setValue(HugeMushroomBlock.SOUTH, zOffset > 0);
                        this.setBlock(world, mutable, state);
                    }
                }
            }

            for (int xOffset = 0; xOffset <= 1; xOffset++) {
                for (int zOffset = 0; zOffset <= 1; zOffset++) {
                    for (Direction direction : Direction.values()) {
                        if (direction.getAxis() != Direction.Axis.Y) {
                            mutable.setWithOffset(pos, xOffset, height - 5, zOffset);
                            mutable.move(direction);
                            if (world.getBlockState(mutable).isAir()) {
                                this.setBlock(world, mutable, config.capProvider.getState(random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false));
                            }
                        }
                    }
                }
            }
        }
        int xOffset = random.nextInt(2);
        int zOffset = random.nextInt(2);

        if (config.variant == 0) {
            placeSmallCap(world, random, pos, height, mutable, xOffset, zOffset, config);
            placeSmallCap(world, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        } else {
            placeSmallFungyss(world, random, pos, height - 1, mutable, xOffset, zOffset, config);
            placeSmallFungyss(world, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        }
    }

    private void placeSmallCap(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        this.setBlock(world, mutable, config.capProvider.getState(random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false));

        for (int i = 0; i <= 1; i++) {
            direction = direction == Direction.SOUTH ? Direction.EAST : Direction.from2DDataValue(direction.get2DDataValue() - 1);
            mutable.move(direction);

            this.setBlock(world, mutable, config.capProvider.getState(random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite().getClockWise()), false));
        }
    }

    private void placeSmallFungyss(LevelAccessor world, Random random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);
        int stemHeight = world.getRandom().nextInt(2) + 2;

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        if (stemHeight == 3) {
            mutable.move(Direction.DOWN);
        }

        this.setBlock(world, mutable, config.stemProvider.getState(random, pos).setValue(BlockStateProperties.AXIS, direction.getAxis()));

        mutable.move(direction);
        this.setBlock(world, mutable, config.hyphaeProvider.getState(random, pos).setValue(BlockStateProperties.AXIS, direction.getAxis()));

        for (int i = 0; i < stemHeight; i++) {
            mutable.move(Direction.UP);
            this.setBlock(world, mutable, config.stemProvider.getState(random, pos).setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
        }

        pos = mutable.immutable();

        int distanceToStem = 1;
        for (int i = stemHeight; i <= stemHeight + 1; i++) {
            for (int xPos = -distanceToStem; xPos <= distanceToStem; xPos++) {
                for (int zPos = -distanceToStem; zPos <= distanceToStem; zPos++) {
                    if ((i < stemHeight + 1 && !(xPos == 0 && zPos == 0)) || !isCorner(xPos, zPos, distanceToStem)) {
                        mutable.setWithOffset(pos, xPos, i - stemHeight, zPos);
                        this.setBlock(world, mutable, config.capProvider.getState(random, pos));
                    }
                }
            }
        }
        this.setBlock(world, pos.above(), config.capProvider.getState(random, pos));
    }

    private Direction getDirectionFromOffset(int xOffset, int zOffset) {
        if (xOffset == 0 && zOffset == 0) {
            return Direction.NORTH;
        } else if (xOffset == 0 && zOffset == 1) {
            return Direction.WEST;
        } else if (xOffset == 1 && zOffset == 0) {
            return Direction.EAST;
        }
        return Direction.SOUTH;
    }

    private boolean isCorner(int xOffset, int zOffset, int distanceToStem) {
        return (xOffset == -distanceToStem || xOffset == distanceToStem) == (zOffset == -distanceToStem || zOffset == distanceToStem);
    }
}