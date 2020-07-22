package com.stal111.forbidden_arcanus.world;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.block.EdelwoodLogBlock;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;

import java.util.OptionalInt;

public class ModBiomeFeatures {

    public static final BaseTreeFeatureConfig CHERRYWOOD_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.CHERRYWOOD_LEAVES.getState()), new AcaciaFoliagePlacer(2, 0, 0, 0), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2))).func_236700_a_().build();
    public static final BaseTreeFeatureConfig MYSTERYWOOD_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LOG.getState()), new SimpleBlockStateProvider(ModBlocks.MYSTERYWOOD_LEAVES.getState()), new FancyFoliagePlacer(2, 0, 4, 0, 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).func_236700_a_().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();
    public static final BlockClusterFeatureConfig EDELWOOD_TREE = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.EDELWOOD_LOG.getState().with(EdelwoodLogBlock.LEAVES, true)), new ColumnBlockPlacer(2, 2))).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).tries(10).func_227317_b_().build();
    public static final BlockClusterFeatureConfig YELLOW_ORCHID = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.YELLOW_ORCHID.getState()), new SimpleBlockPlacer())).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).tries(10).build();
    public static final ReplaceBlockConfig XPETRIFIED_ORE = new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), ModBlocks.XPETRIFIED_ORE.getState());

}
