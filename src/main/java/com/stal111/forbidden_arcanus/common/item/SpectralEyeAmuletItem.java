package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Spectral Eye Amulet Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SpectralEyeAmuletItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
		if (!level.isClientSide() && !this.isDeactivated(stack) && entity instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(ModEffects.SPECTRAL_VISION.get(), 40, 0, false, false, true));
		}
		super.inventoryTick(stack, level, entity, itemSlot, isSelected);
	}

	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		this.setDeactivated(stack, !this.isDeactivated(stack));

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);

		Component toggle = new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".toggle").withStyle(ChatFormatting.GRAY);

		boolean deactivated = this.isDeactivated(stack);
		tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + (deactivated ? ".deactivated" : ".activated")).withStyle(deactivated ? ChatFormatting.RED : ChatFormatting.GREEN).append(" ").append(toggle));
	}

	public boolean isDeactivated(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("Deactivated");
	}

	public void setDeactivated(ItemStack stack, boolean deactivated) {
		stack.getOrCreateTag().putBoolean("Deactivated", deactivated);
	}
}