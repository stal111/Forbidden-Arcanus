package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CandelabraBlock extends AbstractCandelabraBlock {

    public static final IntegerProperty CANDLES_0_3 = ModBlockStateProperties.CANDLES_0_3;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty X = BooleanProperty.create("x");

    private static final VoxelShape BASE_SHAPES = VoxelShapeHelper.combineAll(
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
            Block.box(6.5D, 1.0D, 6.5D, 9.5D, 4.0D, 9.5D));

    private static final List<VoxelShape> SHAPES = Arrays.asList(
            VoxelShapeHelper.combineAll(
                    Block.box(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.box(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D)),
            VoxelShapeHelper.combineAll(
                    Block.box(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
                    Block.box(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D),

                    Block.box(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    Block.box(7.0D, 4.0D, 7.0D, 9.0D, 7.0D, 9.0D),
                    Block.box(4.0D, 5.0D, 7.0D, 12.0D, 7.0D, 9.0D),
                    Block.box(4.0D, 7.0D, 7.0D, 6.0D, 10.0D, 9.0D),
                    Block.box(10.0D, 7.0D, 7.0D, 12.0D, 10.0D, 9.0D),
                    Block.box(3.5D, 10.0D, 6.5D, 6.5D, 12.0D, 9.5D),
                    Block.box(9.5D, 10.0D, 6.5D, 12.5D, 12.0D, 9.5D),

                    Block.box(4.0D, 12.0D, 7.0D, 6.0D, 16.0D, 9.0D),
                    Block.box(10.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D)),
            VoxelShapeHelper.combineAll(
                    Block.box(7.0D, 4.0D, 7.0D, 9.0D, 11.0D, 9.0D),
                    Block.box(6.5D, 11.0D, 6.5D, 9.5D, 13.0D, 9.5D),
                    Block.box(2.0D, 5.0D, 7.0D, 14.0D, 7.0D, 9.0D),
                    Block.box(2.0D, 7.0D, 7.0D, 4.0D, 9.0D, 9.0D),
                    Block.box(12.0D, 7.0D, 7.0D, 14.0D, 9.0D, 9.0D),
                    Block.box(1.5D, 9.0D, 6.5D, 4.5D, 11.0D, 9.5D),
                    Block.box(11.5D, 9.0D, 6.5D, 14.5D, 11.0D, 9.5D),

                    Block.box(2.0D, 11.0D, 7.0D, 4.0D, 14.0D, 9.0D),
                    Block.box(12.0D, 11.0D, 7.0D, 14.0D, 14.0D, 9.0D),
                    Block.box(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D)
            ));

    public CandelabraBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CANDLES_0_3, 0).setValue(LIT, true).setValue(X, true).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShapeForState(BlockState state) {
        VoxelShape shape = SHAPES.get(getCurrentCandles(state));

        if (!state.getValue(X) && getCurrentCandles(state) > 0) {
            shape = VoxelShapeHelper.rotateShape(shape, Direction.EAST);
        }

        return VoxelShapeHelper.combineAll(shape, BASE_SHAPES);
    }

    @Override
    public int maxCandles() {
        return 3;
    }

    @Override
    public int getCurrentCandles(BlockState state) {
        return state.getValue(CANDLES_0_3);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return (canSupportCenter(world, pos.below(), Direction.UP) && (world.getBlockState(pos.above()).getBlock() instanceof AirBlock || world.getFluidState(pos.above()).getType() instanceof WaterFluid) && !ModTags.Blocks.CANDELABRAS.contains(world.getBlockState(pos.below()).getBlock()));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        BlockState state = this.defaultBlockState().setValue(X, context.getHorizontalDirection() == Direction.NORTH || context.getHorizontalDirection() == Direction.SOUTH).setValue(WATERLOGGED, flag).setValue(LIT, !flag);
        if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (state.getValue(LIT) && !state.getValue(WATERLOGGED) && state.getValue(CANDLES_0_3) != 0) {
            if (state.getValue(X)) {
                if (state.getValue(CANDLES_0_3) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.getValue(CANDLES_0_3) == 2) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.31D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.7D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.165D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.85D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                }
            } else {
                if (state.getValue(CANDLES_0_3) == 1) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                } else if (state.getValue(CANDLES_0_3) == 2) {
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
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES_0_3, LIT, X, WATERLOGGED);
    }
}
