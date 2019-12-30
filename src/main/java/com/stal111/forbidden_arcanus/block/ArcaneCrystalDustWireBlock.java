package com.stal111.forbidden_arcanus.block;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ArcaneCrystalDustWireBlock extends Block {

	public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.REDSTONE_NORTH;
	public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.REDSTONE_EAST;
	public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.REDSTONE_SOUTH;
	public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.REDSTONE_WEST;

	public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap
			.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));

	public ArcaneCrystalDustWireBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(ModUtils.location(name));
		this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, RedstoneSide.NONE).with(EAST, RedstoneSide.NONE).with(SOUTH, RedstoneSide.NONE).with(WEST, RedstoneSide.NONE));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		return this.getDefaultState().with(WEST, this.getSide(iblockreader, blockpos, Direction.WEST))
				.with(EAST, this.getSide(iblockreader, blockpos, Direction.EAST))
				.with(NORTH, this.getSide(iblockreader, blockpos, Direction.NORTH))
				.with(SOUTH, this.getSide(iblockreader, blockpos, Direction.SOUTH));
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		if (facing == Direction.DOWN) {
			return stateIn;
		} else {
			return facing == Direction.UP
					? stateIn.with(WEST, this.getSide(worldIn, currentPos, Direction.WEST))
							.with(EAST, this.getSide(worldIn, currentPos, Direction.EAST))
							.with(NORTH, this.getSide(worldIn, currentPos, Direction.NORTH))
							.with(SOUTH, this.getSide(worldIn, currentPos, Direction.SOUTH))
					: stateIn.with(FACING_PROPERTY_MAP.get(facing), this.getSide(worldIn, currentPos, facing));
		}
	}

	@Override
	public void updateDiagonalNeighbors(BlockState state, IWorld worldIn, BlockPos pos, int flags) {
		try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				RedstoneSide redstoneside = state.get(FACING_PROPERTY_MAP.get(direction));
				if (redstoneside != RedstoneSide.NONE && worldIn
						.getBlockState(blockpos$pooledmutableblockpos.setPos(pos).move(direction)).getBlock() != this) {
					blockpos$pooledmutableblockpos.move(Direction.DOWN);
					BlockState blockstate = worldIn.getBlockState(blockpos$pooledmutableblockpos);
					if (blockstate.getBlock() != Blocks.OBSERVER) {
						BlockPos blockpos = blockpos$pooledmutableblockpos.offset(direction.getOpposite());
						BlockState blockstate1 = blockstate.updatePostPlacement(direction.getOpposite(),
								worldIn.getBlockState(blockpos), worldIn, blockpos$pooledmutableblockpos, blockpos);
						replaceBlock(blockstate, blockstate1, worldIn, blockpos$pooledmutableblockpos, flags);
					}

					blockpos$pooledmutableblockpos.setPos(pos).move(direction).move(Direction.UP);
					BlockState blockstate3 = worldIn.getBlockState(blockpos$pooledmutableblockpos);
					if (blockstate3.getBlock() != Blocks.OBSERVER) {
						BlockPos blockpos1 = blockpos$pooledmutableblockpos.offset(direction.getOpposite());
						BlockState blockstate2 = blockstate3.updatePostPlacement(direction.getOpposite(),
								worldIn.getBlockState(blockpos1), worldIn, blockpos$pooledmutableblockpos, blockpos1);
						replaceBlock(blockstate3, blockstate2, worldIn, blockpos$pooledmutableblockpos, flags);
					}
				}
			}
		}

	}

	private RedstoneSide getSide(IBlockReader worldIn, BlockPos pos, Direction face) {
		BlockPos blockpos = pos.offset(face);
		BlockState blockstate = worldIn.getBlockState(blockpos);
		BlockPos blockpos1 = pos.up();
		BlockState blockstate1 = worldIn.getBlockState(blockpos1);
		if (!blockstate1.isNormalCube(worldIn, blockpos1)) {
			boolean flag = Block.hasSolidSide(blockstate, worldIn, blockpos, Direction.UP)
					|| blockstate.getBlock() == Blocks.HOPPER;
			if (flag && canConnectTo(worldIn.getBlockState(blockpos.up()), worldIn, blockpos.up(), null)) {
				if (isOpaque(blockstate.getCollisionShape(worldIn, blockpos))) {
					return RedstoneSide.UP;
				}

				return RedstoneSide.SIDE;
			}
		}

		return !canConnectTo(blockstate, worldIn, blockpos, face) && (blockstate.isNormalCube(worldIn, blockpos)
				|| !canConnectTo(worldIn.getBlockState(blockpos.down()), worldIn, blockpos.down(), null))
						? RedstoneSide.NONE
						: RedstoneSide.SIDE;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.down();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return Block.hasSolidSide(blockstate, worldIn, blockpos, Direction.UP)
				|| blockstate.getBlock() == Blocks.HOPPER;
	}

	private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos).getBlock() == this) {
			worldIn.notifyNeighborsOfStateChange(pos, this);

			for (Direction direction : Direction.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
			}

		}
	}

	public void onBlockAdded(BlockState p_220082_1_, World worldIn, BlockPos pos, BlockState p_220082_4_,
			boolean p_220082_5_) {
		if (p_220082_4_.getBlock() != p_220082_1_.getBlock() && !worldIn.isRemote) {

			for (Direction direction : Direction.Plane.VERTICAL) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
			}

			for (Direction direction1 : Direction.Plane.HORIZONTAL) {
				this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(direction1));
			}

			for (Direction direction2 : Direction.Plane.HORIZONTAL) {
				BlockPos blockpos = pos.offset(direction2);
				if (worldIn.getBlockState(blockpos).isNormalCube(worldIn, blockpos)) {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
				} else {
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}

		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!isMoving && state.getBlock() != newState.getBlock()) {
			super.onReplaced(state, worldIn, pos, newState, isMoving);
			if (!worldIn.isRemote) {
				for (Direction direction : Direction.values()) {
					worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
				}

				for (Direction direction1 : Direction.Plane.HORIZONTAL) {
					this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(direction1));
				}

				for (Direction direction2 : Direction.Plane.HORIZONTAL) {
					BlockPos blockpos = pos.offset(direction2);
					if (worldIn.getBlockState(blockpos).isNormalCube(worldIn, blockpos)) {
						this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
					} else {
						this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
					}
				}

			}
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean p_220069_6_) {
		if (!worldIn.isRemote) {
			if (state.isValidPosition(worldIn, pos)) {
			} else {
				spawnDrops(state, worldIn, pos);
				worldIn.removeBlock(pos, false);
			}

		}
	}

	protected static boolean canConnectTo(BlockState blockState, IBlockReader world, BlockPos pos,
			@Nullable Direction side) {
		Block block = blockState.getBlock();
		//if (block == ModBlocks.arcane_crystal_dust_wire) {
		//	return true;
		//} else {
			return blockState.canConnectRedstone(world, pos, side) && side != null;
		//}
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH))
					.with(WEST, state.get(EAST));
		case COUNTERCLOCKWISE_90:
			return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST))
					.with(WEST, state.get(NORTH));
		case CLOCKWISE_90:
			return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST))
					.with(WEST, state.get(SOUTH));
		default:
			return state;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
		case FRONT_BACK:
			return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
		default:
			return super.mirror(state, mirrorIn);
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST);
	}

}
