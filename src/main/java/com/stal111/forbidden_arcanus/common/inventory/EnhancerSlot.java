package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
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
 * @since 2021-06-30
 */
public class EnhancerSlot extends SlotItemHandler {

    private boolean locked = true;
    private final int requiredLevel;

    public EnhancerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, HephaestusForgeLevel.ONE);
    }

    public EnhancerSlot(IItemHandler inventory, int index, int xPosition, int yPosition, HephaestusForgeLevel requiredLevel) {
        super(inventory, index, xPosition, yPosition);
        this.requiredLevel = requiredLevel.getAsInt();
    }

    public EnhancerSlot updateLocked(HephaestusForgeLevel level) {
        this.locked = level.getAsInt() < this.requiredLevel;

        return this;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return !this.isLocked();
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
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Nullable
    public String getAdditionalData() {
        return this.requiredLevel == 1 ? null : String.valueOf(this.requiredLevel);
    }
}
