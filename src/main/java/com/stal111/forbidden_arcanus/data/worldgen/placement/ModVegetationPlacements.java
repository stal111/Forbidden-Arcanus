package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Mod Vegetation Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-12-29
 */
public class ModVegetationPlacements {

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final Holder<PlacedFeature> YELLOW_ORCHID = register("yellow_orchid", ModConfiguredFeatures.YELLOW_ORCHID, List.of(RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome()));
    public static final Holder<PlacedFeature> CHERRY_TREES_PLAINS = register("cherry_trees_plains", ModVegetationFeatures.CHERRY_TREES_PLAINS, List.of(PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.CHERRYWOOD_SAPLING.get().defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome()));

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PlacementUtils.register(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString(), feature, modifiers);
    }

}
