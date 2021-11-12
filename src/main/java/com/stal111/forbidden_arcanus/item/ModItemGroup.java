package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class ModItemGroup extends CreativeModeTab {

	public ModItemGroup(String label) {
		super(label);
		this.setBackgroundSuffix("forbidden_arcanus_item_search.png");
		this.hideTitle();
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModBlocks.RUNIC_TENEBRIS_CORE.getBlock());
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	@Override
	public int getSearchbarWidth() {
		return 88;
	}
	
	@Override
	public ResourceLocation getTabsImage() {
		return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/forbidden_arcanus_tabs.png");
	}

}
