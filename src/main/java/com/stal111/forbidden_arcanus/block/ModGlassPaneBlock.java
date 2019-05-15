package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockGlassPane;
import net.minecraft.util.ResourceLocation;

public class ModGlassPaneBlock extends BlockGlassPane {

	public ModGlassPaneBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
