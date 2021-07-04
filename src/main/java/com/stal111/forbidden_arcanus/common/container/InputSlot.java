package com.stal111.forbidden_arcanus.common.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

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

    public InputSlot(IInventory inventory, int index, int xPosition, int yPosition, InputType inputType) {
        super(inventory, index, xPosition, yPosition);
        this.inputType = inputType;
    }



    public InputType getInputType() {
        return inputType;
    }
}
