package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;

public class FallingCutoutBlock extends FallingBlock {

	public FallingCutoutBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
