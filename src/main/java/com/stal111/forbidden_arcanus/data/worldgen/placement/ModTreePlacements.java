package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.*;

/**
 * Mod Tree Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-12-29
 */
public class ModTreePlacements {

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final PlacedFeature EDELWOOD_TREES = register("edelwood_trees", ModConfiguredFeatures.EDELWOOD.placed(InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.countExtra(13, 0.25F, 2), BiomeFilter.biome()));

    private static PlacedFeature register(String name, PlacedFeature placedFeature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), placedFeature);
    }
}
