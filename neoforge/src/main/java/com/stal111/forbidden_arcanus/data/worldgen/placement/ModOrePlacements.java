package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.List;

/**
 * Mod Ore Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements
 *
 * @author stal111
 * @since 2021-12-28
 */
public class ModOrePlacements extends DatapackRegistryClass<PlacedFeature> {

    public static final DatapackRegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> ARCANE_CRYSTAL_ORE = HELPER.createKey("arcane_crystal_ore");
    public static final ResourceKey<PlacedFeature> RUNIC_STONE = HELPER.createKey("runic_stone");
    public static final ResourceKey<PlacedFeature> RUNIC_STONE_LOWER = HELPER.createKey("runic_stone_lower");
    public static final ResourceKey<PlacedFeature> DARKSTONE = HELPER.createKey("darkstone");
    public static final ResourceKey<PlacedFeature> STELLA_ARCANUM = HELPER.createKey("stella_arcanum");

    public ModOrePlacements(BootstrapContext<PlacedFeature> context) {
        super(context);
    }

    private List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier placementModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }

    private List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return this.orePlacement(CountPlacement.of(count), placementModifier);
    }

    @Override
    public void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, ARCANE_CRYSTAL_ORE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.ARCANE_CRYSTAL_ORE), this.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(14))));
        PlacementUtils.register(context, RUNIC_STONE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.RUNIC_STONE), this.commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(2))));
        PlacementUtils.register(context, RUNIC_STONE_LOWER, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.RUNIC_STONE_LOWER), this.commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-85), VerticalAnchor.absolute(-20))));
        PlacementUtils.register(context, DARKSTONE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.DARKSTONE), this.commonOrePlacement(28, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(13))));
        PlacementUtils.register(context, STELLA_ARCANUM, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.STELLA_ARCANUM), this.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-44), VerticalAnchor.absolute(42))));
    }
}
