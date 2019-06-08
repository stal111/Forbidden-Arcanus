package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.WallBlock;
import net.minecraft.util.ResourceLocation;

public class ModWallBlock extends WallBlock {

	public ModWallBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
