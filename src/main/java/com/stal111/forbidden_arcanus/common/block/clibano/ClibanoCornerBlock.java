package com.stal111.forbidden_arcanus.common.block.clibano;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

/**
 * Clibano Corner Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.clibano.ClibanoCornerBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public class ClibanoCornerBlock extends AbstractClibanoFrameBlock {

    public static final MapCodec<ClibanoCornerBlock> CODEC = simpleCodec(ClibanoCornerBlock::new);

    private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

    public ClibanoCornerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getFacingProperty(), Direction.NORTH).setValue(BOTTOM, true));
    }

    @Override
    public DirectionProperty getFacingProperty() {
        return BlockStateProperties.HORIZONTAL_FACING;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(this.getFacingProperty(), context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getFacingProperty(), BOTTOM);
    }
}
