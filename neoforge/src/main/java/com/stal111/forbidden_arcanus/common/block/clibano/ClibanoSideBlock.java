package com.stal111.forbidden_arcanus.common.block.clibano;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * Clibano Horizontal Side Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.clibano.ClibanoHorizontalSideBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public class ClibanoSideBlock extends AbstractClibanoFrameBlock {

    public static final MapCodec<ClibanoSideBlock> CODEC = simpleCodec(ClibanoSideBlock::new);

    private static final EnumProperty<ClibanoSideType> TYPE = ModBlockStateProperties.CLIBANO_SIDE_TYPE;
    private static final BooleanProperty MIRRORED = ModBlockStateProperties.MIRRORED;

    public ClibanoSideBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(this.getFacingProperty(), Direction.NORTH)
                .setValue(TYPE, ClibanoSideType.OFF)
                .setValue(MIRRORED, false)
        );
    }

    @Override
    public DirectionProperty getFacingProperty() {
        return BlockStateProperties.HORIZONTAL_FACING;
    }

    @Override
    public BlockState updateAppearance(BlockState state, boolean isLit, ClibanoFireType fireType) {
        return state.setValue(TYPE, isLit ? ClibanoSideType.valueOf(fireType.name()) : ClibanoSideType.OFF);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.getFacingProperty(), TYPE, MIRRORED);
    }
}
