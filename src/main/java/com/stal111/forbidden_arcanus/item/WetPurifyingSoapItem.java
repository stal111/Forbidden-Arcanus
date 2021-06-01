package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.ICounterCapability;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

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
            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                SimpleCounter counter = getTimer(counterCapability);

                if (entity.isInWaterRainOrBubbleColumn()) {
                    counter.resetTimer();
                } else {
                    counter.increase();

                    if (counter.getValue() >= 3600) {
                        entity.setItem(new ItemStack(NewModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
        }
        return false;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (entity.getEntityWorld().getDimensionType().isUltrawarm()) {
                stack.shrink(1);
                player.inventory.setInventorySlotContents(itemSlot, new ItemStack(NewModItems.PURIFYING_SOAP.get()));
            }
            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                SimpleCounter timer = getTimer(counterCapability);

                if (player.isInWaterRainOrBubbleColumn()) {
                    timer.resetTimer();
                } else {
                    timer.increase();

                    if (timer.getValue() >= 3600) {
                        stack.shrink(1);
                        player.inventory.setInventorySlotContents(itemSlot, new ItemStack(NewModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
        }
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CounterProvider(Collections.singletonList(new SimpleCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dry_timer"))));
    }

    private SimpleCounter getTimer(ICounterCapability counterCapability) {
        return counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dry_timer"));
    }
}
