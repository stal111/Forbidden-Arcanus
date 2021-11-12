package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public interface ICapacityBucket {

    int getCapacity();

    static int getFullness(ItemStack stack) {
        if (!(stack.getItem() instanceof ICapacityBucket) || stack.getItem() != ModItems.EDELWOOD_BUCKET.get()) {
            CompoundTag compoundNBT = stack.getOrCreateTagElement("EdelwoodBucket");
            if (compoundNBT.getInt("Fullness") == 0) {
                setFullness(stack, 1);
            }
            return compoundNBT.getInt("Fullness");
        }
        return 0;
    }

    static ItemStack setFullness(ItemStack stack, int fullness) {
        if (!(stack.getItem() instanceof ICapacityBucket) || stack.getItem() != ModItems.EDELWOOD_BUCKET.get()) {
            CompoundTag compoundNBT = stack.getOrCreateTagElement("EdelwoodBucket");
            compoundNBT.putInt("Fullness", fullness);
        }
        return stack;
    }
}
