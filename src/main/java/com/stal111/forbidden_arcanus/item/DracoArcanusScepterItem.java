package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DracoArcanusScepterItem extends Item {

	public DracoArcanusScepterItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			if (!world.isRemote) {
				this.fireSphere(world, player);
			}
			player.getCooldownTracker().setCooldown(this, 10);
		}
		return super.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		player.setActiveHand(hand);
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	private void fireSphere(World world, PlayerEntity player) {
		world.playSound(null, player.getPosition(), ModSounds.dark_bolt_launch, SoundCategory.NEUTRAL, 1.0f, 1.0f);
		spawnSphere(player);

	}

	private void spawnSphere(PlayerEntity player) {
		EnergyBallEntity energyBall = new EnergyBallEntity(player.getEntityWorld(), player, player.getLookVec().x * 1, player.getLookVec().y * 1, player.getLookVec().z * 1);
		energyBall.setPosition(energyBall.getPosX(), player.getPosY() + player.getEyeHeight(), energyBall.getPosZ());

		player.getEntityWorld().addEntity(energyBall);
	}
}