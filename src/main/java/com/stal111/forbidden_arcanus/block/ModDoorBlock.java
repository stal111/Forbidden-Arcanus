package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockDoor;
import net.minecraft.util.ResourceLocation;

public class ModDoorBlock extends BlockDoor {

	public ModDoorBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
