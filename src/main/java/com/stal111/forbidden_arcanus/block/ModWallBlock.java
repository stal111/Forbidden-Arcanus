package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.WallBlock;

public class ModWallBlock extends WallBlock {

	public ModWallBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
