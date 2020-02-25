package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
		if (!world.isRemote) {
			player.addPotionEffect(new EffectInstance(ModEffects.FLY.get(), 6000, 0, true, false, true));
		}
		player.addStat(Stats.ITEM_USED.get(this));
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

}
