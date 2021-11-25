package com.stal111.forbidden_arcanus.common.item.modifier;

import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import net.minecraft.world.item.ItemStack;

/**
 * Eternal Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.EternalModifier
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-25
 */
public class EternalModifier extends ItemModifier {

    @Override
    public void onApplied(ItemStack stack) {
        if (EnchantmentConfig.INDESTRUCTIBLE_REPAIR_ITEM.get()) {
            stack.getOrCreateTag().putBoolean("Repair", true);
        }
    }
}
