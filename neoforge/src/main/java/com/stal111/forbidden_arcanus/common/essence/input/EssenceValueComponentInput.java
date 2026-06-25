package com.stal111.forbidden_arcanus.common.essence.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 06.05.2024
 */
public class EssenceValueComponentInput implements EssenceInput {

    @Override
    public int getAmount(ItemStack stack, EssenceType type) {
        return EssenceHelper.getEssenceAmount(stack, type);
    }

    @Override
    public ItemStack finishInput(ItemStack stack, int inputAmount) {
        stack.shrink(1);

        return stack;
    }
}
