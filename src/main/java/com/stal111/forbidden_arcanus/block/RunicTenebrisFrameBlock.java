
package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class RunicTenebrisFrameBlock extends WaterloggedBlock {

	public RunicTenebrisFrameBlock(String name, Properties properties) {
		super(name, properties);
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return true;
		} else {
			boolean flag = state.get(WATERLOGGED);
			if (stack.getItem() == ModItems.dark_nether_star && !world.isRemote) {
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
				world.setBlockState(pos, ModBlocks.runic_tenebris_core.getStateContainer().getBaseState()
						.with(WATERLOGGED, Boolean.valueOf(flag)));
				return true;
			} else {
				return false;
			}
		}
	}
}
