package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

/**
 * Mod Vegetation Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-12-29
 */
public class ModVegetationPlacements {

    public static final PlacedFeature YELLOW_ORCHID = register("yellow_orchid", ModConfiguredFeatures.YELLOW_ORCHID.placed(RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), BiomeFilter.biome()));

    private static PlacedFeature register(String name, PlacedFeature placedFeature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), placedFeature);
    }
}
