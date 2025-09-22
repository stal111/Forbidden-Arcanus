package com.stal111.forbidden_arcanus.common.world.placement;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInTreePlacements {

    public static final ResourceKeyHelper<PlacedFeature> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> EDELWOOD_TREES = HELPER.createKey("edelwood_trees");
    public static final ResourceKey<PlacedFeature> AURUM_TREES = HELPER.createKey("aurum_trees");
}
