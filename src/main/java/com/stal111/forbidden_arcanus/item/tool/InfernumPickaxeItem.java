package com.stal111.forbidden_arcanus.item.tool;

import java.util.List;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfernumPickaxeItem extends ModPickaxeItem {

	public InfernumPickaxeItem(String name, IItemTier tier, int attackDamageIn, float attackSpeedIn) {
		super(name, tier, attackDamageIn, attackSpeedIn);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslationTextComponent("tooltip." + Main.MODID + ".infernum_pickaxe")).applyTextStyle(TextFormatting.GRAY));
	}


}
