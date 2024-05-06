package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.*;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-05-24
 */
public class ModForgeInputTypes implements RegistryClass {

    public static final MappedRegistryHelper<HephaestusForgeInput> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.FORGE_INPUT);

    public static final Holder<HephaestusForgeInput> SIMPLE_ITEM = HELPER.register("essence_data", EssenceDataInput::new);
    public static final Holder<HephaestusForgeInput> EXTRACT_ENCHANTMENTS = HELPER.register("extract_enchantments", ExtractEnchantmentsInput::new);
    public static final Holder<HephaestusForgeInput> ESSENCE_CONTAINER = HELPER.register("essence_container", EssenceContainerInput::new);

}
