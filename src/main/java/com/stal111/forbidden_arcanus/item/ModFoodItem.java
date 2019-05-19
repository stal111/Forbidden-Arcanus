package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ModFoodItem extends ItemFood {

	public ModFoodItem(String name, int healAmountIn, float saturation, boolean meat) {
		super(healAmountIn, saturation, meat, new Item.Properties().group(Main.FORBIDDEN_ARCANUS));
		this.setRegistryName(Main.MODID, name);
	}

}
