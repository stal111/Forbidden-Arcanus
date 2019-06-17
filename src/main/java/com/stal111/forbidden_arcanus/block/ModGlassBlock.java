package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.GlassBlock;

public class ModGlassBlock extends GlassBlock {

	public ModGlassBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}
}
