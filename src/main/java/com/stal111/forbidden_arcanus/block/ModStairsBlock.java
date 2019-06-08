package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class ModStairsBlock extends StairsBlock {
	
	public static final Block ARCANE_BASE_BLOCK_STAIRS = new BasicBlock("arcane_base_block_stairs", Block.Properties.create(Material.ROCK));

	public ModStairsBlock(String name, Properties properties, BlockState state) {
		super(state, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
