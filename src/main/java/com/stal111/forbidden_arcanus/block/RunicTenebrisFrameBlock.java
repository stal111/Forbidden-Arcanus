
package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RunicTenebrisFrameBlock extends WaterloggedBlock {

	public RunicTenebrisFrameBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getMainHandItem();
		if (stack.isEmpty()) {
			return InteractionResult.SUCCESS;
		} else {
			boolean flag = state.getValue(WATERLOGGED);
			if (stack.getItem() == NewModItems.DARK_NETHER_STAR.get() && !world.isClientSide) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				world.setBlockAndUpdate(pos, ModBlocks.RUNIC_TENEBRIS_CORE.getBlock().defaultBlockState().setValue(WATERLOGGED, flag));
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, world, pos, player, hand, result);
	}
}
