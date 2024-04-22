package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;

/**
 * Enhancer Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.EnhancerSlot
 *
 * @author stal111
 * @since 2021-06-30
 */
public class EnhancerSlot extends SlotItemHandler {

    private final BooleanSupplier locked;

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, () -> false);
    }

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, BooleanSupplier locked) {
        super(itemHandler, index, xPosition, yPosition);
        this.locked = locked;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return !this.isLocked() && super.mayPlace(stack);
    }

    @Override
    public boolean isActive() {
        return !this.isLocked();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public boolean isLocked() {
        return this.locked.getAsBoolean();
    }

    @Nullable
    public String getAdditionalData() {
        return "";
        //TODO
        //return this.requiredLevel == 1 ? null : String.valueOf(this.requiredLevel);
    }
}
