package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Xpetrified Orb Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.XpetrifiedOrbItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class XpetrifiedOrbItem extends Item {

    public XpetrifiedOrbItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.giveExperiencePoints(ItemConfig.XPETRIFIED_ORB_EXPERIENCE_POINTS.get());
        stack.consume(1, player);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
