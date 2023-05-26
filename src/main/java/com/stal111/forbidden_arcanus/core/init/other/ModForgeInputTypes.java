package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInputType;
import com.stal111.forbidden_arcanus.common.inventory.input.BloodInput;
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

    public static final MappedRegistryHelper<HephaestusForgeInputType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.FORGE_INPUT_TYPE);

    public static final RegistryObject<HephaestusForgeInputType<ItemInput>> ITEM = HELPER.register("item", () -> new HephaestusForgeInputType<>(ItemInput.CODEC));
    public static final RegistryObject<HephaestusForgeInputType<EnchantmentInput>> ENCHANTMENT = HELPER.register("enchantment", () -> new HephaestusForgeInputType<>(EnchantmentInput.CODEC));
    public static final RegistryObject<HephaestusForgeInputType<BloodInput>> BLOOD = HELPER.register("blood", () -> new HephaestusForgeInputType<>(BloodInput.CODEC));

}
