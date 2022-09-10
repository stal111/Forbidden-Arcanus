package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enhancer Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.EnhancerSlot
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2021-06-30
 */
public class EnhancerSlot extends SlotItemHandler {

    private boolean unlocked = true;
    @Nullable
    private final String additionalData;

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, null);
    }

    public EnhancerSlot(IItemHandler inventory, int index, int xPosition, int yPosition, @Nullable String additionalData) {
        super(inventory, index, xPosition, yPosition);
        this.additionalData = additionalData;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return this.unlocked;
    }

    @Override
    public boolean isActive() {
        return this.unlocked;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Nullable
    public String getAdditionalData() {
        return this.additionalData;
    }
}
