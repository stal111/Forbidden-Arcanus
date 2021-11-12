package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isClientSide() && !isDeactivated(stack)) {
			((LivingEntity) entity).addEffect(new MobEffectInstance(ModEffects.SPECTRAL_VISION.get(), 40, 0, false, false, true));
		}
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		boolean flag = isDeactivated(stack);
		setDeactivated(stack, !flag);

		return InteractionResultHolder.success(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, world, tooltip, flag);

		Component toggle = new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".toggle").withStyle(ChatFormatting.GRAY);

		tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + (isDeactivated(stack) ? ".deactivated" : ".activated")).withStyle(isDeactivated(stack) ? ChatFormatting.RED : ChatFormatting.GREEN).append(" ").append(toggle));
	}

	public static boolean isDeactivated(ItemStack stack) {
		CompoundTag compoundnbt = stack.getOrCreateTag();

		return compoundnbt.getBoolean("Deactivated");
	}

	public static void setDeactivated(ItemStack stack, boolean deactivated) {
		CompoundTag compoundnbt = stack.getOrCreateTag();
		compoundnbt.putBoolean("Deactivated", deactivated);
	}
}