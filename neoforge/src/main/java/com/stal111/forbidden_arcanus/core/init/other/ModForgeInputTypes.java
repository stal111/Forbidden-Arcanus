package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceStorageComponentInput;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceValueComponentInput;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceInput;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-05-24
 */
public class ModForgeInputTypes implements RegistryClass {

    public static final MappedRegistryHelper<EssenceInput> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ESSENCE_INPUT);

    public static final Holder<EssenceInput> ESSENCE_VALUE_COMPONENT = HELPER.register("essence_value_component", EssenceValueComponentInput::new);
    public static final Holder<EssenceInput> ESSENCE_STORAGE_COMPONENT = HELPER.register("essence_storage_component", EssenceStorageComponentInput::new);

}
