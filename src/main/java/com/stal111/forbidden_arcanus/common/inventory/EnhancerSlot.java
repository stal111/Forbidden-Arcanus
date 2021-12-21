package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Enhancer Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.EnhancerSlot
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-30
 */
public class EnhancerSlot extends Slot {

    private boolean unlocked = true;
    private final HephaestusForgeLevel requiredLevel;

    public EnhancerSlot(Container inventory, int index, int xPosition, int yPosition, HephaestusForgeLevel requiredLevel) {
        super(inventory, index, xPosition, yPosition);
        this.requiredLevel = requiredLevel;
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
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public HephaestusForgeLevel getRequiredLevel() {
        return requiredLevel;
    }
}
