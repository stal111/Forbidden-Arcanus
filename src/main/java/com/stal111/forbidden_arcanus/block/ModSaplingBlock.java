package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.trees.AbstractTree;
import net.minecraft.util.ResourceLocation;

public class ModSaplingBlock extends BlockSapling {

	public ModSaplingBlock(String name, AbstractTree tree, Properties properties) {
		super(tree, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
