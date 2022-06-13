package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

/**
 * Clibano Core Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ClibanoCoreBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-21
 */
public class ClibanoCoreBlock extends HorizontalDirectionalBlock {

    public ClibanoCoreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
