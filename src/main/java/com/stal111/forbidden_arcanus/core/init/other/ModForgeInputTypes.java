package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.inventory.input.BloodTestTubeInput;
import com.stal111.forbidden_arcanus.common.inventory.input.EnchantmentInput;
import com.stal111.forbidden_arcanus.common.inventory.input.ItemInput;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-05-24
 */
public class ModForgeInputTypes implements RegistryClass {

    public static final MappedRegistryHelper<HephaestusForgeInput> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.FORGE_INPUT_TYPE);

    public static final RegistryObject<ItemInput> ITEM = HELPER.register("item", ItemInput::new);
    public static final RegistryObject<EnchantmentInput> ENCHANTMENT = HELPER.register("enchantment", EnchantmentInput::new);
    public static final RegistryObject<BloodTestTubeInput> BLOOD = HELPER.register("blood", BloodTestTubeInput::new);

}
