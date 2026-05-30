package com.stal111.forbidden_arcanus.common.world.feature;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInFeatures {

    public static final ResourceKeyHelper<ConfiguredFeature<?, ?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCANE_CRYSTAL_ORE = HELPER.createKey("ore_arcane_crystal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUNIC_STONE = HELPER.createKey("ore_rune");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUNIC_STONE_LOWER = HELPER.createKey("ore_rune_lower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARKSTONE = HELPER.createKey("ore_darkstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STELLA_ARCANUM = HELPER.createKey("ore_stella_arcanum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> METEORITE = HELPER.createKey("meteorite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM = HELPER.createKey("aurum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_ORCHID = HELPER.createKey("flower_yellow_orchid");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELWOOD = HELPER.createKey("edelwood");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_FUNGYSS_0 = HELPER.createKey("big_fungyss_0");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_FUNGYSS_1 = HELPER.createKey("big_fungyss_1");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGYSS_0 = HELPER.createKey("mega_fungyss_0");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGYSS_1 = HELPER.createKey("mega_fungyss_1");
}
