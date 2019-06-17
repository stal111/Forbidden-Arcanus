package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.SlabBlock;

public class ModSlabBlock extends SlabBlock {

	public ModSlabBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
