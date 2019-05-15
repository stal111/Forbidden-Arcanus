package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.util.ResourceLocation;

public class ModTrapdoorBlock extends BlockTrapDoor {

	public ModTrapdoorBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
