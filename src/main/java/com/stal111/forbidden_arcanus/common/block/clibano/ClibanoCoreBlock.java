package com.stal111.forbidden_arcanus.common.block.clibano;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

/**
 * Clibano Core Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.clibano.ClibanoCoreBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-21
 */
public class ClibanoCoreBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<ClibanoCoreBlock> CODEC = simpleCodec(ClibanoCoreBlock::new);

    public ClibanoCoreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
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
