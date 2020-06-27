package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.capability.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class OrbOfTemporaryFlightItem extends Item {

	public OrbOfTemporaryFlightItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}

		player.getCapability(FlightTimeLeftCapability.FLIGHT_TIME_LEFT_CAPABILITY).ifPresent(iFlightTimeLeft ->
				iFlightTimeLeft.setFlightTimeLeft(iFlightTimeLeft.getFlightTimeLeft() + ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get()));


		player.addStat(Stats.ITEM_USED.get(this));
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);

		//TODO
		//tooltip.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".duration").applyTextStyle(TextFormatting.GRAY).appendText(": " + StringUtils.ticksToElapsedTime(ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get())));
	}
}
