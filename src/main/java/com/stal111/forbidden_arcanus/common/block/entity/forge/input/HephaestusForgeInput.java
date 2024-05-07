package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2023-05-24
 */
public interface HephaestusForgeInput {

    boolean canInput(EssenceType type, ItemStack stack);

    EssenceData getInputValue(ItemStack stack, RandomSource random);

    default EssenceData getMaxInputValue(ItemStack stack, RandomSource random) {
        return this.getInputValue(stack, random);
    }

    ItemStack finishInput(ItemStack stack, int inputValue);
}
