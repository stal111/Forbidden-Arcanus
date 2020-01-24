package com.stal111.forbidden_arcanus.world;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.block.EdelwoodLogBlock;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

public class BiomeFeatures {

    public static final TreeFeatureConfig CHERRYWOOD_TREE = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LEAVES.getState()), new AcaciaFoliagePlacer(2, 0))).baseHeight(5).func_227354_b_(2).func_227355_c_(2).func_227356_e_(0).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable) ModBlocks.CHERRYWOOD_SAPLING.getBlock()).build();
    public static final TreeFeatureConfig MYSTERYWOOD_TREE = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LEAVES.getState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).func_227354_b_(2).func_227360_i_(3).func_227352_a_().setSapling((net.minecraftforge.common.IPlantable) ModBlocks.MYSTERYWOOD_SAPLING.getBlock()).build();
    public static final BlockClusterFeatureConfig EDELWOOD = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.EDELWOOD_LOG.getState().with(EdelwoodLogBlock.LEAVES, true)), new ColumnBlockPlacer(2, 2))).func_227316_a_(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227315_a_(10).func_227317_b_().func_227322_d_();
    public static final BlockClusterFeatureConfig YELLOW_ORCHID = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.YELLOW_ORCHID.getState()), new SimpleBlockPlacer())).func_227316_a_(ImmutableSet.of(Blocks.GRASS_BLOCK)).func_227315_a_(10).func_227322_d_();
}
