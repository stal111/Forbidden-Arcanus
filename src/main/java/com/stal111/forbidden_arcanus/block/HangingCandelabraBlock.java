package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

public class HangingCandelabraBlock extends AbstractCandelabraBlock {

    public static final BooleanProperty CANDLE = ModBlockStateProperties.CANDLE;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.box(6.0D, 12.0D, 6.0D, 10.0D, 13.0D, 10.0D),
            Block.box(5.0D, 3.0D, 5.0D, 11.0D, 12.0D, 11.0D));

    public HangingCandelabraBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CANDLE, false).setValue(LIT, true).setValue(WATERLOGGED, false));
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
        return state.getValue(CANDLE) ? 1 : 0;
    }

    @Override
    public String getDescriptionId() {
        return this.asItem().getDescriptionId();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(LIT, !flag);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CANDLE, LIT, WATERLOGGED);
    }
}
