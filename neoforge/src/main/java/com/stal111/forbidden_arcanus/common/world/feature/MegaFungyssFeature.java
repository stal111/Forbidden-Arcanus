package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class MegaFungyssFeature extends Feature<BigFungyssFeatureConfig> {

    private static final Predicate<BlockState> CAN_REPLACE = state -> state.isAir() || (!state.is(BlockTags.FEATURES_CANNOT_REPLACE) && state.is(BlockTags.REPLACEABLE_BY_TREES));

    public MegaFungyssFeature(Codec<BigFungyssFeatureConfig> codec) {
        super(codec);
    }

    private int getRandomHeight(RandomSource random) {
        return random.nextInt(4) + 11;
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<BigFungyssFeatureConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BigFungyssFeatureConfig config = context.config();

        int height = this.getRandomHeight(random);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!this.canGenerate(level, pos, height, mutable)) {
            return false;
        }

        this.placeStem(level, random, pos, height, mutable, config);
        this.placeCap(level, random, pos, height, mutable, config);

        return true;
    }

    private boolean canGenerate(LevelAccessor level, BlockPos pos, int height, BlockPos.MutableBlockPos mutable) {
        if (pos.getY() <= level.getMinY() || pos.getY() + height + 1 > level.getMaxY()) {
            return false;
        }

        for (int xOffset = 0; xOffset <= 1; xOffset++) {
            for (int zOffset = 0; zOffset <= 1; zOffset++) {
                if (!level.getBlockState(mutable.setWithOffset(pos, xOffset, -1, zOffset)).is(Tags.Blocks.STONES)) {
                    return false;
                }
                for (int yOffset = 0; yOffset <= height; yOffset++) {
                    if (!CAN_REPLACE.test(level.getBlockState(mutable.setWithOffset(pos, xOffset, yOffset, zOffset)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeStem(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            for (int xOffset = 0; xOffset <= 1; xOffset++) {
                for (int zOffset = 0; zOffset <= 1; zOffset++) {
                    mutable.setWithOffset(pos, xOffset, i, zOffset);

                    this.safeSetBlock(level, mutable, config.stemProvider().getState(level, random, mutable), CAN_REPLACE);
                }
            }
        }
    }

    private void placeCap(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssFeatureConfig config) {
        int distanceToStem = 2;

        for (int i = height - 3; i <= height; i++) {
            for (int xOffset = -distanceToStem; xOffset <= distanceToStem + 1; xOffset++) {
                for (int zOffset = -distanceToStem; zOffset <= distanceToStem + 1; zOffset++) {
                    boolean onOuterX = xOffset == -distanceToStem || xOffset == distanceToStem + 1;
                    boolean onOuterZ = zOffset == -distanceToStem || zOffset == distanceToStem + 1;
                    boolean isTopLayer = i >= height && !onOuterX && !onOuterZ;
                    boolean isSkirtLayer = i < height && (onOuterX != onOuterZ);

                    if (isTopLayer || isSkirtLayer) {
                        boolean moveDown = (xOffset == -1 || xOffset == 2) && (zOffset == -1 || zOffset == 2);
                        mutable.setWithOffset(pos, xOffset, moveDown ? i - 1 : i, zOffset);
                        BlockState state = config.capProvider().getState(level, random, mutable);

                        if (i != height || moveDown) {
                            state = state.setValue(HugeMushroomBlock.WEST, xOffset < 0)
                                    .setValue(HugeMushroomBlock.EAST, xOffset > 0)
                                    .setValue(HugeMushroomBlock.NORTH, zOffset < 0)
                                    .setValue(HugeMushroomBlock.SOUTH, zOffset > 0);
                        }
                        this.safeSetBlock(level, mutable, state, CAN_REPLACE);
                    }
                }
            }
        }

        for (int xOffset = 0; xOffset <= 1; xOffset++) {
            for (int zOffset = 0; zOffset <= 1; zOffset++) {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    mutable.setWithOffset(pos, xOffset + direction.getStepX(), height - 5, zOffset + direction.getStepZ());
                    BlockState state = config.capProvider().getState(level, random, mutable).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false);

                    this.safeSetBlock(level, mutable, state, CAN_REPLACE);
                }
            }
        }

        int xOffset = random.nextInt(2);
        int zOffset = random.nextInt(2);

        if (config.variant() == 0) {
            this.placeSmallCap(level, random, pos, height, mutable, xOffset, zOffset, config);
            this.placeSmallCap(level, random, pos, height - 2, mutable, 1 - xOffset, 1 - zOffset, config);
        } else {
            this.placeSmallFungyss(level, random, pos, height - 1, mutable, xOffset, zOffset, config);
            this.placeSmallFungyss(level, random, pos, height - 2, mutable, 1 - xOffset, 1 - zOffset, config);
        }
    }

    private void placeSmallCap(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = this.getDirectionFromOffset(xOffset, zOffset);

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset).move(direction);

        this.safeSetBlock(level, mutable, config.capProvider().getState(level, random, mutable).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false), CAN_REPLACE);

        for (int i = 0; i <= 1; i++) {
            direction = direction.getCounterClockWise();
            mutable.move(direction);

            this.safeSetBlock(level, mutable, config.capProvider().getState(level, random, mutable).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite().getClockWise()), false), CAN_REPLACE);
        }
    }

    private void placeSmallFungyss(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = this.getDirectionFromOffset(xOffset, zOffset);
        int stemHeight = random.nextInt(2) + 2;

        mutable.setWithOffset(pos, xOffset, height - 8, zOffset).move(direction);

        if (stemHeight == 3) {
            mutable.move(Direction.DOWN);
        }

        this.safeSetBlock(level, mutable, config.stemProvider().getState(level, random, mutable).setValue(BlockStateProperties.AXIS, direction.getAxis()), CAN_REPLACE);

        mutable.move(direction);
        this.safeSetBlock(level, mutable, config.hyphaeProvider().getState(level, random, mutable).setValue(BlockStateProperties.AXIS, direction.getAxis()), CAN_REPLACE);

        for (int i = 0; i < stemHeight; i++) {
            mutable.move(Direction.UP);

            this.safeSetBlock(level, mutable, config.stemProvider().getState(level, random, mutable).setValue(BlockStateProperties.AXIS, Direction.Axis.Y), CAN_REPLACE);
        }

        BlockPos topStemPos = mutable.immutable();
        int distanceToStem = 1;

        for (int yOffset = 0; yOffset <= 1; yOffset++) {
            for (int xPos = -distanceToStem; xPos <= distanceToStem; xPos++) {
                for (int zPos = -distanceToStem; zPos <= distanceToStem; zPos++) {
                    boolean isCenter = xPos == 0 && zPos == 0;
                    boolean isCorner = Math.abs(xPos) == distanceToStem && Math.abs(zPos) == distanceToStem;

                    if (yOffset == 0 ? !isCenter : !isCorner) {
                        mutable.setWithOffset(topStemPos, xPos, yOffset, zPos);

                        this.safeSetBlock(level, mutable, config.capProvider().getState(level, random, mutable), CAN_REPLACE);
                    }
                }
            }
        }
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
}