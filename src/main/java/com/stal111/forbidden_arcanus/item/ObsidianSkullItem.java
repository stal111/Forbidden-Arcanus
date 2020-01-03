package com.stal111.forbidden_arcanus.item;

import java.util.List;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ObsidianSkullItem extends Item {

	public ObsidianSkullItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof  LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			if (!worldIn.isRemote) {
				livingEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0, false, false, true));
			}
		}
		super.inventoryTick(stack, worldIn, entity, itemSlot, isSelected);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".obsidian_skull").applyTextStyle(TextFormatting.GRAY));
	}

}
