package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.SkullBlock;

public class ModSkullBlock extends SkullBlock {

	public ModSkullBlock(String name, ISkullType type, Properties properties) {
		super(type, properties);
		this.setRegistryName(ModUtils.location(name));

	}

}
