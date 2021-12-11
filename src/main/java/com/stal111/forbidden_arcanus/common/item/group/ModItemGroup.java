package com.stal111.forbidden_arcanus.common.item.group;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Mod Item Group <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.group.ModItemGroup
 *
 * @author stal111
 * @version 2.0.0
 */
public class ModItemGroup extends CreativeModeTab {

	private static final String PATH = "textures/gui/container/creative_inventory/";

	public ModItemGroup(String label) {
		super(label);
		this.setBackgroundImage(new ResourceLocation(ForbiddenArcanus.MOD_ID, PATH + "tab_forbidden_arcanus.png"));
		this.hideTitle();
	}

	@Nonnull
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get());
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}
	
	@Override
	public int getSearchbarWidth() {
		return 88;
	}
	
	@Nonnull
	@Override
	public ResourceLocation getTabsImage() {
		return new ResourceLocation(ForbiddenArcanus.MOD_ID, PATH + "forbidden_arcanus_tabs.png");
	}
}
