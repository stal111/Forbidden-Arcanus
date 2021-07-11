package com.stal111.forbidden_arcanus.common.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

/**
 * Main Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.MainSlot
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class MainSlot extends Slot {

    public MainSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
