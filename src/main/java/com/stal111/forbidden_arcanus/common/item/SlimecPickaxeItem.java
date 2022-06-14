package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;

import javax.annotation.Nonnull;

/**
 * Slimec Pickaxe Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SlimecPickaxeItem
 *
 * @author stal111
 * @version 1.19 - 2.0.1
 */
public class SlimecPickaxeItem extends PickaxeItem {

	public SlimecPickaxeItem(Tier tier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public void fillItemCategory(@Nonnull CreativeModeTab tab, @Nonnull NonNullList<ItemStack> items) {
		if (this.allowedIn(tab)) {
			ItemStack stack = new ItemStack(this);

			stack.enchant(Enchantments.SILK_TOUCH, 1);
			items.add(stack);
		}
	}
}
