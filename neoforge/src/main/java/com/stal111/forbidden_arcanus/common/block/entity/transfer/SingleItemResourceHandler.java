package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;

import java.util.function.Consumer;

public class SingleItemResourceHandler extends ItemStackResourceHandler {

    private static final Consumer<ItemStack> NO_OP_CONSUMER = stack -> {};

    private ItemStack stack = ItemStack.EMPTY;
    private final Consumer<ItemStack> onChanged;

    public SingleItemResourceHandler() {
        this(NO_OP_CONSUMER);
    }

    public SingleItemResourceHandler(Consumer<ItemStack> onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    protected int getCapacity(ItemResource resource) {
        return 1;
    }

    public void serialize(String key, ValueOutput output) {
        if (!this.stack.isEmpty()) {
            output.store(key, ItemStack.SINGLE_ITEM_CODEC, this.stack);
        }
    }

    public void deserialize(String key, ValueInput input) {
        this.stack = input.read(key, ItemStack.SINGLE_ITEM_CODEC).orElse(ItemStack.EMPTY);
    }

    public void set(int index, ItemResource resource, int amount) {
        TransferPreconditions.checkNonNegative(amount);
        if (resource.isEmpty() && amount > 0) {
            throw new IllegalArgumentException("Resource is empty but the amount is positive: " + amount);
        }

        ItemStack stack = resource.toStack(amount);

        if (!ItemStack.isSameItemSameComponents(this.getStack(), stack)) {
            this.setStack(stack);
            this.onChanged.accept(this.stack);
        }
    }
}
