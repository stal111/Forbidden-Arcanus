package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.config.ItemConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class XpetrifiedOrbItem extends Item {

    public XpetrifiedOrbItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.giveExperiencePoints(ItemConfig.XPETRIFIED_ORB_EXPERIENCE_POINTS.get());

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
