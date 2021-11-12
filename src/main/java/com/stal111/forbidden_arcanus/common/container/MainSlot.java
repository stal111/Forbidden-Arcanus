package com.stal111.forbidden_arcanus.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

/**
 * Main Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.MainSlot
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class MainSlot extends Slot {

    public MainSlot(Container inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
