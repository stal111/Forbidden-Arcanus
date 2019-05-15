package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ModBlocks;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModItemGroup extends ItemGroup {

	public ModItemGroup(String label) {
		super(label);
		this.setBackgroundImageName("forbidden_arcanus_item_search.png");
		this.setNoTitle();
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModBlocks.runic_tenebris_core);
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	@Override
	public int getSearchbarWidth() {
		return 65;
	}
	
	@Override
	public ResourceLocation getTabsImage() {
		return new ResourceLocation(Main.MODID, "textures/gui/forbidden_arcanus_tabs.png");
	}

}
