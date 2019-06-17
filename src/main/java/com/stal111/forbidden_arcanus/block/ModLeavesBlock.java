package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.LeavesBlock;

public class ModLeavesBlock extends LeavesBlock {

	public ModLeavesBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}
}
