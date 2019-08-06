package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.WoodButtonBlock;

public class ModWoodButtonBlock extends WoodButtonBlock {

	public ModWoodButtonBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
