package com.stal111.forbidden_arcanus.common.container;

import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeLevel;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Enhancer Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.EnhancerSlot
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-30
 */
public class EnhancerSlot extends Slot {

    private boolean unlocked = true;
    private final HephaestusForgeLevel requiredLevel;

    public EnhancerSlot(IInventory inventory, int index, int xPosition, int yPosition, HephaestusForgeLevel requiredLevel) {
        super(inventory, index, xPosition, yPosition);
        this.requiredLevel = requiredLevel;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return this.unlocked;
    }

    @Override
    public boolean isEnabled() {
        return this.unlocked;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public HephaestusForgeLevel getRequiredLevel() {
        return requiredLevel;
    }
}
