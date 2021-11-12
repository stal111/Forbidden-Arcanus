package com.stal111.forbidden_arcanus.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnchantmentGlintItem extends Item {

	public EnchantmentGlintItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

}
