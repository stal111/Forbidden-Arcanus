package com.stal111.forbidden_arcanus.common.container;

import com.stal111.forbidden_arcanus.common.container.input.HephaestusForgeInputs;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Input Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.InputSlot
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-02
 */
public class InputSlot extends Slot {

    private final InputType inputType;

    public InputSlot(Container inventory, int index, int xPosition, int yPosition, InputType inputType) {
        super(inventory, index, xPosition, yPosition);
        this.inputType = inputType;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return HephaestusForgeInputs.getInputs().stream().anyMatch(input -> input.canInput(inputType, stack));
    }

    public InputType getInputType() {
        return inputType;
    }
}
