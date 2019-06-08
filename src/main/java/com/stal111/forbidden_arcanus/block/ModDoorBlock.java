package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.DoorBlock;
import net.minecraft.util.ResourceLocation;

public class ModDoorBlock extends DoorBlock {

	public ModDoorBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
