package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.FenceBlock;
import net.minecraft.util.ResourceLocation;

public class ModFenceBlock extends FenceBlock {

	public ModFenceBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
