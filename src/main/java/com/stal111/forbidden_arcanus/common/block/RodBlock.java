package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.PillarType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

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

        map.put(PillarType.MIDDLE, this.addShapeWithRotations(SHAPE_PARTS[0]));
        map.put(PillarType.TOP, this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[0], SHAPE_PARTS[2])));
        map.put(PillarType.BOTTOM, this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[0], SHAPE_PARTS[1])));
        map.put(PillarType.SINGLE, this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS)));

        return map;
    }

    private EnumMap<Direction.Axis, VoxelShape> addShapeWithRotations(VoxelShape shape) {
        EnumMap<Direction.Axis, VoxelShape> rotatedShapes = new EnumMap<>(Direction.Axis.class);
        rotatedShapes.put(Direction.Axis.Y, shape);
        rotatedShapes.put(Direction.Axis.X, VoxelShapeHelper.rotate(shape, Direction.Axis.X));
        rotatedShapes.put(Direction.Axis.Z, VoxelShapeHelper.rotate(shape, Direction.Axis.Z));

        return rotatedShapes;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return shapes.get(state.getValue(TYPE)).get(state.getValue(AXIS));
    }
}
