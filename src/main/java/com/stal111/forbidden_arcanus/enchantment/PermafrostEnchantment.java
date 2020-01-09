package com.stal111.forbidden_arcanus.enchantment;

import com.stal111.forbidden_arcanus.item.EdelwoodBucketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class PermafrostEnchantment extends Enchantment {

    public PermafrostEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... equipmentSlot) {
        super(rarity, enchantmentType, equipmentSlot);
    }

    @Override
    public int getMinEnchantability(int p_77321_1_) {
        return 0;
    }

    @Override
    public int getMaxEnchantability(int p_223551_1_) {
        return super.getMinEnchantability(p_223551_1_) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
