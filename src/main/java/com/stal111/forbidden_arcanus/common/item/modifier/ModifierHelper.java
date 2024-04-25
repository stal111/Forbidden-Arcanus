package com.stal111.forbidden_arcanus.common.item.modifier;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Modifier Helper <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-24
 */
public class ModifierHelper {

    @Nullable
    public static ItemModifier getModifier(ItemStack stack) {
        return null;

        //TODO
//        if (stack.isEmpty() || !stack.hasTag()) {
//            return null;
//        }
//
//        return FARegistries.ITEM_MODIFIER_REGISTRY.get(new ResourceLocation(Objects.requireNonNull(stack.getTag()).getString("Modifier")));
    }

    public static void setModifier(ItemStack stack, ItemModifier modifier) {
       // stack.getOrCreateTag().putString("Modifier", modifier.getRegistryName().toString());

        modifier.onApplied(stack);
    }
}
