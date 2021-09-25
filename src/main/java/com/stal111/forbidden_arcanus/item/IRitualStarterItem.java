package com.stal111.forbidden_arcanus.item;

import net.minecraft.item.ItemStack;

/**
 * Ritual Starter Item Interface <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.IRitualStarterItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-09-18
 */
public interface IRitualStarterItem {
    int getRitualUses();
    int getRemainingUses(ItemStack stack);
    void setRemainingUses(ItemStack stack, int remainingUses);
}
