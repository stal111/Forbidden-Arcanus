package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.function.Consumer;

public class ResultSlotResourceHandler extends SingleSlotResourceHandler {

    public ResultSlotResourceHandler(boolean singleCapacity) {
        super(singleCapacity);
    }

    public ResultSlotResourceHandler(boolean singleCapacity, Consumer<ItemStack> onChanged) {
        super(singleCapacity, onChanged);
    }

    @Override
    protected boolean isValid(ItemResource resource) {
        return false;
    }
}
