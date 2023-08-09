package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterProvider;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Orb of Temporary Flight Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.OrbOfTemporaryFlightItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class OrbOfTemporaryFlightItem extends Item {

	public OrbOfTemporaryFlightItem(Item.Properties properties) {
		super(properties);
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		ItemStackUtils.shrinkStack(player, stack);

		player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
			SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

			counter.setActive(true);
			counter.setValue(counter.getValue() + ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get());
		});

		player.awardStat(Stats.ITEM_USED.get(this));

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		tooltip.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".duration").withStyle(ChatFormatting.GRAY).append(": " + StringUtil.formatTickDuration(ItemConfig.ORB_OF_TEMPORARY_FLIGHT_TIME.get())));
	}
}
