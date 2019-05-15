package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.potion.ModPotions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SpectralEyeAmuletItem extends BasicItem {

	public SpectralEyeAmuletItem(String name) {
		super(name, new Item.Properties().maxStackSize(1));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModPotions.spectral_vision, 180, 0, false, false, true));
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
