package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

import java.util.List;
import java.util.function.Supplier;

/**
 * Mod Vegetation Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-29
 */
public class ModVegetationPlacements implements RegistryClass {

    public static final RegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.PLACED_FEATURE_REGISTRY);

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final RegistryObject<PlacedFeature> YELLOW_ORCHID = register("yellow_orchid", ModConfiguredFeatures.YELLOW_ORCHID.getHolder().get(), () -> List.of(RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome()));
    public static final RegistryObject<PlacedFeature> CHERRY_TREES_PLAINS = register("cherry_trees_plains", ModVegetationFeatures.CHERRY_TREES_PLAINS, () -> List.of(PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.CHERRY_SAPLING.get().defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome()));

    private static RegistryObject<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return HELPER.register(name, () -> new PlacedFeature(Holder.hackyErase(feature), modifiers.get()));
    }

}
