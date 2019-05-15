package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockButtonWood;
import net.minecraft.util.ResourceLocation;

public class ModButtonBlock extends BlockButtonWood {

	public ModButtonBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
