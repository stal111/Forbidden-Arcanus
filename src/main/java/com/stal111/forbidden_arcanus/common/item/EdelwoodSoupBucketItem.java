package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Edelwood Soup Bucket Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EdelwoodSoupBucketItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-09
 */
public class EdelwoodSoupBucketItem extends Item implements CapacityBucket {

    private final Supplier<Item> soup;

    public EdelwoodSoupBucketItem(Supplier<Item> soup, Properties properties) {
        super(properties);
        this.soup = soup;
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        super.finishUsingItem(stack.copy(), level, livingEntity);

        return this.tryDrain(stack);
    }


    @Override
    public int getCapacity() {
        return ItemConfig.EDELWOOD_SOUP_BUCKET_CAPACITY.get();
    }

    @Override
    public ItemStack getEmptyBucket() {
        return new ItemStack(ModItems.EDELWOOD_BUCKET.get());
    }

    public Item getSoup() {
        return soup.get();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
    }
}
