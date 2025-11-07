package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2023-05-24
 */
public interface HephaestusForgeInput {

    boolean canInput(EssenceType type, ItemStack stack);

    EssenceValue getInputValue(ItemStack stack);

    default EssenceValue getMaxInputValue(ItemStack stack) {
        return this.getInputValue(stack);
    }

    ItemStack finishInput(ItemStack stack, int inputValue);
}
