package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.giveExperiencePoints(EssenceHelper.getEssenceAmount(stack, EssenceType.EXPERIENCE));
        stack.consume(1, player);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
