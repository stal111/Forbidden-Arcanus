package com.stal111.forbidden_arcanus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.ArrayList;
import java.util.List;

public class ModEnchantment extends Enchantment {

    public int maxLevel = 1;
    public int minEnchantability = 0;
    public int maxEnchantability = 0;
    public boolean isTreasure = false;
    public List<Enchantment> blacklistedEnchantments = new ArrayList<>();

    public ModEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType[] equipmentSlotTypes) {
        super(rarity, enchantmentType, equipmentSlotTypes);
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return this.isTreasure;
    }

    @Override
    public int getMinEnchantability(int minEnchantability) {
        return this.minEnchantability;
    }

    @Override
    public int getMaxEnchantability(int maxEnchantability) {
        return this.maxEnchantability;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return !blacklistedEnchantments.contains(ench) && super.canApplyTogether(ench);
    }
}
