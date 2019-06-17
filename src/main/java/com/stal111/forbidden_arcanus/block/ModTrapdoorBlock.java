package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.TrapDoorBlock;

public class ModTrapdoorBlock extends TrapDoorBlock {

	public ModTrapdoorBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
