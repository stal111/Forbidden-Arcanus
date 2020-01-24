
package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class RunicTenebrisFrameBlock extends WaterloggedBlock {

	public RunicTenebrisFrameBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return ActionResultType.SUCCESS;
		} else {
			boolean flag = state.get(WATERLOGGED);
			if (stack.getItem() == ModItems.DARK_NETHER_STAR.getItem() && !world.isRemote) {
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
				world.setBlockState(pos, ModBlocks.RUNIC_TENEBRIS_CORE.getBlock().getDefaultState().with(WATERLOGGED, flag));
				return ActionResultType.SUCCESS;
			}
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}
}
