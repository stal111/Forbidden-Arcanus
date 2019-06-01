
package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunicTenebrisFrameBlock extends WaterloggedBlock {

	public RunicTenebrisFrameBlock(String name, Properties properties) {
		super(name, properties);
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
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
