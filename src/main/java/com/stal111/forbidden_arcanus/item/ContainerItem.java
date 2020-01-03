package com.stal111.forbidden_arcanus.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerItem extends Item {

    final Item item;

    public ContainerItem(Item item, Properties properties) {
        super(properties);
        this.item = item;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(item);
    }
}
