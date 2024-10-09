package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @author stal111
 * @since 19.02.2024
 */
public enum ClibanoInputSlot {
    FIRST(ClibanoMenu.INPUT_SLOTS.getFirst()),
    SECOND(ClibanoMenu.INPUT_SLOTS.getSecond()),
    BOTH(ClibanoMenu.INPUT_SLOTS.getFirst(), ClibanoMenu.INPUT_SLOTS.getSecond());

    private final int[] index;
    private final int[] slots;

    ClibanoInputSlot(int... slots) {
        this.index = Arrays.stream(slots).map(slot -> slot - 3).toArray();
        this.slots = slots;
    }

    public void apply(IntConsumer consumer) {
        for (int slot : this.slots) {
            consumer.accept(slot);
        }
    }

    public boolean apply(Int2BooleanFunction function) {
        for (int slot : this.slots) {
            if (!function.apply(slot)) {
                return false;
            }
        }
        return true;
    }

    public int[] getIndex() {
        return this.index;
    }
}
