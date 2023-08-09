package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.PillarType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import java.util.EnumMap;

/**
 * Rod Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.RodBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-12
 */
public class RodBlock extends PillarBlock {

    private static final VoxelShape[] SHAPE_PARTS = {
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(5, 14, 5, 11, 16, 11),
    };

    private final EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> shapes;

    public RodBlock(Properties properties) {
        super(properties);
        this.shapes = this.buildShapes();
    }

    private EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> buildShapes() {
        EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> map = new EnumMap<>(PillarType.class);

        map.put(PillarType.MIDDLE, VoxelShapeHelper.rotateAxis(SHAPE_PARTS[0]));
        map.put(PillarType.TOP, VoxelShapeHelper.rotateAxis(Shapes.or(SHAPE_PARTS[0], SHAPE_PARTS[2])));
        map.put(PillarType.BOTTOM, VoxelShapeHelper.rotateAxis(Shapes.or(SHAPE_PARTS[0], SHAPE_PARTS[1])));
        map.put(PillarType.SINGLE, VoxelShapeHelper.rotateAxis(VoxelShapeHelper.combineAll(SHAPE_PARTS)));

        return map;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return shapes.get(state.getValue(TYPE)).get(state.getValue(AXIS));
    }
}
