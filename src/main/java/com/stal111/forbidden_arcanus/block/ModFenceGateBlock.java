package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.util.ResourceLocation;

public class ModFenceGateBlock extends FenceGateBlock {

	public ModFenceGateBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
