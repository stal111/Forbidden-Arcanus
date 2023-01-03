package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Main Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.MainSlot
 *
 * @author stal111
 * @since 2021-07-10
 */
public class MainSlot extends SlotItemHandler {

    public MainSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
