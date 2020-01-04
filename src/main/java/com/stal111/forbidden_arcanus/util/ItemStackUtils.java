package com.stal111.forbidden_arcanus.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemStackUtils {

    public static void shrinkStack(PlayerEntity player, ItemStack stack) {
        shrinkStack(player, stack, 1);
    }

    public static void shrinkStack(PlayerEntity player, ItemStack stack, int count) {
        if (!player.abilities.isCreativeMode) {
            stack.shrink(count);
        }
    }

    public static ItemStack transferEnchantments(ItemStack oldStack, ItemStack newStack) {
        if (!EnchantmentHelper.getEnchantments(oldStack).isEmpty()) {
            EnchantmentHelper.getEnchantments(oldStack).forEach(newStack::addEnchantment);
        }
        return newStack;
    }
}
