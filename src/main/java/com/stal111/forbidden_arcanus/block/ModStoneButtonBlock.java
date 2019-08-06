package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WoodButtonBlock;

public class ModStoneButtonBlock extends StoneButtonBlock {

	public ModStoneButtonBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
