package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

/**
 * @author stal111
 * @since 2021-06-30
 */
public class EnhancerSlot extends SlotItemHandler {

    private final BooleanSupplier locked;
    private final Tooltip lockedTooltip;

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, () -> false, null);
    }

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, BooleanSupplier locked, Tooltip lockedTooltip) {
        super(itemHandler, index, xPosition, yPosition);
        this.locked = locked;
        this.lockedTooltip = lockedTooltip;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
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
    public Tooltip getLockedTooltip() {
        return this.lockedTooltip;
    }
}
