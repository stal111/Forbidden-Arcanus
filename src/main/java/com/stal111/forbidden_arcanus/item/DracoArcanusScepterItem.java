package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class DracoArcanusScepterItem extends Item {

	public DracoArcanusScepterItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
		if (entityLiving instanceof Player) {
			Player player = (Player) entityLiving;
			if (!world.isClientSide) {
				this.fireSphere(world, player);
			}
			player.getCooldowns().addCooldown(this, 10);
		}
		return super.finishUsingItem(stack, world, entityLiving);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		player.startUsingItem(hand);
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	private void fireSphere(Level world, Player player) {
		world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.dark_bolt_launch, SoundSource.NEUTRAL, 1.0f, 1.0f);
		spawnSphere(player);

	}

	private void spawnSphere(Player player) {
		EnergyBallEntity energyBall = new EnergyBallEntity(player.getCommandSenderWorld(), player, player.getLookAngle().x * 1, player.getLookAngle().y * 1, player.getLookAngle().z * 1);
		energyBall.setPos(energyBall.getX(), player.getY() + player.getEyeHeight(), energyBall.getZ());

		player.getCommandSenderWorld().addFreshEntity(energyBall);
	}
}