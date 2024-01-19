package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Clibano Soul Slot <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoSoulSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-26
 */
public class ClibanoSoulSlot extends SlotItemHandler {

    public ClibanoSoulSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    public boolean mayPlace(@Nonnull ItemStack stack) {
        return ClibanoFireType.fromItem(stack) != ClibanoFireType.FIRE;
    }
}
