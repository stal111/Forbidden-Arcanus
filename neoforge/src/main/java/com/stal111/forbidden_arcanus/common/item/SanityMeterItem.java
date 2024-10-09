package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.network.chat.Component;
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
            EssenceHelper.getEssenceProvider(player).ifPresent(provider -> {
                player.displayClientMessage(Component.translatable("forbidden_arcanus.aureal").append(": " + provider.getAmount(EssenceType.AUREAL) + "/" + provider.getLimit(EssenceType.AUREAL)), true);
            });
        }

        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(level.isClientSide()), stack);
    }
}
