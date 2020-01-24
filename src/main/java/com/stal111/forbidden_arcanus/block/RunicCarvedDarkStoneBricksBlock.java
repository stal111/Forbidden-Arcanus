package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class RunicCarvedDarkStoneBricksBlock extends Block {

	public RunicCarvedDarkStoneBricksBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return ActionResultType.SUCCESS;
		} else {
			if (stack.getItem() == ModItems.ARCANE_CRYSTAL.getItem() && !world.isRemote) {
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
				//world.setBlockState(pos, ModBlocks.white_runic_carved_dark_stone_bricks.getDefaultState());
				return ActionResultType.SUCCESS;
			}
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}
}
