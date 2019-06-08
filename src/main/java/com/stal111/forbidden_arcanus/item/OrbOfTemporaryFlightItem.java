package com.stal111.forbidden_arcanus.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OrbOfTemporaryFlightItem extends BasicItem {

	public OrbOfTemporaryFlightItem(String name) {
		super(name, new Item.Properties().maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (!player.playerAbilities.isCreativeMode) {
			stack.shrink(1);
		}
		if (!world.isRemote) {
//			player.addPotionEffect(new PotionEffect(null, 0, true, false));

		}
		player.addStat(Stats.ITEM_USED.get(this));
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
	}

}
