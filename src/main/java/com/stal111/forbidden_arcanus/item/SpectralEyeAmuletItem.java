package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpectralEyeAmuletItem extends Item {

	public SpectralEyeAmuletItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote() && !isDeactivated(stack)) {
			((LivingEntity) entity).addPotionEffect(new EffectInstance(ModEffects.SPECTRAL_VISION.get(), 40, 0, false, false, true));
		}
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		boolean flag = isDeactivated(stack);
		setDeactivated(stack, !flag);

		return ActionResult.resultSuccess(stack);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);

		ITextComponent toggle = new TranslationTextComponent("tooltip." + Main.MOD_ID + ".toggle").func_240699_a_(TextFormatting.GRAY);

		tooltip.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + (isDeactivated(stack) ? ".deactivated" : ".activated")).func_240699_a_(isDeactivated(stack) ? TextFormatting.RED : TextFormatting.GREEN).func_240702_b_(" ").func_230529_a_(toggle));
	}

	public static boolean isDeactivated(ItemStack stack) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();

		return compoundnbt.getBoolean("Deactivated");
	}

	public static void setDeactivated(ItemStack stack, boolean deactivated) {
		CompoundNBT compoundnbt = stack.getOrCreateTag();
		compoundnbt.putBoolean("Deactivated", deactivated);
	}
}