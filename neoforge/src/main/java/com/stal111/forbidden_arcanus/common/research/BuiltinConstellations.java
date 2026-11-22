package com.stal111.forbidden_arcanus.common.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

public class BuiltinConstellations {

    public static final ResourceKeyHelper<Constellation> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.createKeyHelper(FARegistries.CONSTELLATION);

    public static final ResourceKey<Constellation> URSA_MINOR = HELPER.createKey("ursa_minor");

}
