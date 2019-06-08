package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.ResourceLocation;

public class ModSaplingBlock extends SaplingBlock {

	public ModSaplingBlock(String name, Tree tree, Properties properties) {
		super(tree, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
