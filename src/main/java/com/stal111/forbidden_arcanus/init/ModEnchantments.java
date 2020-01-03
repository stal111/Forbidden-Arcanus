package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.enchantment.PermafrostEnchantment;
import com.stal111.forbidden_arcanus.item.EdelwoodBucketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public enum ModEnchantments {
    PERMAFROST(new PermafrostEnchantment(Enchantment.Rarity.COMMON, ModEnchantmentType.type, EquipmentSlotType.MAINHAND));

    private final Enchantment enchantment;

    ModEnchantments(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public Enchantment getEnchantment() {
        if (enchantment.getRegistryName() == null) {
            enchantment.setRegistryName(new ResourceLocation(Main.MOD_ID, getName()));
        }
        return enchantment;
    }

    public static class ModEnchantmentType {
        public static EnchantmentType type = EnchantmentType.create("EDELWOOD_BUCKET", item -> item instanceof EdelwoodBucketItem);
    }
}
