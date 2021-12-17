package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
        if (entity.isInWaterRainOrBubble()) {
            entity.setItem(new ItemStack(ModItems.WET_PURIFYING_SOAP.get()));
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity.isInWaterRainOrBubble() && entity instanceof Player) {
            Player player = (Player) entity;
            stack.shrink(1);
            player.getInventory().setItem(itemSlot, new ItemStack(ModItems.WET_PURIFYING_SOAP.get()));
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }
}
