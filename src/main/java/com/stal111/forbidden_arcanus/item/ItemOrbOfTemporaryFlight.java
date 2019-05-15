package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.potion.ModPotions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemOrbOfTemporaryFlight extends BasicItem {

	public ItemOrbOfTemporaryFlight(String name) {
		super(name, new Item.Properties().maxStackSize(1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}
		player.getCooldownTracker().setCooldown(this, 150);
		if (!world.isRemote) {
			player.addPotionEffect(new PotionEffect(ModPotions.fly, 6000, 0, true, true));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

}
