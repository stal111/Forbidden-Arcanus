package com.stal111.forbidden_arcanus.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;
import java.util.function.Function;

public class PetrifiedRootFeature extends Feature<NoFeatureConfig> {

    public PetrifiedRootFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        if (!world.isAirBlock(pos)) {
            return false;
        } else if (world.getBlockState(pos.up()).getBlock() != Blocks.STONE) {
            return false;
        } else {
            world.setBlockState(pos, ModBlocks.PETRIFIED_ROOT.getState(), 2);
            System.out.println("GENERATED ROOT AT: " + pos.toString());
            Direction direction = null;
            for (int j = 1; j <= 5; j++) {
                if (rand.nextDouble() >= 0.1D * i) {
                    if (j == 1 && rand.nextDouble() <= 0.5D) {
                        for (Direction direction1 : Direction.values()) {
                            if (world.getBlockState(pos.offset(direction1)).isAir(world, pos.offset(direction1))) {
                                direction = direction1;
                            }
                        }
                    }
                    if (direction != null) {
                        if (world.getBlockState(pos.down(j).offset(direction)).isAir(world, pos.down(j).offset(direction))) {
                            world.setBlockState(pos.down(j).offset(direction), ModBlocks.PETRIFIED_ROOT.getState(), 2);
                            i = j;
                        }
                    } else {
                        if (world.getBlockState(pos.down(j)).isAir(world, pos.down(j))) {
                            world.setBlockState(pos.down(j), ModBlocks.PETRIFIED_ROOT.getState(), 2);
                            i = j;
                        }
                    }
                } else {
                    break;
                }
            }
        }
        return i > 0;
    }
}
