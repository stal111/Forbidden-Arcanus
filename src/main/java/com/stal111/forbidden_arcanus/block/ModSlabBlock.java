package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockSlab;
import net.minecraft.util.ResourceLocation;

public class ModSlabBlock extends BlockSlab {

	public ModSlabBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
