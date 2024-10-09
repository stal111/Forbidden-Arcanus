package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Spectral Eye Amulet Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SpectralEyeAmuletItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
		if (!level.isClientSide() && !this.isDeactivated(stack) && entity instanceof LivingEntity livingEntity) {
			//TODO
			//livingEntity.addEffect(new MobEffectInstance(ModMobEffects.SPECTRAL_VISION.get(), 40, 0, false, false, true));
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
	public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
		super.appendHoverText(stack, context, components, flag);

		Component toggle = Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + ".toggle").withStyle(ChatFormatting.GRAY);

		boolean deactivated = this.isDeactivated(stack);
		components.add(Component.translatable("tooltip." + ForbiddenArcanus.MOD_ID + (deactivated ? ".deactivated" : ".activated")).withStyle(deactivated ? ChatFormatting.RED : ChatFormatting.GREEN).append(" ").append(toggle));
	}

	public boolean isDeactivated(ItemStack stack) {
		return false;
		//TODO
//		return stack.getOrCreateTag().getBoolean("Deactivated");
	}

	public void setDeactivated(ItemStack stack, boolean deactivated) {
		//stack.getOrCreateTag().putBoolean("Deactivated", deactivated);
	}
}