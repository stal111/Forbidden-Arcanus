package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

/**
 * Mod Cave Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-12-29
 */
public class ModCavePlacements {

    public static final PlacedFeature PETRIFIED_ROOT = register("petrified_root", ModConfiguredFeatures.PETRIFIED_ROOT.placed(HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50)), InSquarePlacement.spread(), CountPlacement.of(UniformInt.of(173, 256)), EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome()));

    private static PlacedFeature register(String name, PlacedFeature placedFeature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), placedFeature);
    }
}
