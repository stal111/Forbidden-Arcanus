package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.function.Consumer;

public class UnmodifiableSlotResourceHandler extends SingleSlotResourceHandler {

    public UnmodifiableSlotResourceHandler(boolean singleCapacity) {
        super(singleCapacity);
    }

    public UnmodifiableSlotResourceHandler(boolean singleCapacity, Consumer<ItemStack> onChanged) {
        super(singleCapacity, onChanged);
    }

    @Override
    protected boolean isValid(ItemResource resource) {
        return false;
    }

    @Override
    public int extract(ItemResource resource, int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
        return 0;
    }
}
