package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.function.Consumer;

public class ResultSlotItemHandler extends SingleSlotResourceHandler {

    public ResultSlotItemHandler() {
        super(false);
    }

    public ResultSlotItemHandler(Consumer<ItemStack> onChanged) {
        super(false, onChanged);
    }

    @Override
    protected boolean isValid(ItemResource resource) {
        return false;
    }
}
