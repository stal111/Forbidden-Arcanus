package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.Block;

public class BasicBlock extends Block {

	public BasicBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}
}
