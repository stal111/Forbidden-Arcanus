package com.stal111.forbidden_arcanus.item;

import net.minecraft.item.ItemStack;

public class MysticalDaggerItem extends BasicItem {

    public MysticalDaggerItem(String name) {
        super(name);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(this);
    }
}
