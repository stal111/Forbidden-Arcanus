package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

public class EssenceSources {

    public static final MappedRegistryHelper<EssenceSource> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ESSENCE_SOURCE);

    public static final Holder<EssenceSource> FROM_VALUE = HELPER.register("from_value", EssenceValueSource::new);
    public static final Holder<EssenceSource> FROM_STORAGE = HELPER.register("from_storage", EssenceStorageSource::new);
}
