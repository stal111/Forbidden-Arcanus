package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BottleBlock extends FallingWaterloggedBlock {

	private static final VoxelShape[] SHAPE = { 
			Block.makeCuboidShape(2, 0, 2, 14, 14, 14),
			Block.makeCuboidShape(5, 14, 5, 11, 15, 11), 
			Block.makeCuboidShape(6, 15, 6, 10, 16, 10)};

	public BottleBlock(String name, Properties properties) {
		super(name, properties.hardnessAndResistance(0.5F, 0.5F));
	}

	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.generateShape();
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
		return this.generateShape();
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return true;
		} else {
			if (this != ModBlocks.bottle_block) {
				return true;
			} else {
				boolean flag = state.get(WATERLOGGED);
				if (stack.getItem() == ModItems.pixi && !worldIn.isRemote) {
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
					worldIn.setBlockState(pos, ModBlocks.pixi_in_a_bottle_block.getStateContainer().getBaseState()
							.with(WATERLOGGED, Boolean.valueOf(flag)));
					return true;
				} else if (stack.getItem() == ModItems.corrupt_pixi && !worldIn.isRemote) {
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
					worldIn.setBlockState(pos, ModBlocks.corrupt_pixi_in_a_bottle_block.getStateContainer()
							.getBaseState().with(WATERLOGGED, Boolean.valueOf(flag)));
					return true;
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public int getLightValue(IBlockState state) {
		if (this == ModBlocks.pixi_in_a_bottle_block) {
			return 15;
		} else if (this == ModBlocks.corrupt_pixi_in_a_bottle_block) {
			return 7;
		} else {
			return 0;
		}
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
		if (state.getBlock() == ModBlocks.pixi_in_a_bottle_block) {
			return ModItems.pixi;
		} else if (state.getBlock() == ModBlocks.corrupt_pixi_in_a_bottle_block) {
			return ModItems.corrupt_pixi;
		} else {
			return null;
		}
	}

	@Override
	public boolean canSilkHarvest(IBlockState state, IWorldReader world, BlockPos pos, EntityPlayer player) {
		return true;
	}

}
