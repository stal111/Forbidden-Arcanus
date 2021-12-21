package com.stal111.forbidden_arcanus.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * Permafrost Enchantment <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.enchantment.PermafrostEnchantment
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-12-21
 */
public class PermafrostEnchantment extends Enchantment {

    public PermafrostEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... equipmentSlots) {
        super(rarity, category, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) {
        return 12 + (level - 1) * 20;
    }

    @Override
    public int getMaxCost(int level) {
        return 50;
    }
}
