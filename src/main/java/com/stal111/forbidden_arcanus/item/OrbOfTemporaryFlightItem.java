package com.stal111.forbidden_arcanus.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class OrbOfTemporaryFlightItem extends BasicItem {

	public OrbOfTemporaryFlightItem(String name) {
		super(name, new Item.Properties().maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}
		if (!world.isRemote) {
//			player.addPotionEffect(new PotionEffect(null, 0, true, false));

		}
		player.addStat(StatList.ITEM_USED.get(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

}
