package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class HangingCandelabraBlock extends AbstractCandelabraBlock {

    public static final BooleanProperty CANDLE = ModBlockStateProperties.CANDLE;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 12.0D, 6.0D, 10.0D, 13.0D, 10.0D),
            Block.makeCuboidShape(5.0D, 3.0D, 5.0D, 11.0D, 12.0D, 11.0D));

    public HangingCandelabraBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLE, false).with(LIT, true).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShapeForState(BlockState state) {
        return SHAPE;
    }

    @Override
    public int maxCandles() {
        return 1;
    }

    @Override
    public int getCurrentCandles(BlockState state) {
        return state.get(CANDLE) ? 1 : 0;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return Block.hasEnoughSolidSide(world, pos.up(), Direction.DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        return this.getDefaultState().with(WATERLOGGED, flag).with(LIT, !flag);
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANDLE, LIT, WATERLOGGED);
    }
}
