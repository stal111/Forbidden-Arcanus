package com.stal111.forbidden_arcanus.enchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ModEnchantment extends Enchantment {

    public int maxLevel = 1;
    public int minEnchantability = 0;
    public int maxEnchantability = 0;
    public boolean isTreasure = false;
    public boolean canBeVillagerTrade = true;
    public boolean canGenerateInLoot = true;
    public Predicate<Enchantment> canApplyTogether = enchantment -> true;

    public ModEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot[] equipmentSlotTypes) {
        super(rarity, enchantmentType, equipmentSlotTypes);
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public boolean isTreasureOnly() {
        return this.isTreasure;
    }

    @Override
    public int getMinCost(int minEnchantability) {
        return this.minEnchantability;
    }

    @Override
    public int getMaxCost(int maxEnchantability) {
        return this.maxEnchantability;
    }

    @Override
    protected boolean checkCompatibility(@Nonnull Enchantment enchantment) {
        return canApplyTogether.test(enchantment) && super.checkCompatibility(enchantment);
    }

    public boolean isTradeable() {
        return canBeVillagerTrade;
    }

    @Override
    public boolean isDiscoverable() {
        return canGenerateInLoot;
    }
}
