package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
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

import javax.annotation.Nonnull;

/**
 * Draco Arcanus Scepter Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.DracoArcanusScepterItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class DracoArcanusScepterItem extends Item {

	private static final int USE_DURATION = 30;
	private static final int COOLDOWN_TICKS = 10;

	public DracoArcanusScepterItem(Item.Properties properties) {
		super(properties);
	}

	@Nonnull
	@Override
	public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			if (!level.isClientSide()) {
				level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.ENERGY_BALL_LAUNCH.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);

				EnergyBall energyBall = new EnergyBall(level, player, player.getLookAngle().x * 1, player.getLookAngle().y * 1, player.getLookAngle().z * 1);
				energyBall.setPos(energyBall.getX(), player.getY() + player.getEyeHeight(), energyBall.getZ());

				level.addFreshEntity(energyBall);
			}
			player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
		}
		return super.finishUsingItem(stack, level, livingEntity);
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
		player.startUsingItem(hand);

		return new InteractionResultHolder<>(InteractionResult.sidedSuccess(level.isClientSide()), player.getItemInHand(hand));
	}

	@Nonnull
	@Override
	public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
		return UseAnim.BOW;
	}
	
	@Override
	public int getUseDuration(@Nonnull ItemStack stack) {
		return USE_DURATION;
	}

	@Override
	public boolean isEnchantable(@Nonnull ItemStack stack) {
		return false;
	}
}
