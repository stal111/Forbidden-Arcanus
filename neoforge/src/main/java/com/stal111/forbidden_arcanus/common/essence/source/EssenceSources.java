package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

public class EssenceSources {

    public static final MappedRegistryHelper<EssenceSource> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ESSENCE_SOURCE);

    public static final Holder<EssenceSource> FROM_VALUE_COMPONENT = HELPER.register("from_value_component", () -> new DataComponentEssenceSource(ModDataComponents.ESSENCE_VALUE.get()));
    public static final Holder<EssenceSource> FROM_STORAGE_COMPONENT = HELPER.register("from_storage_component", () -> new DataComponentEssenceSource(ModDataComponents.ESSENCE_STORAGE.get()));
}
