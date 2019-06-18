package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.Block;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ModStandingSignBlock extends StandingSignBlock {

	public ModStandingSignBlock(String name, Block.Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new ModSignTileEntity();
	}

}
