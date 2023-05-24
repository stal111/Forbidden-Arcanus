package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2023-05-24
 */
public interface HephaestusForgeInput {
    boolean canInput(EssenceType inputType, ItemStack stack);
    int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random);
    void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);

    HephaestusForgeInputType<?> getType();
}
