package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BottleBlock extends FallingWaterloggedBlock {

	private static final VoxelShape[] SHAPE = { Block.makeCuboidShape(2, 0, 2, 14, 14, 14),
			Block.makeCuboidShape(5, 14, 5, 11, 15, 11), Block.makeCuboidShape(6, 15, 6, 10, 16, 10) };

	public BottleBlock(String name, Properties properties) {
		super(name, properties.hardnessAndResistance(0.5F, 0.5F));
	}

	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return this.generateShape();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return this.generateShape();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return true;
		} else {
			if (this != ModBlocks.bottle_block) {
				return true;
			} else {
				boolean flag = state.get(WATERLOGGED);
				if (stack.getItem() == ModItems.pixi && !world.isRemote) {
					if (!player.playerAbilities.isCreativeMode) {
						stack.shrink(1);
					}
					world.setBlockState(pos, ModBlocks.pixi_in_a_bottle_block.getStateContainer().getBaseState()
							.with(WATERLOGGED, Boolean.valueOf(flag)));
					return true;
				} else if (stack.getItem() == ModItems.corrupt_pixi && !world.isRemote) {
					if (!player.playerAbilities.isCreativeMode) {
						stack.shrink(1);
					}
					world.setBlockState(pos, ModBlocks.corrupt_pixi_in_a_bottle_block.getStateContainer().getBaseState()
							.with(WATERLOGGED, Boolean.valueOf(flag)));
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public int getLightValue(BlockState state) {
		if (this == ModBlocks.pixi_in_a_bottle_block) {
			return 15;
		} else if (this == ModBlocks.corrupt_pixi_in_a_bottle_block) {
			return 7;
		} else {
			return 0;
		}
	}
}
