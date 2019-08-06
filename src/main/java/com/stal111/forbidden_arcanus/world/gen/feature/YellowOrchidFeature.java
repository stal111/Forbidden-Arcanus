package com.stal111.forbidden_arcanus.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.stal111.forbidden_arcanus.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class YellowOrchidFeature extends FlowersFeature {

    public YellowOrchidFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49876_1_) {
        super(p_i49876_1_);
    }

    @Override
    public BlockState getRandomFlower(Random random, BlockPos pos) {
        return ModBlocks.yellow_orchid.getDefaultState();
    }
}
