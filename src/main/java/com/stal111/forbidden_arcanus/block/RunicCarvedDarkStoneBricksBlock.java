package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunicCarvedDarkStoneBricksBlock extends BasicBlock {

	public RunicCarvedDarkStoneBricksBlock(String name, Properties properties) {
		super(name, properties);
	}
	
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return true;
		} else {
			if (stack.getItem() == ModItems.arcane_crystal && !worldIn.isRemote) {
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
				worldIn.setBlockState(pos, ModBlocks.white_runic_carved_dark_stone_bricks.getDefaultState());
				return true;
			} else {
				return false;
			}
		}
	}

}
