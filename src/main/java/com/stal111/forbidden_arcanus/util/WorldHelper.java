package com.stal111.forbidden_arcanus.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldHelper {

	public static boolean isServerWorld(World world) {
		return world != null && !world.isRemote;
	}

	public static boolean isClientWorld(World world) {
		return !isServerWorld(world);
	}

	public static void updateClient(World world, BlockPos pos) {
		if (world != null) {
			BlockState iBlockState = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, iBlockState, iBlockState, 4);
		}
	}

}
