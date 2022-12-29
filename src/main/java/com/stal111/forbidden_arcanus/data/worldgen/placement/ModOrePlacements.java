package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

import java.util.List;

/**
 * Mod Ore Placements <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements
 *
 * @author stal111
 * @since 2021-12-28
 */
public class ModOrePlacements extends DatapackRegistryClass<PlacedFeature> {

    public static final DatapackRegistryHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> ARCANE_CRYSTAL_ORE = HELPER.createKey("arcane_crystal_ore");
    public static final ResourceKey<PlacedFeature> RUNIC_STONE = HELPER.createKey("runic_stone");
    public static final ResourceKey<PlacedFeature> DARKSTONE = HELPER.createKey("darkstone");
    public static final ResourceKey<PlacedFeature> ARCANE_GILDED_DARKSTONE = HELPER.createKey("arcane_gilded_darkstone");
    public static final ResourceKey<PlacedFeature> STELLA_ARCANUM = HELPER.createKey("stella_arcanum");
    public static final ResourceKey<PlacedFeature> XPETRIFIED_ORE = HELPER.createKey("xpetrified_ore");

    public ModOrePlacements(DataProviderInfo info, BootstapContext<PlacedFeature> context) {
        super(info, context);
    }

    private List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier placementModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }

    private List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return this.orePlacement(CountPlacement.of(count), placementModifier);
    }

    @Override
    public void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(context, ARCANE_CRYSTAL_ORE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.ARCANE_CRYSTAL_ORE), this.commonOrePlacement(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), HeightRangePlacement.triangle(VerticalAnchor.absolute(WorldGenConfig.ARCANE_CRYSTAL_ORE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get()))));
        PlacementUtils.register(context, RUNIC_STONE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.RUNIC_STONE), this.commonOrePlacement(WorldGenConfig.RUNIC_STONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.RUNIC_STONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.RUNIC_STONE_MAX_HEIGHT.get()))));
        PlacementUtils.register(context, DARKSTONE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.DARKSTONE), this.commonOrePlacement(WorldGenConfig.DARKSTONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.DARKSTONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.DARKSTONE_MAX_HEIGHT.get()))));
        PlacementUtils.register(context, ARCANE_GILDED_DARKSTONE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.ARCANE_GILDED_DARKSTONE), this.commonOrePlacement(WorldGenConfig.ARCANE_GILDED_DARKSTONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.ARCANE_GILDED_DARKSTONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.ARCANE_GILDED_DARKSTONE_MAX_HEIGHT.get()))));
        PlacementUtils.register(context, STELLA_ARCANUM, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.STELLA_ARCANUM), this.commonOrePlacement(WorldGenConfig.STELLA_ARCANUM_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.STELLA_ARCANUM_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.STELLA_ARCANUM_MAX_HEIGHT.get()))));
        PlacementUtils.register(context, XPETRIFIED_ORE, configuredFeatureRegistry.getOrThrow(ModConfiguredFeatures.XPETRIFIED_ORE), this.commonOrePlacement(WorldGenConfig.XPETRIFIED_ORE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.XPETRIFIED_ORE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.XPETRIFIED_ORE_MAX_HEIGHT.get()))));
    }
}
