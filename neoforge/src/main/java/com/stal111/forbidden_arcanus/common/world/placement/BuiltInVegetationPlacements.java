package com.stal111.forbidden_arcanus.common.world.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInVegetationPlacements {

    public static final ResourceKeyHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> YELLOW_ORCHID = HELPER.createKey("yellow_orchid");
}
