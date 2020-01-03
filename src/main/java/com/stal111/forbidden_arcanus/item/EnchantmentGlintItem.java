package com.stal111.forbidden_arcanus.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantmentGlintItem extends Item {

	public EnchantmentGlintItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
