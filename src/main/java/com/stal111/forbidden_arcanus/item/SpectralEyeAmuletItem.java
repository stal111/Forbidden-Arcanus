package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.potion.effect.ModEffects;
import net.minecraft.command.impl.TeamCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote()) {
			((LivingEntity) entity).addPotionEffect(new EffectInstance(ModEffects.spectral_vision, 40, 0, false, false, true));
		}
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}
}