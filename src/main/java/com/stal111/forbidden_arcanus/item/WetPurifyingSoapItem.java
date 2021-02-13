package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.capability.item.timer.TimerProvider;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * Wet Purifying Soap Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.WetPurifyingSoapItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-31
 */
public class WetPurifyingSoapItem extends Item implements ITimerItem {

    public WetPurifyingSoapItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (entity.getEntityWorld().getDimensionType().isUltrawarm()) {
            entity.setItem(new ItemStack(NewModItems.PURIFYING_SOAP.get()));
        } else {
            stack.getCapability(TimerProvider.CAPABILITY).ifPresent(timer -> {
                if (entity.isInWaterRainOrBubbleColumn()) {
                    timer.resetTimer();
                } else {
                    timer.increaseTimer();

                    if (timer.getTimer() >= 3600) {
                        entity.setItem(new ItemStack(NewModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (entity.getEntityWorld().getDimensionType().isUltrawarm()) {
                stack.shrink(1);
                player.inventory.setInventorySlotContents(itemSlot, new ItemStack(NewModItems.PURIFYING_SOAP.get()));
            }
            stack.getCapability(TimerProvider.CAPABILITY).ifPresent(timer -> {
                if (player.isInWaterRainOrBubbleColumn()) {
                    timer.resetTimer();
                } else {
                    timer.increaseTimer();

                    if (timer.getTimer() >= 3600) {
                        stack.shrink(1);
                        player.inventory.setInventorySlotContents(itemSlot, new ItemStack(NewModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            aureal.decreaseCorruption(20);
        });

        if (!world.isRemote()) {
            player.clearActivePotions();
        }

        if (world.getRandom().nextDouble() <= 0.65 && !world.isRemote()) {
            ItemStackUtils.shrinkStack(player, stack);
        }
        player.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
