package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

/**
 * Sanity Meter Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.SanityMeterItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class SanityMeterItem extends Item {

    public SanityMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            if (world.isClientSide()) {
                player.displayClientMessage(new TranslatableComponent("forbidden_arcanus.aureal").append(": " + aureal.getAureal() + "/200 - ").append(new TranslatableComponent("forbidden_arcanus.corruption").append(": " + aureal.getCorruption() + "/100")), true);
            }
        });
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
