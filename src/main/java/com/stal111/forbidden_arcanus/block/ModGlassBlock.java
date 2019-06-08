package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.GlassBlock;
import net.minecraft.util.ResourceLocation;

public class ModGlassBlock extends GlassBlock {

	public ModGlassBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
}
