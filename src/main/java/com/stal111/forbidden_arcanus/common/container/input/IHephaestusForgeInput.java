package com.stal111.forbidden_arcanus.common.container.input;

import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * Hephaestus Forge Input
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.input.IInput
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-07
 */
public interface IHephaestusForgeInput {

    boolean canInput(InputType inputType, ItemStack stack);
    int getInputValue(InputType inputType, ItemStack stack, Random random);
    void finishInput(InputType inputType, ItemStack stack, HephaestusForgeTileEntity tileEntity, int slot, int inputValue);
}
