package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import java.util.Arrays;
import java.util.List;

public class WallCandelabraBlock extends AbstractCandelabraBlock {

    public static final IntegerProperty CANDLES_0_3 = ModBlockStateProperties.CANDLES_0_3;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty DIRECTION = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape BASE_SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 1.5D, 0.0D, 10.0D, 5.5D, 1.0D),
            Block.makeCuboidShape(7.0D, 2.5D, 1.0D, 9.0D, 4.5D, 5.5D),
            Block.makeCuboidShape(6.5D, 2.0D, 5.5D, 9.5D, 5.0D, 8.5D));

    private static final List<VoxelShape> SHAPES = Arrays.asList(
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.makeCuboidShape(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.makeCuboidShape(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D),

                    Block.makeCuboidShape(7.0D, 12.0D, 6.0D, 9.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 6.0D, 8.0D),
                    Block.makeCuboidShape(4.0D, 6.0D, 6.0D, 12.0D, 8.0D, 8.0D),
                    Block.makeCuboidShape(4.0D, 8.0D, 6.0D, 6.0D, 10.0D, 8.0D),
                    Block.makeCuboidShape(10.0D, 7.0D, 6.0D, 12.0D, 10.0D, 8.0D),

                    Block.makeCuboidShape(3.5D, 10.0D, 5.5D, 6.5D, 12.0D, 8.5D),
                    Block.makeCuboidShape(9.5D, 10.0D, 5.5D, 12.5D, 12.0D, 8.5D),

                    Block.makeCuboidShape(4.0D, 12.0D, 6.0D, 6.0D, 16.0D, 8.0D),
                    Block.makeCuboidShape(10.0D, 12.0D, 6.0D, 12.0D, 16.0D, 8.0D)),
            VoxelShapeHelper.combineAll(
                    BASE_SHAPE,
                    Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 11.0D, 8.0D),
                    Block.makeCuboidShape(2.0D, 6.0D, 6.0D, 14.0D, 8.0D, 8.0D),

                    Block.makeCuboidShape(2.0D, 8.0D, 6.0D, 4.0D, 9.0D, 8.0D),
                    Block.makeCuboidShape(12.0D, 7.0D, 6.0D, 14.0D, 9.0D, 8.0D),

                    Block.makeCuboidShape(1.5D, 9.0D, 5.5D, 4.5D, 11.0D, 8.5D),
                    Block.makeCuboidShape(6.5D, 11.0D, 5.5D, 9.5D, 13.0D, 8.5D),
                    Block.makeCuboidShape(11.5D, 9.0D, 5.5D, 14.5D, 11.0D, 8.5D),

                    Block.makeCuboidShape(2.0D, 11.0D, 6.0D, 4.0D, 14.0D, 8.0D),
                    Block.makeCuboidShape(7.0D, 13.0D, 6.0D, 9.0D, 16.0D, 8.0D),
                    Block.makeCuboidShape(12.0D, 11.0D, 6.0D, 14.0D, 14.0D, 8.0D)
            ));

    public WallCandelabraBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES_0_3, 0).with(LIT, true).with(DIRECTION, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShapeForState(BlockState state) {
        VoxelShape shape = SHAPES.get(getCurrentCandles(state));

        return VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.getRotationAmountFromDirection(state.get(DIRECTION).getOpposite()));
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
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        Direction direction = state.get(DIRECTION);
        return hasEnoughSolidSide(world, pos.offset(direction.getOpposite()), direction) && world.getBlockState(pos.up()).getBlock() instanceof AirBlock && !ModTags.Blocks.CANDELABRAS.contains(world.getBlockState(pos.down()).getBlock());
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        BlockState state = this.getDefaultState().with(WATERLOGGED, flag).with(LIT, !flag);
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                state = state.with(DIRECTION, direction1);
                break;
            }
        }

        if (state.isValidPosition(context.getWorld(), context.getPos())) {
            return state;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLES_0_3, LIT, DIRECTION, WATERLOGGED);
    }
}
