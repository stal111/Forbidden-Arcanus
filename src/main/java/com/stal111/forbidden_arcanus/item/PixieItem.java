package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.sound.ModSounds;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class PixieItem extends Item {

	public PixieItem(Item.Properties properties) {
		super(properties.durability(256));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == ModItems.PIXIE.get()) {
			if (!world.isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100));
				player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160));
				player.getItemInHand(hand).hurtAndBreak(1, player, p_220038_0_ -> p_220038_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
			player.playSound(ModSounds.pixi_activated, 1.0F, 1.0F);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
		}
		return super.use(world, player, hand);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof Player) {
			Player player = (Player) entityIn;
			if (stack.getItem() == ModItems.PIXIE.get()) {
				player.fallDistance = 0;
			}
		}
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
