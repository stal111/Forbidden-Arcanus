package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;

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

    public static boolean registryContainsItem(String name) {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(Main.MOD_ID, name));
    }

    public static boolean hasStackEnchantment(ItemStack stack, Enchantment enchantment) {
        return hasStackEnchantment(stack, Collections.singletonList(enchantment));
    }

    public static boolean hasStackEnchantment(ItemStack stack, List<Enchantment> enchantments) {
        for (Enchantment itemEnchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            for (Enchantment enchantment : enchantments) {
                if (itemEnchantment == enchantment) {
                    return true;
                }
            }
        }

        return false;
    }
}
