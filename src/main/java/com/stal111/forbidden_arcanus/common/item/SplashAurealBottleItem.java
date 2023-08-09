package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.ThrownAurealBottle;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-10-22
 */
public class SplashAurealBottleItem extends Item {

    public SplashAurealBottleItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide()) {
            ThrownAurealBottle bottle = new ThrownAurealBottle(level, player);
            bottle.setItem(stack);
            bottle.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.7F, 1.0F);
            level.addFreshEntity(bottle);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        ItemStackUtils.shrinkStack(player, stack);

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
