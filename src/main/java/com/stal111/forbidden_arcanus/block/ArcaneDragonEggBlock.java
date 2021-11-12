package com.stal111.forbidden_arcanus.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

public class ArcaneDragonEggBlock extends FallingWaterloggedBlock {
	
	private static final VoxelShape[] SHAPE = { 
			Block.box(3, 0, 3, 13, 13, 13),
			Block.box(2, 1, 2, 14, 11, 14), 
			Block.box(1, 3, 1, 15, 8, 15),
			Block.box(5, 13, 5, 11, 15, 11),
			Block.box(6, 15, 6, 10, 16, 10)};

	public ArcaneDragonEggBlock(Properties properties) {
		super(properties);
	}
	
	private VoxelShape generateShape() {
		return VoxelShapeHelper.combineAll(SHAPE);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return this.generateShape();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return this.generateShape();
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		double d0 = pos.getX() + random.nextFloat();
		double d2 = pos.getY() + random.nextFloat();
		double d3 = pos.getZ() + random.nextFloat();
		double d4 = (random.nextFloat() - 0.5) * 0.1;
		double d5 = (random.nextFloat() - 0.5) * 0.1;
		double d6 = (random.nextFloat() - 0.5) * 0.1;
		world.addParticle(ParticleTypes.END_ROD, d0, d2, d3, d4, d5, d6);
	}
}
