package com.stal111.forbidden_arcanus.common.inventory.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.inventory.InputType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * Hephaestus Forge Input
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInput
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-07-07
 */
public interface HephaestusForgeInput {

    boolean canInput(InputType inputType, ItemStack stack);
    int getInputValue(InputType inputType, ItemStack stack, RandomSource random);
    void finishInput(InputType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);
}
