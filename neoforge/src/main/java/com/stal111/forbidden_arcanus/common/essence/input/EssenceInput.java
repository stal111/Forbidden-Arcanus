package com.stal111.forbidden_arcanus.common.essence.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2023-05-24
 */
public interface EssenceInput {

    default boolean isValidInput(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type) > 0;
    }

    int getAmount(ItemStack stack, EssenceType type);

    default int getMaxAmount(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type);
    }

    ItemStack finishInput(ItemStack stack, int inputAmount);
}
