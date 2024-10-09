package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Edelwood Oil Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EdelwoodOilItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class EdelwoodOilItem extends Item {

    public EdelwoodOilItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
