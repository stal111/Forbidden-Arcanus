package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

import java.util.List;
import java.util.function.Supplier;

/**
 * Mod Tree Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-29
 */
public class ModTreePlacements implements RegistryClass {

    public static final RegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.PLACED_FEATURE_REGISTRY);

    public static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilter.forMaxDepth(0);

    public static final RegistryObject<PlacedFeature> EDELWOOD_TREES = register("edelwood_trees", ModConfiguredFeatures.EDELWOOD.getHolder().get(), () -> List.of(InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.countExtra(18, 0.25F, 3), BiomeFilter.biome()));
    public static final RegistryObject<PlacedFeature> SMALL_CHERRY_CHECKED = register("small_cherry_checked", ModConfiguredFeatures.SMALL_CHERRYWOOD.getHolder().get(), () -> List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRYWOOD_SAPLING.get())));
    public static final RegistryObject<PlacedFeature> LARGE_CHERRY_CHECKED = register("large_cherry_checked", ModConfiguredFeatures.LARGE_CHERRYWOOD.getHolder().get(), () -> List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.CHERRYWOOD_SAPLING.get())));

    private static RegistryObject<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return HELPER.register(name, () -> new PlacedFeature(Holder.hackyErase(feature), modifiers.get()));
    }
}
