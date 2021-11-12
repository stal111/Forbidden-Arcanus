package com.stal111.forbidden_arcanus.item;

import java.util.List;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfernumPickaxeItem extends PickaxeItem {

	public InfernumPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			target.setSecondsOnFire(3);
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".infernum_pickaxe")).withStyle(ChatFormatting.GRAY));
	}
}
