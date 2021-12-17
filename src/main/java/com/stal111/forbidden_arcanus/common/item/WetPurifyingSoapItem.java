package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.ICounterCapability;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;

/**
 * Wet Purifying Soap Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.WetPurifyingSoapItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-01-31
 */
public class WetPurifyingSoapItem extends Item {

    public WetPurifyingSoapItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (this.isUltraWarm(entity)) {
            entity.setItem(new ItemStack(ModItems.PURIFYING_SOAP.get()));
        } else {
            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                SimpleCounter counter = getTimer(counterCapability);

                if (entity.isInWaterRainOrBubble()) {
                    counter.resetTimer();
                } else {
                    counter.increase();

                    if (counter.getValue() >= 3600) {
                        entity.setItem(new ItemStack(ModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
        }
        return false;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player player) {
            if (this.isUltraWarm(player)) {
                player.getInventory().setItem(itemSlot, new ItemStack(ModItems.PURIFYING_SOAP.get()));
            }
            stack.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                SimpleCounter timer = getTimer(counterCapability);

                if (player.isInWaterRainOrBubble()) {
                    timer.resetTimer();
                } else {
                    timer.increase();

                    if (timer.getValue() >= 3600) {
                        player.getInventory().setItem(itemSlot, new ItemStack(ModItems.PURIFYING_SOAP.get()));
                    }
                }
            });
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

        AurealHelper.getCapability(player).decreaseAureal(20);

        if (!level.isClientSide()) {
            player.removeAllEffects();

            if (level.getRandom().nextDouble() <= 0.65) {
                ItemStackUtils.shrinkStack(player, stack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(level.isClientSide()), stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CounterProvider(Collections.singletonList(new SimpleCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dry_timer"))));
    }

    private SimpleCounter getTimer(ICounterCapability counterCapability) {
        return counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dry_timer"));
    }
}
