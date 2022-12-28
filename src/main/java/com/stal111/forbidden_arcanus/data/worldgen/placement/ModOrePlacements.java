package com.stal111.forbidden_arcanus.data.worldgen.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

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

    public static final RegistryObject<PlacedFeature> ARCANE_CRYSTAL_ORE = register("arcane_crystal_ore", ModConfiguredFeatures.ARCANE_CRYSTAL_ORE.getHolder().get(), commonOrePlacement(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get(), HeightRangePlacement.triangle(VerticalAnchor.absolute(WorldGenConfig.ARCANE_CRYSTAL_ORE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get()))));
    public static final RegistryObject<PlacedFeature> RUNIC_STONE = register("runic_stone", ModConfiguredFeatures.RUNIC_STONE.getHolder().get(), commonOrePlacement(WorldGenConfig.RUNIC_STONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.RUNIC_STONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.RUNIC_STONE_MAX_HEIGHT.get()))));
    public static final RegistryObject<PlacedFeature> DARKSTONE = register("darkstone", ModConfiguredFeatures.DARKSTONE.getHolder().get(), commonOrePlacement(WorldGenConfig.DARKSTONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.DARKSTONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.DARKSTONE_MAX_HEIGHT.get()))));
    public static final RegistryObject<PlacedFeature> ARCANE_GILDED_DARKSTONE = register("arcane_gilded_darkstone", ModConfiguredFeatures.ARCANE_GILDED_DARKSTONE.getHolder().get(), commonOrePlacement(WorldGenConfig.ARCANE_GILDED_DARKSTONE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.ARCANE_GILDED_DARKSTONE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.ARCANE_GILDED_DARKSTONE_MAX_HEIGHT.get()))));
    public static final RegistryObject<PlacedFeature> STELLA_ARCANUM = register("stella_arcanum", ModConfiguredFeatures.STELLA_ARCANUM.getHolder().get(), commonOrePlacement(WorldGenConfig.STELLA_ARCANUM_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.STELLA_ARCANUM_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.STELLA_ARCANUM_MAX_HEIGHT.get()))));
    public static final RegistryObject<PlacedFeature> XPETRIFIED_ORE = register("xpetrified_ore", ModConfiguredFeatures.XPETRIFIED_ORE.getHolder().get(), commonOrePlacement(WorldGenConfig.XPETRIFIED_ORE_COUNT.get(), HeightRangePlacement.uniform(VerticalAnchor.absolute(WorldGenConfig.XPETRIFIED_ORE_MIN_HEIGHT.get()), VerticalAnchor.absolute(WorldGenConfig.XPETRIFIED_ORE_MAX_HEIGHT.get()))));

    public ModOrePlacements(DataProviderInfo info, BootstapContext<PlacedFeature> context) {
        super(info, context);
    }

    private static RegistryObject<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return HELPER.register(name, () -> new PlacedFeature(Holder.hackyErase(feature), modifiers));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier placementModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), placementModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(count), placementModifier);
    }

    @Override
    public void bootstrap(BootstapContext<PlacedFeature> context) {

    }
}
