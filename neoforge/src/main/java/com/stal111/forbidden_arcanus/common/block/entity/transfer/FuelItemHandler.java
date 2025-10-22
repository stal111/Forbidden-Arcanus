package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

import java.util.function.Consumer;
import java.util.function.Function;

public class FuelItemHandler extends SingleSlotResourceHandler {

    private final Function<ItemStack, Boolean> canSmelt;

    public FuelItemHandler(Function<ItemStack, Boolean> canSmelt, Consumer<ItemStack> onChanged) {
        super(false, onChanged);
        this.canSmelt = canSmelt;
    }

    @Override
    protected boolean isValid(ItemResource resource) {
        return this.canSmelt.apply(resource.toStack());
    }
}
