package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Slimec Pickaxe Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SlimecPickaxeItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class SlimecPickaxeItem extends PickaxeItem {

	public SlimecPickaxeItem(Tier tier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, livingEntity) == 0) {
			stack.enchant(Enchantments.SILK_TOUCH, 1);
		}
		super.inventoryTick(stack, level, entity, slotId, isSelected);
	}

	@Override
	public void fillItemCategory(@Nonnull CreativeModeTab tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.allowdedIn(tab)) {
			ItemStack stack = new ItemStack(this);

			stack.enchant(Enchantments.SILK_TOUCH, 1);
			items.add(stack);
		}
	}
}
