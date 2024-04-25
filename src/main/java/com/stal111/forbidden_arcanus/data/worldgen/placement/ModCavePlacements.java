package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * Mod Cave Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements
 *
 * @author stal111
 * @since 2021-12-29
 */
public class ModCavePlacements extends DatapackRegistryClass<PlacedFeature> {

    public static final DatapackRegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> PETRIFIED_ROOT = HELPER.createKey("petrified_root");

    public ModCavePlacements(BootstrapContext<PlacedFeature> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<PlacedFeature> context) {
        PlacementUtils.register(context, PETRIFIED_ROOT, context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(ModConfiguredFeatures.PETRIFIED_ROOT), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(50)), InSquarePlacement.spread(), CountPlacement.of(UniformInt.of(173, 256)), EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
    }
}
