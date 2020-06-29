package com.stal111.forbidden_arcanus.world.gen.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

public class PetrifiedRootFeature extends Feature<NoFeatureConfig> {

    public PetrifiedRootFeature(Codec<NoFeatureConfig> noFeatureConfigCodec) {
        super(noFeatureConfigCodec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader seedReader, StructureManager manager, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig noFeatureConfig) {
        int i = 0;

        if (!seedReader.isAirBlock(pos)) {
            return false;
        } else if (seedReader.getBlockState(pos.up()).getBlock() != Blocks.STONE) {
            return false;
        } else {
            seedReader.setBlockState(pos, ModBlocks.PETRIFIED_ROOT.getState(), 2);
            Direction direction = null;
            for (int j = 1; j <= 5; j++) {
                if (rand.nextDouble() >= 0.1D * i) {
                    if (j == 1 && rand.nextDouble() <= 0.5D) {
                        for (Direction direction1 : Direction.values()) {
                            if (seedReader.getBlockState(pos.offset(direction1)).isAir(seedReader, pos.offset(direction1))) {
                                direction = direction1;
                            }
                        }
                    }
                    if (direction != null) {
                        if (seedReader.getBlockState(pos.down(j).offset(direction)).isAir(seedReader, pos.down(j).offset(direction))) {
                            seedReader.setBlockState(pos.down(j).offset(direction), ModBlocks.PETRIFIED_ROOT.getState(), 2);
                            i = j;
                        }
                    } else {
                        if (seedReader.getBlockState(pos.down(j)).isAir(seedReader, pos.down(j))) {
                            seedReader.setBlockState(pos.down(j), ModBlocks.PETRIFIED_ROOT.getState(), 2);
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
