package com.stal111.forbidden_arcanus.common.inventory.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.inventory.InputType;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * Hephaestus Forge Input
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInput
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-07
 */
public interface HephaestusForgeInput {

    boolean canInput(InputType inputType, ItemStack stack);
    int getInputValue(InputType inputType, ItemStack stack, Random random);
    void finishInput(InputType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);
}
