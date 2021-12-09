package com.stal111.forbidden_arcanus.common.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Capacity Bucket <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.CapacityBucket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-03
 */
public interface CapacityBucket {
    int getCapacity();
    ItemStack getEmptyBucket();

    default int getFullness(ItemStack stack) {
        if (stack.hasTag() && Objects.requireNonNull(stack.getTag()).contains("Fullness")) {
            return stack.getOrCreateTag().getInt("Fullness");
        }
        if (!this.isValidBucket(stack)) {
            return 0;
        }
        if (stack.getOrCreateTag().getInt("Fullness") == 0) {
            this.setFullness(stack, 1);
        }
        return stack.getOrCreateTag().getInt("Fullness");
    }

    default ItemStack setFullness(ItemStack stack, int fullness) {
        if (this.isValidBucket(stack)) {
            stack.getOrCreateTag().putInt("Fullness", fullness);
        }

        return stack;
    }

    default boolean isFull(ItemStack stack) {
        return this.getFullness(stack) >= this.getCapacity();
    }

    default Pair<Boolean, ItemStack> tryFill(ItemStack stack) {
        if (this.isFull(stack)) {
            return Pair.of(false, stack);
        }
        this.setFullness(stack, this.getFullness(stack) + 1);

        return Pair.of(true, stack);
    }

    default ItemStack tryDrain(ItemStack stack) {
        if (this.getFullness(stack) - 1 <= 0) {
            return this.getEmptyBucket();
        }
        return this.setFullness(stack, this.getFullness(stack) - 1);
    }

    private boolean isValidBucket(ItemStack stack) {
        return stack.getItem() instanceof CapacityBucket && this.getCapacity() != 0;
    }

    default ItemStack transferFullness(ItemStack oldStack, ItemStack newStack) {
        newStack.getOrCreateTag().putInt("Fullness", this.getFullness(oldStack));
        return newStack;
    }
}
