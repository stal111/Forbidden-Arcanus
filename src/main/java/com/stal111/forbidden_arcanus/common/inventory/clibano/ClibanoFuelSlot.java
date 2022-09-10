package com.stal111.forbidden_arcanus.common.inventory.clibano;

import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Clibano Fuel Slot <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoFuelSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-26
 */
public class ClibanoFuelSlot extends SlotItemHandler {

    private final ClibanoMenu menu;

    public ClibanoFuelSlot(ClibanoMenu menu, IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return this.menu.isFuel(stack) || FurnaceFuelSlot.isBucket(stack);
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return FurnaceFuelSlot.isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }
}
