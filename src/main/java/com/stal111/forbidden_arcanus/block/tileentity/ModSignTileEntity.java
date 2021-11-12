package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignTileEntity extends SignBlockEntity {

	public ModSignTileEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ModTileEntities.SIGN.get();
	}
}
