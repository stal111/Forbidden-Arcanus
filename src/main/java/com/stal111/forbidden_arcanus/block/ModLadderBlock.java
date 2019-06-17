package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.LadderBlock;

public class ModLadderBlock extends LadderBlock {

	public ModLadderBlock(String name, Properties builder) {
		super(builder);
		this.setRegistryName(ModUtils.location(name));
	}
}
