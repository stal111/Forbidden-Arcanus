package com.stal111.forbidden_arcanus.common.world.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInOrePlacements {

    public static final ResourceKeyHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> ARCANE_CRYSTAL_ORE = HELPER.createKey("arcane_crystal_ore");
    public static final ResourceKey<PlacedFeature> RUNIC_STONE = HELPER.createKey("runic_stone");
    public static final ResourceKey<PlacedFeature> RUNIC_STONE_LOWER = HELPER.createKey("runic_stone_lower");
    public static final ResourceKey<PlacedFeature> DARKSTONE = HELPER.createKey("darkstone");
    public static final ResourceKey<PlacedFeature> STELLA_ARCANUM = HELPER.createKey("stella_arcanum");
}
