package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Mod Tree Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-12-29
 */
public class ModTreePlacements {

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final Holder<PlacedFeature> EDELWOOD_TREES = register("edelwood_trees", ModConfiguredFeatures.EDELWOOD, List.of(InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(18, 0.25F, 3), BiomeFilter.biome()));

    private static Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return PlacementUtils.register(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString(), feature, modifiers);
    }

}
