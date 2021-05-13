package com.stal111.forbidden_arcanus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModEnchantment extends Enchantment {

    public int maxLevel = 1;
    public int minEnchantability = 0;
    public int maxEnchantability = 0;
    public boolean isTreasure = false;
    public boolean canBeVillagerTrade = true;
    public boolean canGenerateInLoot = true;
    public Predicate<Enchantment> canApplyTogether = enchantment -> true;

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
    protected boolean canApplyTogether(@Nonnull Enchantment enchantment) {
        return canApplyTogether.test(enchantment) && super.canApplyTogether(enchantment);
    }

    public boolean func_230309_h_() {
        return canBeVillagerTrade;
    }

    @Override
    public boolean canGenerateInLoot() {
        return canGenerateInLoot;
    }
}
