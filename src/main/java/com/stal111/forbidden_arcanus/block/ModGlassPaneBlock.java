package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.PaneBlock;

public class ModGlassPaneBlock extends PaneBlock {

	public ModGlassPaneBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
