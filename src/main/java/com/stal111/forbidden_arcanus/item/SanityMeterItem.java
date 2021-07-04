package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            if (world.isRemote()) {
                player.sendStatusMessage(new TranslationTextComponent("forbidden_arcanus.aureal").appendString(": " + aureal.getAureal() + "/200 - ").append(new TranslationTextComponent("forbidden_arcanus.corruption").appendString(": " + aureal.getCorruption() + "/100")), true);
            }
        });
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
