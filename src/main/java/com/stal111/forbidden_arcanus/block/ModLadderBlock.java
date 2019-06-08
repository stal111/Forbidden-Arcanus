package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.LadderBlock;
import net.minecraft.util.ResourceLocation;

public class ModLadderBlock extends LadderBlock {

	public ModLadderBlock(String name, Properties builder) {
		super(builder);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
}
