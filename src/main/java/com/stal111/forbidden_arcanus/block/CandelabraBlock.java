package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandelabraBlock extends AbstractCandelabraBlock {

    public static final IntegerProperty CANDLES_0_3 = ModBlockStateProperties.CANDLES_0_3;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty X = BooleanProperty.create("x");

    private static final VoxelShape BASE_SHAPES = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
            Block.makeCuboidShape(6.5D, 1.0D, 6.5D, 9.5D, 4.0D, 9.5D));

    private static final List<VoxelShape> SHAPES = Arrays.asList(
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D)),
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D),

                    Block.makeCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(4.0D, 5.0D, 7.0D, 12.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(4.0D, 7.0D, 7.0D, 6.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(10.0D, 7.0D, 7.0D, 12.0D, 10.0D, 9.0D),
                    Block.makeCuboidShape(3.5D, 10.0D, 6.5D, 6.5D, 12.0D, 9.5D),
                    Block.makeCuboidShape(9.5D, 10.0D, 6.5D, 12.5D, 12.0D, 9.5D),

                    Block.makeCuboidShape(4.0D, 12.0D, 7.0D, 6.0D, 16.0D, 9.0D),
                    Block.makeCuboidShape(10.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 11.0D, 9.0D),
                    Block.makeCuboidShape(6.5D, 11.0D, 6.5D, 9.5D, 13.0D, 9.5D),
                    Block.makeCuboidShape(2.0D, 5.0D, 7.0D, 14.0D, 7.0D, 9.0D),
                    Block.makeCuboidShape(2.0D, 7.0D, 7.0D, 4.0D, 9.0D, 9.0D),
                    Block.makeCuboidShape(12.0D, 7.0D, 7.0D, 14.0D, 9.0D, 9.0D),
                    Block.makeCuboidShape(1.5D, 9.0D, 6.5D, 4.5D, 11.0D, 9.5D),
                    Block.makeCuboidShape(11.5D, 9.0D, 6.5D, 14.5D, 11.0D, 9.5D),

                    Block.makeCuboidShape(2.0D, 11.0D, 7.0D, 4.0D, 14.0D, 9.0D),
                    Block.makeCuboidShape(12.0D, 11.0D, 7.0D, 14.0D, 14.0D, 9.0D),
                    Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D)
            ));

    public CandelabraBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES_0_3, 0).with(LIT, true).with(X, true).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShapeForState(BlockState state) {
        VoxelShape shape = SHAPES.get(getCurrentCandles(state));

        if (!state.get(X) && getCurrentCandles(state) > 0) {
            shape = VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.NINETY);
        }

        return VoxelShapeHelper.combineAll(shape, BASE_SHAPES);
    }

    @Override
    public int maxCandles() {
        return 3;
    }

    @Override
    public int getCurrentCandles(BlockState state) {
        return state.get(CANDLES_0_3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return (hasEnoughSolidSide(world, pos.down(), Direction.UP) && (world.getBlockState(pos.up()).getBlock() instanceof AirBlock || world.getFluidState(pos.up()).getFluid() instanceof WaterFluid) && !ModTags.Blocks.CANDELABRAS.contains(world.getBlockState(pos.down()).getBlock()));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        BlockState state = this.getDefaultState().with(X, context.getPlacementHorizontalFacing() == Direction.NORTH || context.getPlacementHorizontalFacing() == Direction.SOUTH).with(WATERLOGGED, flag).with(LIT, !flag);
        if (state.isValidPosition(context.getWorld(), context.getPos())) {
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES_0_3) != 0) {
            if (state.get(X)) {
                if (state.get(CANDLES_0_3) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.get(CANDLES_0_3) == 2) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.31D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.7D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.165D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.85D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (state.get(CANDLES_0_3) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.get(CANDLES_0_3) == 2) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.31D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.7D, 0.0D, 0.0D, 0.0D);
                } else {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.165D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.85D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLES_0_3, LIT, X, WATERLOGGED);
    }
}
