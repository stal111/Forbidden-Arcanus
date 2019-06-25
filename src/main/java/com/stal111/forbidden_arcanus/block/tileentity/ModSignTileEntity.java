package com.stal111.forbidden_arcanus.block.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModSignTileEntity extends SignTileEntity {

	public ModSignTileEntity() {
		super();
	}

	@Override
	public TileEntityType<?> getType() {
		return ModTileEntities.sign;
	}
}
