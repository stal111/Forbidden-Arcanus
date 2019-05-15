package com.stal111.forbidden_arcanus.item;

import java.util.List;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ObsidianSkullShieldItem extends BasicItem {

	public ObsidianSkullShieldItem(String registryName) {
		super(registryName);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(12), 180, 1, false, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.getPotionById(11), 180, 0, false, false, true));
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TextComponentTranslation("tooltip." + Main.MODID + ".obsidian_skull_shield")).applyTextStyle(TextFormatting.GRAY));
	}

}
