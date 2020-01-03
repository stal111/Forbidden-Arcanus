package com.stal111.forbidden_arcanus.item.armor;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BasicArmor extends ArmorItem {

	public BasicArmor(String name, IArmorMaterial materialIn, EquipmentSlotType slots) {
		super(materialIn, slots, new Item.Properties().group(Main.FORBIDDEN_ARCANUS));
		this.setRegistryName(new ResourceLocation(Main.MOD_ID, name));
	}
	
	

}
