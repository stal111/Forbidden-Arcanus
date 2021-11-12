package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.LevelReader;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import java.util.Arrays;
import java.util.List;

public class WallCandelabraBlock extends AbstractCandelabraBlock {

    public static final IntegerProperty CANDLES_0_3 = ModBlockStateProperties.CANDLES_0_3;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty DIRECTION = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape BASE_SHAPE = VoxelShapeHelper.combineAll(
            Block.box(6.0D, 1.5D, 0.0D, 10.0D, 5.5D, 1.0D),
            Block.box(7.0D, 2.5D, 1.0D, 9.0D, 4.5D, 5.5D),
            Block.box(6.5D, 2.0D, 5.5D, 9.5D, 5.0D, 8.5D));

    private static final List<VoxelShape> SHAPES = Arrays.asList(
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.box(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.box(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.box(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.box(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D),

                    Block.box(7.0D, 12.0D, 6.0D, 9.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.box(7.0D, 5.0D, 6.0D, 9.0D, 6.0D, 8.0D),
                    Block.box(4.0D, 6.0D, 6.0D, 12.0D, 8.0D, 8.0D),
                    Block.box(4.0D, 8.0D, 6.0D, 6.0D, 10.0D, 8.0D),
                    Block.box(10.0D, 7.0D, 6.0D, 12.0D, 10.0D, 8.0D),

                    Block.box(3.5D, 10.0D, 5.5D, 6.5D, 12.0D, 8.5D),
                    Block.box(9.5D, 10.0D, 5.5D, 12.5D, 12.0D, 8.5D),

                    Block.box(4.0D, 12.0D, 6.0D, 6.0D, 16.0D, 8.0D),
                    Block.box(10.0D, 12.0D, 6.0D, 12.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.box(7.0D, 5.0D, 6.0D, 9.0D, 11.0D, 8.0D),
                    Block.box(2.0D, 6.0D, 6.0D, 14.0D, 8.0D, 8.0D),

                    Block.box(2.0D, 8.0D, 6.0D, 4.0D, 9.0D, 8.0D),
                    Block.box(12.0D, 7.0D, 6.0D, 14.0D, 9.0D, 8.0D),

                    Block.box(1.5D, 9.0D, 5.5D, 4.5D, 11.0D, 8.5D),
                    Block.box(6.5D, 11.0D, 5.5D, 9.5D, 13.0D, 8.5D),
                    Block.box(11.5D, 9.0D, 5.5D, 14.5D, 11.0D, 8.5D),

                    Block.box(2.0D, 11.0D, 6.0D, 4.0D, 14.0D, 8.0D),
                    Block.box(7.0D, 13.0D, 6.0D, 9.0D, 16.0D, 8.0D),
                    Block.box(12.0D, 11.0D, 6.0D, 14.0D, 14.0D, 8.0D)
            ));

    public WallCandelabraBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CANDLES_0_3, 0).setValue(LIT, true).setValue(DIRECTION, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShapeForState(BlockState state) {
        VoxelShape shape = SHAPES.get(getCurrentCandles(state));

        return VoxelShapeHelper.rotateShape(shape, state.getValue(DIRECTION));
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
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(DIRECTION);
        return canSupportCenter(world, pos.relative(direction.getOpposite()), direction) && world.getBlockState(pos.above()).getBlock() instanceof AirBlock && !ModTags.Blocks.CANDELABRAS.contains(world.getBlockState(pos.below()).getBlock());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        BlockState state = this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, !flag);
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                state = state.setValue(DIRECTION, direction1);
                break;
            }
        }

        if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLES_0_3, LIT, DIRECTION, WATERLOGGED);
    }
}
