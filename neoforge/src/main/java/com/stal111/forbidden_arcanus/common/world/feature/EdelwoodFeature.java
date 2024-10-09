package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.Nonnull;

/**
 * Edelwood Feature <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.EdelwoodFeature
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-23
 */
public class EdelwoodFeature extends Feature<NoneFeatureConfiguration> {

    private static final BlockState EDELWOOD_LOG = ModBlocks.EDELWOOD_LOG.get().defaultBlockState();
    private static final BlockState CARVED_EDELWOOD_LOG = ModBlocks.CARVED_EDELWOOD_LOG.get().defaultBlockState().setValue(ModBlockStateProperties.LEAVES, true);
    private static final BlockState EDELWOOD_BRANCH = ModBlocks.EDELWOOD_BRANCH.get().defaultBlockState();

    private static final int MAX_HEIGHT = 4;
    private static final int MIN_HEIGHT = 2;

    public EdelwoodFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        BlockPos.MutableBlockPos mutable = pos.mutable();
        RandomSource random = context.random();

        if (!level.isEmptyBlock(pos.above()) || !level.getBlockState(pos.below()).is(BlockTags.DIRT)) {
            return false;
        }

        int height = 1;
        Direction direction = Direction.from2DDataValue(random.nextInt(4));

        while (level.isEmptyBlock(mutable) && height <= MAX_HEIGHT) {
            if (height == MAX_HEIGHT || !level.isEmptyBlock(mutable.above()) || (height >= MIN_HEIGHT && random.nextDouble() < 0.35D)) {
                level.setBlock(mutable, CARVED_EDELWOOD_LOG.setValue(BlockStateProperties.HORIZONTAL_FACING, direction), 2);
                break;
            } else {
                level.setBlock(mutable, EDELWOOD_LOG, 2);
            }
            mutable.move(Direction.UP);
            height++;
        }

        if (height >= 3 && random.nextDouble() < 0.65D) {
            direction = direction.getClockWise();

            mutable.move(Direction.DOWN).move(direction);
            level.setBlock(mutable, EDELWOOD_BRANCH.setValue(BlockStateProperties.HORIZONTAL_FACING, direction), 2);

            mutable.move(direction.getOpposite(), 2);
            level.setBlock(mutable, EDELWOOD_BRANCH.setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite()), 2);
        }

        return true;
    }
}
