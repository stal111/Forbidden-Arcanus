package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;

public class ModLogBlock extends LogBlock {

	public ModLogBlock(String name, MaterialColor color, Properties properties) {
		super(color, properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
