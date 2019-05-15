package com.stal111.forbidden_arcanus.item;

import net.minecraft.item.ItemStack;

public class EnchantmentGlintItem extends BasicItem {

	public EnchantmentGlintItem(String name) {
		super(name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
