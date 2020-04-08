package com.stal111.forbidden_arcanus.world;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.block.EdelwoodLogBlock;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;

public class BiomeFeatures {

    public static final TreeFeatureConfig CHERRYWOOD_TREE = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LEAVES.getState()), new AcaciaFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).heightRandB(2).trunkHeight(0).ignoreVines().setSapling((net.minecraftforge.common.IPlantable) ModBlocks.CHERRYWOOD_SAPLING.getBlock()).build();
    public static final TreeFeatureConfig MYSTERYWOOD_TREE = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LEAVES.getState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling((net.minecraftforge.common.IPlantable) ModBlocks.MYSTERYWOOD_SAPLING.getBlock()).build();
    public static final BlockClusterFeatureConfig EDELWOOD = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.EDELWOOD_LOG.getState().with(EdelwoodLogBlock.LEAVES, true)), new ColumnBlockPlacer(2, 2))).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).tries(10).func_227317_b_().build();
    public static final BlockClusterFeatureConfig YELLOW_ORCHID = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.YELLOW_ORCHID.getState()), new SimpleBlockPlacer())).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).tries(10).build();
    public static final Feature<ReplaceBlockConfig> XPETRIFIED_ORE = new ReplaceBlockFeature(ReplaceBlockConfig::deserialize);
}
