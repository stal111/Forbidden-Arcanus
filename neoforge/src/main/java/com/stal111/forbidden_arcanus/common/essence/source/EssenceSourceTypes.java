package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

public class EssenceSourceTypes {

    public static final MappedRegistryHelper<EssenceSourceType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ESSENCE_SOURCE_TYPE);

    public static final Supplier<EssenceSourceType<?>> FROM_VALUE_COMPONENT = HELPER.register("from_value_component", () -> new EssenceSourceType<>(DataComponentEssenceSource.CODEC.fieldOf("component"), DataComponentEssenceSource.STREAM_CODEC));
    public static final Supplier<EssenceSourceType<?>> STATIC_VALUE = HELPER.register("static_value", () -> new EssenceSourceType<>(StaticValueEssenceSource.CODEC, StaticValueEssenceSource.STREAM_CODEC));
}
