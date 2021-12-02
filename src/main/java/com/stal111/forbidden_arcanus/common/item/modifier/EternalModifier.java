package com.stal111.forbidden_arcanus.common.item.modifier;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Predicate;

/**
 * Eternal Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-25
 */
public class EternalModifier extends ItemModifier {

    public EternalModifier(Predicate<ItemStack> predicate, Tag.Named<Item> blacklistedItems, Tag.Named<Enchantment> blacklistedEnchantments, Pair<Integer, Integer> tooltipColors) {
        super(predicate, blacklistedItems, blacklistedEnchantments, tooltipColors);
    }

    @Override
    public void onApplied(ItemStack stack) {
        if (EnchantmentConfig.INDESTRUCTIBLE_REPAIR_ITEM.get()) {
            stack.getOrCreateTag().putBoolean("Repair", true);
        }
    }
}
