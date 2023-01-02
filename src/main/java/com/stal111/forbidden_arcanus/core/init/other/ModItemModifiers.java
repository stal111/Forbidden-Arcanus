package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Mod Item Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModItemModifiers
 *
 * @author stal111
 * @since 2021-11-24
 */
public class ModItemModifiers {

    public static final DeferredRegister<ItemModifier> MODIFIERS = DeferredRegister.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item_modifiers"), ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<EternalModifier> ETERNAL = MODIFIERS.register("eternal", () -> new EternalModifier(ItemStack::isDamageableItem, ModTags.Items.ETERNAL_INCOMPATIBLE, ModTags.Enchantments.ETERNAL_INCOMPATIBLE, Pair.of(FastColor.ARGB32.color(255, 170, 181, 159), FastColor.ARGB32.color(255, 49, 57, 56))));
    public static final RegistryObject<ItemModifier> FIERY = MODIFIERS.register("fiery", () -> new ItemModifier(stack -> stack.canPerformAction(ToolActions.PICKAXE_DIG) || stack.canPerformAction(ToolActions.AXE_DIG) || stack.canPerformAction(ToolActions.SHOVEL_DIG) || stack.canPerformAction(ToolActions.HOE_DIG) || stack.canPerformAction(ToolActions.PICKAXE_DIG), ModTags.Items.FIERY_INCOMPATIBLE, ModTags.Enchantments.FIERY_INCOMPATIBLE, Pair.of(FastColor.ARGB32.color(255, 255, 143, 0), FastColor.ARGB32.color(255, 88, 6, 6))));
    public static final RegistryObject<ItemModifier> MAGNETIZED = MODIFIERS.register("magnetized", () -> new ItemModifier(stack -> {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getSlot() == EquipmentSlot.FEET;
        }
        return false;
    }, ModTags.Items.MAGNETIZED_INCOMPATIBLE, ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE, Pair.of(FastColor.ARGB32.color(255, 200, 201, 215), FastColor.ARGB32.color(255, 87, 105, 99))));
}
