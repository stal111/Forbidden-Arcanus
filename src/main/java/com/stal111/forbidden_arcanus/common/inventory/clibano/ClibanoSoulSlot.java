package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Clibano Soul Slot <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoSoulSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-26
 */
public class ClibanoSoulSlot extends Slot {

    private static final List<? extends Item> VALID_ITEMS = List.of(ModItems.SOUL.get(), ModItems.DARK_SOUL.get());

    public ClibanoSoulSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    public boolean mayPlace(@Nonnull ItemStack stack) {
        return VALID_ITEMS.contains(stack.getItem());
    }
}
