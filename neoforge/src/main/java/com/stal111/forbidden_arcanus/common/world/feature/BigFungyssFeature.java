package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class BigFungyssFeature extends Feature<BigFungyssConfiguration> {

    private static final Predicate<BlockState> CAN_REPLACE = state -> state.isAir() || (!state.is(BlockTags.FEATURES_CANNOT_REPLACE) && state.is(BlockTags.REPLACEABLE_BY_TREES));

    public BigFungyssFeature(Codec<BigFungyssConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<BigFungyssConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        BigFungyssConfiguration config = context.config();

        int height = config.height().sample(random);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        if (!this.canGenerate(level, pos, height, mutable)) {
            return false;
        }

        this.placeStem(level, random, pos, height, mutable, config);
        this.placeCap(level, random, pos, height, mutable, config);

        return true;
    }

    private boolean canGenerate(LevelAccessor level, BlockPos pos, int height, BlockPos.MutableBlockPos mutable) {
        if (pos.getY() < level.getMinY() + 1 || pos.getY() + height + 1 > level.getMaxY()) {
            return false;
        }

        if (!level.getBlockState(pos.below()).is(Tags.Blocks.STONES)) {
            return false;
        }

        for (int i = 0; i <= height; i++) {
            if (!CAN_REPLACE.test(level.getBlockState(mutable.setWithOffset(pos, 0, i, 0)))) {
                return false;
            }
        }
        return true;
    }

    private void placeStem(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssConfiguration config) {
        for (int i = 0; i < height; i++) {
            mutable.setWithOffset(pos, 0, i, 0);
            this.safeSetBlock(level, mutable, config.stemProvider().getState(level, random, mutable), CAN_REPLACE);
        }
    }

    private void placeCap(WorldGenLevel level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutable, BigFungyssConfiguration config) {
        if (config.capStyle() == BigFungyssConfiguration.CapStyle.SMALL) {
            int distanceToStem = 1;
            for (int i = height - 2; i <= height; i++) {
                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        boolean isCenter = xOffset == 0 && zOffset == 0;
                        boolean isCorner = Math.abs(xOffset) == distanceToStem && Math.abs(zOffset) == distanceToStem;

                        if (i < height ? !isCenter : !isCorner) {
                            mutable.setWithOffset(pos, xOffset, i, zOffset);

                            this.safeSetBlock(level, mutable, config.capProvider().getState(level, random, mutable), CAN_REPLACE);
                        }
                    }
                }
            }
        } else {
            for (int i = height - 2; i <= height; i++) {
                int distanceToStem = i < height ? 2 : 1;

                for (int xOffset = -distanceToStem; xOffset <= distanceToStem; xOffset++) {
                    for (int zOffset = -distanceToStem; zOffset <= distanceToStem; zOffset++) {
                        boolean isOuterEdgeWithoutCorners = (Math.abs(xOffset) == distanceToStem) != (Math.abs(zOffset) == distanceToStem);

                        if (i >= height || isOuterEdgeWithoutCorners) {
                            mutable.setWithOffset(pos, xOffset, i, zOffset);
                            BlockState state = config.capProvider().getState(level, random, mutable)
                                    .setValue(HugeMushroomBlock.UP, i >= height - 1)
                                    .setValue(HugeMushroomBlock.WEST, xOffset < 0)
                                    .setValue(HugeMushroomBlock.EAST, xOffset > 0)
                                    .setValue(HugeMushroomBlock.NORTH, zOffset < 0)
                                    .setValue(HugeMushroomBlock.SOUTH, zOffset > 0);

                            this.safeSetBlock(level, mutable, state, CAN_REPLACE);
                        }
                    }
                }
            }

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                mutable.setWithOffset(pos, direction.getStepX(), height - 4, direction.getStepZ());
                BlockState state = config.capProvider().getState(level, random, mutable).setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), false);

                this.safeSetBlock(level, mutable, state, CAN_REPLACE);
            }
        }
    }
}