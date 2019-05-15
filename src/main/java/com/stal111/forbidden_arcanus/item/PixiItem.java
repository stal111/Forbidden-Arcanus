package com.stal111.forbidden_arcanus.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class PixiItem extends BasicItem {

	public PixiItem(String name, int maxDamage) {
		super(name);
		// TODO
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(14)));
			playerIn.fallDistance = 0;
			playerIn.getActiveItemStack().damageItem(1, playerIn);
		}
		return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
