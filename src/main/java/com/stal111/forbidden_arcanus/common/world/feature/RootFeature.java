package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import javax.annotation.Nonnull;

/**
 * Root Feature <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.RootFeature
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class RootFeature extends Feature<BlockStateConfiguration> {

    private static final double DIRECTION_CHANGE_CHANCE = 0.25D;

    public RootFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<BlockStateConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockState state = context.config().state;
        BlockPos.MutableBlockPos pos = context.origin().mutable();
        RandomSource rand = context.random();

        level.setBlock(pos, state, 2);

        for (int i = 1; i < 6; i++) {
            pos = this.tryChangeDirection(level, pos.move(Direction.DOWN), rand, DIRECTION_CHANGE_CHANCE);

            if (level.isEmptyBlock(pos)) {
                level.setBlock(pos, state, 2);
            } else {
                break;
            }
        }

        return true;
    }

    private BlockPos.MutableBlockPos tryChangeDirection(WorldGenLevel level, BlockPos.MutableBlockPos pos, RandomSource random, double chance) {
        if (random.nextFloat() >= chance) {
            return pos;
        }
        Direction direction = this.getRandomDirection(random);
        BlockPos.MutableBlockPos relativePos = pos.move(direction);

        return !level.getBlockState(relativePos).isSolidRender(level, relativePos) ? relativePos : pos;
    }

    private Direction getRandomDirection(RandomSource random) {
        return Direction.from2DDataValue(random.nextInt(4));
    }
}
