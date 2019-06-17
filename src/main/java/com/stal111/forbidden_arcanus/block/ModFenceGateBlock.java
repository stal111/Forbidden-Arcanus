package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.FenceGateBlock;

public class ModFenceGateBlock extends FenceGateBlock {

	public ModFenceGateBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
