package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BottleBlock extends FallingWaterloggedBlock {

	private static final VoxelShape[] SHAPE = { 
			Block.makeCuboidShape(2, 0, 2, 14, 14, 14),
			Block.makeCuboidShape(5, 14, 5, 11, 15, 11),
			Block.makeCuboidShape(6, 15, 6, 10, 16, 10)};

	public BottleBlock(Properties properties) {
		super(properties.hardnessAndResistance(0.5F, 0.5F));
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
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty()) {
			return ActionResultType.SUCCESS;
		} else {
			if (this != ModBlocks.BOTTLE_BLOCK.getBlock()) {
				return ActionResultType.SUCCESS;
			} else {
				boolean flag = state.get(WATERLOGGED);
				if (stack.getItem() == ModItems.PIXIE.get() && !world.isRemote) {
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
					world.setBlockState(pos, ModBlocks.PIXIE_IN_A_BOTTLE_BLOCK.getState().with(WATERLOGGED, flag));
					return ActionResultType.SUCCESS;
				} else if (stack.getItem() == ModItems.CORRUPTED_PIXIE.get() && !world.isRemote) {
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
					world.setBlockState(pos, ModBlocks.CORRUPTED_PIXIE_IN_A_BOTTLE_BLOCK.getState().with(WATERLOGGED, flag));
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.getBlock() == ModBlocks.PIXIE_IN_A_BOTTLE_BLOCK.getBlock()) {
			final double d0 = pos.getX() + random.nextFloat();
			final double d2 = pos.getY() + random.nextFloat();
			final double d3 = pos.getZ() + random.nextFloat();
			final double d4 = (random.nextFloat() - 0.5) * 0.0999999985098839;
			final double d5 = (random.nextFloat() - 0.5) * 0.0999999985098839;
			final double d6 = (random.nextFloat() - 0.5) * 0.0999999985098839;
			world.addParticle(ParticleTypes.END_ROD, d0, d2, d3, d4, d5, d6);
		}
	}
}
