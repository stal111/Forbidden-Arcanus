package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Aureal Bottle Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.AurealBottleItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-16
 */
public class AurealBottleItem extends Item {

    private static final int USE_DURATION = 32;

    public AurealBottleItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity livingEntity) {
        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);

        if (!(livingEntity instanceof Player player)) {
            return emptyBottle;
        }

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        ItemStackUtils.shrinkStack(player, stack);

        AurealHelper.increaseAureal(player, 35);

        if (!player.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return emptyBottle;
            }

            player.getInventory().add(emptyBottle);
        }

        return stack;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return USE_DURATION;
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (AurealHelper.getAureal(player) < 200) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return super.use(level, player, hand);
    }
}
