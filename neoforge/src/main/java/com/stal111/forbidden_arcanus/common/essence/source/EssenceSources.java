package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

public class EssenceSources {

    public static final MappedRegistryHelper<EssenceSource> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ESSENCE_SOURCE_TYPE);

    public static final Supplier<EssenceSource> FROM_VALUE = HELPER.register("from_value", () -> new EssenceSource(ModDataComponents.ESSENCE_VALUE.get()));
    public static final Supplier<EssenceSource> FROM_STORAGE = HELPER.register("from_storage", () -> new EssenceSource(ModDataComponents.ESSENCE_STORAGE.get()));
}
