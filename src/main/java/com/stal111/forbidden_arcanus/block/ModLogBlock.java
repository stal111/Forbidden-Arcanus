package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

public class ModLogBlock extends LogBlock {

	public ModLogBlock(String name, MaterialColor color, Properties properties) {
		super(color, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
