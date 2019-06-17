package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;

public class ModSaplingBlock extends SaplingBlock {

	public ModSaplingBlock(String name, Tree tree, Properties properties) {
		super(tree, properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
