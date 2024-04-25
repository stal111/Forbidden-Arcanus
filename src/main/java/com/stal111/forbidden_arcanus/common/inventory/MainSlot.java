package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraft.world.inventory.CrafterMenu;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

/**
 * Main Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.MainSlot
 *
 * @author stal111
 * @since 2021-07-10
 */
public class MainSlot extends SlotItemHandler {

    private final HephaestusForgeMenu menu;

    public MainSlot(IItemHandler itemHandler, int index, int x, int y, HephaestusForgeMenu menu) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.menu.broadcastChanges();
    }
}
