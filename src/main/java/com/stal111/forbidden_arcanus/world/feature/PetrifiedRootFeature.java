package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

public class PetrifiedRootFeature extends Feature<NoneFeatureConfiguration> {

    public PetrifiedRootFeature(Codec<NoneFeatureConfiguration> noFeatureConfigCodec) {
        super(noFeatureConfigCodec);
    }

    @Override
    public boolean place(@Nonnull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        int i = 0;

        if (level.isEmptyBlock(pos) && Tags.Blocks.STONE.contains(level.getBlockState(pos.above()).getBlock())) {
            level.setBlock(pos, ModBlocks.PETRIFIED_ROOT.getState(), 2);
            Direction direction = null;
            for (int j = 1; j <= 5; j++) {
                if (rand.nextDouble() >= 0.1D * i) {
                    if (j == 1 && rand.nextDouble() <= 0.5D) {
                        for (Direction direction1 : Direction.values()) {
                            if (level.getBlockState(pos.relative(direction1)).isAir()) {
                                direction = direction1;
                            }
                        }
                    }
                    if (direction != null) {
                        if (level.getBlockState(pos.below(j).relative(direction)).isAir()) {
                            level.setBlock(pos.below(j).relative(direction), ModBlocks.PETRIFIED_ROOT.getState(), 2);
                            i = j;
                        }
                    } else {
                        if (level.getBlockState(pos.below(j)).isAir()) {
                            level.setBlock(pos.below(j), ModBlocks.PETRIFIED_ROOT.getState(), 2);
                            i = j;
                        }
                    }
                } else {
                    break;
                }
            }
            return i > 0;
        } else {
            return false;
        }
    }
}
