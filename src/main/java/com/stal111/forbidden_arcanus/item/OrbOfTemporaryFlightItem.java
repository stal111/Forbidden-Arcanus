package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Orb of Temporary Flight Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.OrbOfTemporaryFlightItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class OrbOfTemporaryFlightItem extends Item {

	public OrbOfTemporaryFlightItem(Item.Properties properties) {
		super(properties);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity player, @Nonnull Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		ItemStackUtils.shrinkStack(player, stack);

		player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
			SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

			counter.setActive(true);
			counter.setValue(counter.getValue() + ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get());
		});

		player.addStat(Stats.ITEM_USED.get(this));

		return ActionResult.func_233538_a_(stack, world.isRemote());
	}

	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".duration").mergeStyle(TextFormatting.GRAY).appendString(": " + StringUtils.ticksToElapsedTime(ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get())));
	}
}
