package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;

import java.util.function.Consumer;

public class SingleSlotResourceHandler extends ItemStackResourceHandler {

    private static final Consumer<ItemStack> NO_OP_CONSUMER = _ -> {};

    private ItemStack stack = ItemStack.EMPTY;
    private final Consumer<ItemStack> onChanged;

    private final boolean singleCapacity;

    public SingleSlotResourceHandler(boolean singleCapacity) {
        this(singleCapacity, NO_OP_CONSUMER);
    }

    public SingleSlotResourceHandler(boolean singleCapacity, Consumer<ItemStack> onChanged) {
        this.onChanged = onChanged;
        this.singleCapacity = singleCapacity;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void setStack(ItemStack stack) {
        if (!ItemStack.matches(this.stack, stack)) {
            this.stack = stack;

            this.onChanged.accept(stack);
        }
    }

    @Override
    protected int getCapacity(ItemResource resource) {
        return this.singleCapacity ? 1 : super.getCapacity(resource);
    }

    @Override
    protected ItemStack createSnapshot() {
        ItemStack original = this.stack;
        this.stack = original.copy();
        return original;
    }

    @Override
    protected void revertToSnapshot(ItemStack snapshot) {
        this.stack = snapshot;
    }

    @Override
    protected void onRootCommit(ItemStack originalState) {
        if (!ItemStack.matches(originalState, this.stack)) {
            this.onChanged.accept(this.stack);
        }
    }

    public void serialize(String key, ValueOutput output) {
        if (!this.stack.isEmpty()) {
            output.store(key, ItemStack.CODEC, this.stack);
        }
    }

    public void deserialize(String key, ValueInput input) {
        this.stack = input.read(key, ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    public void set(int index, ItemResource resource, int amount) {
        TransferPreconditions.checkNonNegative(amount);
        if (resource.isEmpty() && amount > 0) {
            throw new IllegalArgumentException("Resource is empty but the amount is positive: " + amount);
        }

        this.setStack(resource.toStack(amount));
    }
}
