package com.stal111.forbidden_arcanus.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WorldHelper {

	public static boolean isServerWorld(Level world) {
		return world != null && !world.isClientSide;
	}

	public static boolean isClientWorld(Level world) {
		return !isServerWorld(world);
	}

	public static void updateClient(Level world, BlockPos pos) {
		if (world != null) {
			BlockState iBlockState = world.getBlockState(pos);
			world.sendBlockUpdated(pos, iBlockState, iBlockState, 4);
		}
	}

}
