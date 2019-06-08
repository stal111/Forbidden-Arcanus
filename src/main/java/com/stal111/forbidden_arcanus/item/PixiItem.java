package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.sound.ModSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PixiItem extends BasicItem {

	public PixiItem(String name, int maxDamage) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote) {
//			player.addPotionEffect(new PotionEffect(Potion.getPotionById(14)));
			player.getActiveItemStack().setDamage(player.getActiveItemStack().getDamage() - 1);
		}
		player.playSound(ModSounds.pixi_activated, 1.0F, 1.0F);
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityIn;
			if (player.getHeldItem(Hand.MAIN_HAND).getItem() == this || player.getHeldItem(Hand.OFF_HAND).getItem() == this) {
				entityIn.fallDistance = 0;
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
