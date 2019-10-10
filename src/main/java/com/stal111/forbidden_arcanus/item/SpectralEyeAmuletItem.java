package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.potion.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			((LivingEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.spectral_vision, 10, 0, false, false, true));
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}