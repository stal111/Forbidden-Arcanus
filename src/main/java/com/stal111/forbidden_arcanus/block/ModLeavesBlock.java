package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.LeavesBlock;
import net.minecraft.util.ResourceLocation;

public class ModLeavesBlock extends LeavesBlock {

	public ModLeavesBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
}
