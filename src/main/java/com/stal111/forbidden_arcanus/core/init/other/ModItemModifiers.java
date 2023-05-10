package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * Mod Item Modifiers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModItemModifiers
 *
 * @author stal111
 * @since 2021-11-24
 */
public class ModItemModifiers implements RegistryClass {

    public static final MappedRegistryHelper<ItemModifier> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.ITEM_MODIFIER);

    public static final RegistryObject<EternalModifier> ETERNAL = HELPER.register("eternal", () -> new EternalModifier(ItemStack::isDamageableItem, ModTags.Items.ETERNAL_INCOMPATIBLE, ModTags.Enchantments.ETERNAL_INCOMPATIBLE, FastColor.ARGB32.color(255, 170, 181, 159), FastColor.ARGB32.color(255, 49, 57, 56)));
    public static final RegistryObject<ItemModifier> FIERY = HELPER.register("fiery", () -> new ItemModifier(stack -> stack.canPerformAction(ToolActions.PICKAXE_DIG) || stack.canPerformAction(ToolActions.AXE_DIG) || stack.canPerformAction(ToolActions.SHOVEL_DIG) || stack.canPerformAction(ToolActions.HOE_DIG) || stack.canPerformAction(ToolActions.PICKAXE_DIG), ModTags.Items.FIERY_INCOMPATIBLE, ModTags.Enchantments.FIERY_INCOMPATIBLE, FastColor.ARGB32.color(255, 255, 143, 0), FastColor.ARGB32.color(255, 88, 6, 6)));
    public static final RegistryObject<ItemModifier> MAGNETIZED = HELPER.register("magnetized", () -> new ItemModifier(stack -> {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getType() == ArmorItem.Type.BOOTS;
        }
        return false;
    }, ModTags.Items.MAGNETIZED_INCOMPATIBLE, ModTags.Enchantments.MAGNETIZED_INCOMPATIBLE, FastColor.ARGB32.color(255, 200, 201, 215), FastColor.ARGB32.color(255, 87, 105, 99)));
    public static final RegistryObject<ItemModifier> DEMOLISHING = HELPER.register("demolishing", () -> new ItemModifier(stack -> stack.canPerformAction(ToolActions.PICKAXE_DIG) || stack.canPerformAction(ToolActions.AXE_DIG) || stack.canPerformAction(ToolActions.SHOVEL_DIG) || stack.canPerformAction(ToolActions.HOE_DIG) || stack.canPerformAction(ToolActions.PICKAXE_DIG), ModTags.Items.DEMOLISHING_INCOMPATIBLE, ModTags.Enchantments.DEMOLISHING_INCOMPATIBLE, FastColor.ARGB32.color(255, 111, 84, 80), FastColor.ARGB32.color(255, 78, 58, 39)));
    public static final RegistryObject<ItemModifier> AQUATIC = HELPER.register("aquatic", () -> new ItemModifier(stack -> LivingEntity.getEquipmentSlotForItem(stack) == EquipmentSlot.HEAD, ModTags.Items.AQUATIC_INCOMPATIBLE, ModTags.Enchantments.AQUATIC_INCOMPATIBLE, FastColor.ARGB32.color(255, 90, 130, 243), FastColor.ARGB32.color(255, 35, 79, 204)));

}
