package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.item.ItemStack;

/**
 * Ritual Starter Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.RitualStarterItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-09-18
 */
public interface RitualStarterItem {
    int getRitualUses();
    int getRemainingUses(ItemStack stack);
    void setRemainingUses(ItemStack stack, int remainingUses);
}
