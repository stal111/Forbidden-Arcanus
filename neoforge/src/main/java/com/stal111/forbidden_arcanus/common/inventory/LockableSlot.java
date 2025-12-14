package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * @author stal111
 * @since 2021-06-30
 */
public class LockableSlot extends ResourceHandlerSlot {

    public static final Identifier LOCKED_SLOT_SPRITE = ForbiddenArcanus.identifier("container/locked_slot");

    private final BooleanSupplier locked;
    private final Component lockedDescription;

    public LockableSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition) {
        this(itemHandler, index, xPosition, yPosition, () -> false, Component.empty());
    }

    public LockableSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition, BooleanSupplier locked, Component lockedDescription) {
        super(itemHandler, itemHandler::set, index, xPosition, yPosition);
        this.locked = locked;
        this.lockedDescription = lockedDescription;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return !this.isLocked() && super.mayPlace(stack);
    }

    @Override
    public boolean isActive() {
        return !this.isLocked();
    }

    public boolean isLocked() {
        return this.locked.getAsBoolean();
    }

    public Component getLockedDescription() {
        return this.lockedDescription;
    }
}
