package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltInEnhancers {

    public static final ResourceKeyHelper<EnhancerDefinition> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.ENHANCER_DEFINITION);

    public static final ResourceKey<EnhancerDefinition> ARTISAN_RELIC = HELPER.createKey("artisan_relic");
    public static final ResourceKey<EnhancerDefinition> CRESCENT_MOON = HELPER.createKey("crescent_moon");
    public static final ResourceKey<EnhancerDefinition> CRIMSON_STONE = HELPER.createKey("crimson_stone");
    public static final ResourceKey<EnhancerDefinition> SOUL_CRIMSON_STONE = HELPER.createKey("soul_crimson_stone");
    public static final ResourceKey<EnhancerDefinition> ELEMENTARIUM = HELPER.createKey("elementarium");
    public static final ResourceKey<EnhancerDefinition> DIVINE_PACT = HELPER.createKey("divine_pact");
    public static final ResourceKey<EnhancerDefinition> MALEDICTUS_PACT = HELPER.createKey("maledictus_pact");
}
