package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockFence;
import net.minecraft.util.ResourceLocation;

public class ModFenceBlock extends BlockFence {

	public ModFenceBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
