package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Mod Ore Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-12-28
 */
public class ModOrePlacements {

    public static final PlacedFeature ARCANE_CRYSTAL_ORE = register("arcane_crystal_ore", ModConfiguredFeatures.ARCANE_CRYSTAL_ORE.placed(commonOrePlacement(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(14)))));
    public static final PlacedFeature RUNIC_STONE = register("runic_stone", ModConfiguredFeatures.RUNIC_STONE.placed(commonOrePlacement(WorldGenConfig.RUNIC_STONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(2)))));
    public static final PlacedFeature DARKSTONE = register("darkstone", ModConfiguredFeatures.DARKSTONE.placed(commonOrePlacement(28, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(13)))));
    public static final PlacedFeature ARCANE_GILDED_DARKSTONE = register("arcane_gilded_darkstone", ModConfiguredFeatures.ARCANE_GILDED_DARKSTONE.placed(commonOrePlacement(WorldGenConfig.ARCANE_GILDED_DARKSTONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(13)))));
    public static final PlacedFeature STELLA_ARCANUM = register("stella_arcanum", ModConfiguredFeatures.STELLA_ARCANUM.placed(commonOrePlacement(WorldGenConfig.STELLA_ARCANUM_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-44), VerticalAnchor.absolute(42)))));
    public static final PlacedFeature XPETRIFIED_ORE = register("xpetrified_ore", ModConfiguredFeatures.XPETRIFIED_ORE.placed(commonOrePlacement(18, HeightRangePlacement.uniform(VerticalAnchor.absolute(-6), VerticalAnchor.absolute(35)))));

    private static PlacedFeature register(String name, PlacedFeature placedFeature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), placedFeature);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier placementModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }
}
