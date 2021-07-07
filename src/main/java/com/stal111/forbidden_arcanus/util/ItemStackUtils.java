package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, name));
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

    public static ItemStack removeEnchantments(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.removeChildTag("Enchantments");
        copy.removeChildTag("StoredEnchantments");

        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((entry) -> entry.getKey().isCurse()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, copy);
        copy.setRepairCost(0);

        if (copy.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            copy = new ItemStack(Items.BOOK);
            if (stack.hasDisplayName()) {
                copy.setDisplayName(stack.getDisplayName());
            }
        }

        for(int i = 0; i < map.size(); ++i) {
            copy.setRepairCost(RepairContainer.getNewRepairCost(copy.getRepairCost()));
        }

        return copy;
    }
}
