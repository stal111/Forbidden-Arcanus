package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Sanity Meter Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SanityMeterItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class SanityMeterItem extends Item {

    public SanityMeterItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            IAureal aureal = AurealHelper.getCapability(player);

            player.displayClientMessage(new TranslatableComponent("forbidden_arcanus.aureal").append(": " + aureal.getAureal() + "/200 - ").append(new TranslatableComponent("forbidden_arcanus.corruption").append(": " + aureal.getCorruption() + "/100")), true);
        }

        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(level.isClientSide()), stack);
    }
}
