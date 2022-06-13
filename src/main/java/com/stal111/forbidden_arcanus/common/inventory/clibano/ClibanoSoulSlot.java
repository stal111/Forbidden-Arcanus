package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Clibano Soul Slot <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoSoulSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-26
 */
public class ClibanoSoulSlot extends Slot {

    public ClibanoSoulSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    public boolean mayPlace(@Nonnull ItemStack stack) {
        return ClibanoMainBlockEntity.ITEM_TO_FIRE_TYPE.apply(stack).isPresent();
    }
}
