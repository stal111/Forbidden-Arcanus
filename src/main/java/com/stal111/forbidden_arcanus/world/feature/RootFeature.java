package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Root Feature
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.feature.RootFeature
 *
 * @author stal111
 * @version 1.17.1-2.0.0
 */
public class RootFeature extends Feature<BlockStateConfiguration> {

    public RootFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<BlockStateConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockState state = context.config().state;
        BlockPos.MutableBlockPos pos = context.origin().mutable();
        Random rand = context.random();

        if (!level.isEmptyBlock(pos) || !Tags.Blocks.STONE.contains(level.getBlockState(pos.above()).getBlock()) || !level.isEmptyBlock(pos.below())) {
            return false;
        }

        level.setBlock(pos, state, 2);

        for (int i = 1; i < 6; i++) {
            pos = this.tryChangeDirection(level, pos.move(Direction.DOWN), rand, 0.25);

            if (level.isEmptyBlock(pos)) {
                level.setBlock(pos, state, 2);
            } else {
                break;
            }
        }

        return true;
    }

    private BlockPos.MutableBlockPos tryChangeDirection(WorldGenLevel level, BlockPos.MutableBlockPos pos, Random random, double chance) {
        if (random.nextFloat() >= chance) {
            return pos;
        }
        Direction direction = this.getRandomDirection(random);
        BlockPos.MutableBlockPos relativePos = pos.move(direction);

        return !level.getBlockState(relativePos).isSolidRender(level, relativePos) ? relativePos : pos;
    }

    private Direction getRandomDirection(Random random) {
        return Direction.from2DDataValue(random.nextInt(4));
    }
}
