package com.stal111.forbidden_arcanus.common.item.group;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

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

	public ModItemGroup(CreativeModeTab.Builder builder) {
		super(builder);
		//this.setBackgroundImage(new ResourceLocation(ForbiddenArcanus.MOD_ID, PATH + "tab_forbidden_arcanus.png"));
		//this.hideTitle();
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}

	@Override
	public int getSearchBarWidth() {
		return 88;
	}

	@Nonnull
	@Override
	public ResourceLocation getTabsImage() {
		return new ResourceLocation(ForbiddenArcanus.MOD_ID, PATH + "forbidden_arcanus_tabs.png");
	}
}
