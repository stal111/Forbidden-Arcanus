package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.SkullBlock;
import net.minecraft.util.ResourceLocation;

public class ModSkullBlock extends SkullBlock {

	public ModSkullBlock(String name, ISkullType type, Properties properties) {
		super(type, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));

	}

}
