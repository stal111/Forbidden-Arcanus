package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.sound.ModSounds;

import net.minecraft.entity.Entity;
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
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(14)));
			player.getActiveItemStack().damageItem(1, player);
		}
		player.playSound(ModSounds.pixi_activated, 1.0F, 1.0F);
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == this || player.getHeldItem(EnumHand.OFF_HAND).getItem() == this) {
				entityIn.fallDistance = 0;
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
