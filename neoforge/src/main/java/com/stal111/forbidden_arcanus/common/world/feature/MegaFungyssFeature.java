package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;

/**
 * Mega Fungyss Feature
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.MegaFungyssFeature
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-04-13
 */
public class MegaFungyssFeature extends Feature<BigFungyssFeatureConfig> {

    public MegaFungyssFeature(Codec<BigFungyssFeatureConfig> codec) {
        super(codec);
    }

    private int getRandomHeight(RandomSource random) {
        return random.nextInt(4) + 11;
    }

    private boolean canGenerate(LevelAccessor world, ChunkGenerator generator, BlockPos pos, int height, BlockPos.MutableBlockPos mutable) {
        if (pos.getY() < 1 || pos.getY() + height + 1 >= generator.getGenDepth()) {
            return false;
        }

        if (!world.getBlockState(pos.below()).is(Tags.Blocks.STONES)) {
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
        RandomSource rand = context.random();

        int height = this.getRandomHeight(context.random());

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!this.canGenerate(level, context.chunkGenerator(), pos, height, mutable)) {
            return false;
        }

        this.placeStem(level, rand, pos, height, mutable, context.config());
        this.placeCap(level, rand, pos, height, mutable, context.config());
        return true;
    }


    private void placeStem(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            this.placeStemBlock(level, config.stemProvider.getState(level, random, pos), mutable, pos, 0, i, 0);
            this.placeStemBlock(level, config.stemProvider.getState(level, random, pos), mutable, pos, 1, i, 0);
            this.placeStemBlock(level, config.stemProvider.getState(level, random, pos), mutable, pos, 1, i, 1);
            this.placeStemBlock(level, config.stemProvider.getState(level, random, pos), mutable, pos, 0, i, 1);
        }
    }

    private void placeStemBlock(WorldGenLevel level, BlockState state, BlockPos.MutableBlockPos mutable, BlockPos pos, int xOffset, int yOffset, int zOffset) {
        mutable.set(pos).move(xOffset, yOffset, zOffset);

        if (!level.getBlockState(mutable).isSolidRender()) {
            this.setBlock(level, mutable, state);
        }
    }

    private void placeCap(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
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
                        BlockState state = i == height && !moveDown ? config.capProvider.getState(level, random, pos) : config.capProvider.getState(level, random, pos).setValue(HugeMushroomBlock.WEST, xOffset < 0).setValue(HugeMushroomBlock.EAST, xOffset > 0).setValue(HugeMushroomBlock.NORTH, zOffset < 0).setValue(HugeMushroomBlock.SOUTH, zOffset > 0);
                        this.setBlock(level, mutable, state);
                    }
                }
            }

            for (int xOffset = 0; xOffset <= 1; xOffset++) {
                for (int zOffset = 0; zOffset <= 1; zOffset++) {
                    for (Direction direction : Direction.values()) {
                        if (direction.getAxis() != Direction.Axis.Y) {
                            mutable.setWithOffset(pos, xOffset, height - 5, zOffset);
                            mutable.move(direction);
                            if (level.getBlockState(mutable).isAir()) {
                                this.setBlock(level, mutable, config.capProvider.getState(level, random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false));
                            }
                        }
                    }
                }
            }
        }
        int xOffset = random.nextInt(2);
        int zOffset = random.nextInt(2);

        if (config.variant == 0) {
            placeSmallCap(level, random, pos, height, mutable, xOffset, zOffset, config);
            placeSmallCap(level, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        } else {
            placeSmallFungyss(level, random, pos, height - 1, mutable, xOffset, zOffset, config);
            placeSmallFungyss(level, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        }
    }

    private void placeSmallCap(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        this.setBlock(level, mutable, config.capProvider.getState(level, random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false));

        for (int i = 0; i <= 1; i++) {
            direction = direction == Direction.SOUTH ? Direction.EAST : Direction.from2DDataValue(direction.get2DDataValue() - 1);
            mutable.move(direction);

            this.setBlock(level, mutable, config.capProvider.getState(level, random, pos).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite().getClockWise()), false));
        }
    }

    private void placeSmallFungyss(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);
        int stemHeight = level.getRandom().nextInt(2) + 2;

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        if (stemHeight == 3) {
            mutable.move(Direction.DOWN);
        }

        this.setBlock(level, mutable, config.stemProvider.getState(level, random, pos).setValue(BlockStateProperties.AXIS, direction.getAxis()));

        mutable.move(direction);
        this.setBlock(level, mutable, config.hyphaeProvider.getState(level, random, pos).setValue(BlockStateProperties.AXIS, direction.getAxis()));

        for (int i = 0; i < stemHeight; i++) {
            mutable.move(Direction.UP);
            this.setBlock(level, mutable, config.stemProvider.getState(level, random, pos).setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
        }

        pos = mutable.immutable();

        int distanceToStem = 1;
        for (int i = stemHeight; i <= stemHeight + 1; i++) {
            for (int xPos = -distanceToStem; xPos <= distanceToStem; xPos++) {
                for (int zPos = -distanceToStem; zPos <= distanceToStem; zPos++) {
                    if ((i < stemHeight + 1 && !(xPos == 0 && zPos == 0)) || !isCorner(xPos, zPos, distanceToStem)) {
                        mutable.setWithOffset(pos, xPos, i - stemHeight, zPos);
                        this.setBlock(level, mutable, config.capProvider.getState(level, random, pos));
                    }
                }
            }
        }
        this.setBlock(level, pos.above(), config.capProvider.getState(level, random, pos));
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