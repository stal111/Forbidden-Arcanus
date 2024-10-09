package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 06.05.2024
 */
public class EssenceDataInput implements HephaestusForgeInput {

    @Override
    public boolean canInput(EssenceType type, ItemStack stack) {
        return EssenceHelper.getEssenceData(stack).map(data -> data.type() == type).orElse(false);
    }

    @Override
    public EssenceData getInputValue(ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceData(stack).orElse(EssenceData.EMPTY);
    }

    @Override
    public ItemStack finishInput(ItemStack stack, int inputValue) {
        stack.shrink(1);

        return stack;
    }
}
