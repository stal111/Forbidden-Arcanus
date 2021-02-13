package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Purifying Soap Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.PurifyingSoapItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-31
 */
public class PurifyingSoapItem extends Item {

    public PurifyingSoapItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.isInWaterRainOrBubbleColumn()) {
            entity.setItem(new ItemStack(NewModItems.WET_PURIFYING_SOAP.get()));
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity.isInWaterRainOrBubbleColumn() && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            stack.shrink(1);
            player.inventory.setInventorySlotContents(itemSlot, new ItemStack(NewModItems.WET_PURIFYING_SOAP.get()));
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }
}
