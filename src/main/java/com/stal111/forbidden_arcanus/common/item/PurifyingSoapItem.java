package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Purifying Soap Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.PurifyingSoapItem
 *
 * @author stal111
 * @version 2.0.0
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
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (entity.isInWaterRainOrBubble() && entity instanceof Player player) {
            player.getInventory().setItem(itemSlot, new ItemStack(ModItems.WET_PURIFYING_SOAP.get()));
        }
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
    }
}
