package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Wet Purifying Soap Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.WetPurifyingSoapItem
 *
 * @author stal111
 * @since 2021-01-31
 */
public class WetPurifyingSoapItem extends Item {

    private static final ResourceLocation COUNTER = ForbiddenArcanus.location("dry_timer");

    private static final double CONSUME_CHANCE = 0.65D;

    public WetPurifyingSoapItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (this.isUltraWarm(entity)) {
            entity.setItem(new ItemStack(ModItems.PURIFYING_SOAP.get()));
        } else {
            //TODO
//            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
//                SimpleCounter counter = this.getCounter(counterCapability);
//
//                if (entity.isInWaterRainOrBubble()) {
//                    counter.reset();
//                } else {
//                    counter.increase();
//
//                    if (counter.getValue() >= 3600) {
//                        entity.setItem(new ItemStack(ModItems.PURIFYING_SOAP.get()));
//                    }
//                }
//            });
        }
        return false;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player player) {
            if (this.isUltraWarm(player)) {
                player.getInventory().setItem(itemSlot, new ItemStack(ModItems.PURIFYING_SOAP.get()));
            }
            //TODO
//            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
//                SimpleCounter timer = this.getCounter(counterCapability);
//
//                if (player.isInWaterRainOrBubble()) {
//                    timer.reset();
//                } else {
//                    timer.increase();
//
//                    if (timer.getValue() >= 3600) {
//                        player.getInventory().setItem(itemSlot, new ItemStack(ModItems.PURIFYING_SOAP.get()));
//                    }
//                }
//            });
        }
        super.inventoryTick(stack, level, entity, itemSlot, isSelected);
    }

    /**
     * Checks if the Entity is in a Dimension marked as Ultra Warm.
     */
    private boolean isUltraWarm(Entity entity) {
        return entity.getCommandSenderWorld().dimensionType().ultraWarm();
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        //TODO: new use

        if (!level.isClientSide()) {
            player.removeAllEffects();

            if (level.getRandom().nextDouble() < CONSUME_CHANCE) {
                ItemStackUtils.shrinkStack(player, stack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
