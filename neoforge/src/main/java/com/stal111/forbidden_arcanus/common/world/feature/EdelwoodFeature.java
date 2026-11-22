package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.world.feature.config.EdelwoodConfiguration;
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

public class EdelwoodFeature extends Feature<EdelwoodConfiguration> {

    public EdelwoodFeature(Codec<EdelwoodConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<EdelwoodConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        EdelwoodConfiguration config = context.config();
        int minHeight = config.height().minInclusive();

        if (level.isOutsideBuildHeight(pos) || pos.getY() + minHeight - 1 > level.getMaxY()) {
            return false;
        }

        int targetHeight = config.height().sample(random);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int height = 0;

        while (height < targetHeight) {
            mutable.setWithOffset(pos, 0, height, 0);

            if (level.isOutsideBuildHeight(mutable) || !this.canReplace(level.getBlockState(mutable))) {
                break;
            }

            height++;
        }

        if (height < minHeight) {
            return false;
        }

        Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockState log = ModBlocks.EDELWOOD_LOG.get().defaultBlockState();

        for (int y = 0; y < height - 1; y++) {
            mutable.setWithOffset(pos, 0, y, 0);

            this.setBlock(level, mutable, log);
        }

        BlockState carvedLog = ModBlocks.CARVED_EDELWOOD_LOG.get().defaultBlockState()
                .setValue(ModBlockStateProperties.LEAVES, true)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing);

        mutable.setWithOffset(pos, 0, height - 1, 0);
        this.setBlock(level, mutable, carvedLog);


        if (height >= config.branchMinHeight() && random.nextFloat() < config.branchChance()) {
            BlockState branch = ModBlocks.EDELWOOD_BRANCH.get().defaultBlockState();

            Direction branchDir = facing.getClockWise();
            Direction opposite = branchDir.getOpposite();
            int branchY = height - 2;

            this.tryPlaceBranch(level, mutable.setWithOffset(pos, branchDir.getStepX(), branchY, branchDir.getStepZ()), branch, branchDir);
            this.tryPlaceBranch(level, mutable.setWithOffset(pos, opposite.getStepX(), branchY, opposite.getStepZ()), branch, opposite);
        }

        return true;
    }

    private boolean canReplace(BlockState state) {
        return state.isAir() || (!state.is(BlockTags.FEATURES_CANNOT_REPLACE) && state.is(BlockTags.REPLACEABLE_BY_TREES));
    }

    private void tryPlaceBranch(WorldGenLevel level, BlockPos pos, BlockState branch, Direction facing) {
        if (this.canReplace(level.getBlockState(pos))) {
            this.setBlock(level, pos, branch.setValue(BlockStateProperties.HORIZONTAL_FACING, facing));
        }
    }
}
