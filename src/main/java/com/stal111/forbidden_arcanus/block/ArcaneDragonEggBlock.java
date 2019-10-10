package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ArcaneDragonEggBlock extends FallingWaterloggedBlock {
	
	private static final VoxelShape[] SHAPE = { 
			Block.makeCuboidShape(3, 0, 3, 13, 13, 13),
			Block.makeCuboidShape(2, 1, 2, 14, 11, 14), 
			Block.makeCuboidShape(1, 3, 1, 15, 8, 15),
			Block.makeCuboidShape(5, 13, 5, 11, 15, 11),
			Block.makeCuboidShape(6, 15, 6, 10, 16, 10)};

	public ArcaneDragonEggBlock(Properties properties) {
		super(properties);
	}
	
	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos,
			ISelectionContext context) {
		return this.generateShape();
	}
	
	@Override
	public int getLightValue(BlockState state) {
		return 9;
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		double d0 = pos.getX() + random.nextFloat();
		double d2 = pos.getY() + random.nextFloat();
		double d3 = pos.getZ() + random.nextFloat();
		double d4 = (random.nextFloat() - 0.5) * 0.1;
		double d5 = (random.nextFloat() - 0.5) * 0.1;
		double d6 = (random.nextFloat() - 0.5) * 0.1;
		world.addParticle(ParticleTypes.END_ROD, d0, d2, d3, d4, d5, d6);
	}
}
