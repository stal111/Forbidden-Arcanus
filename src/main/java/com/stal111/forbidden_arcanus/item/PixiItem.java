package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.sound.ModSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PixiItem extends Item {

	public PixiItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() == ModItems.pixi) {
			if (!world.isRemote) {
				player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 100));
				player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 160));
				player.getHeldItem(hand).damageItem(1, player, p_220038_0_ -> p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND));
			}
			player.playSound(ModSounds.pixi_activated, 1.0F, 1.0F);
			return new ActionResult<>(ActionResultType.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityIn;
			if (stack.getItem() == ModItems.pixi) {
				player.fallDistance = 0;
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
