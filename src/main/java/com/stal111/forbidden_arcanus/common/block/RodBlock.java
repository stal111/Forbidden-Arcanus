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
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<PillarType, Map<Direction.Axis, VoxelShape>> SHAPES = new HashMap<>();

    public RodBlock(Properties properties) {
        super(properties);
        this.buildShapes();
    }

    private void buildShapes() {
        this.addShapeWithRotations(SHAPE_PARTS[0], PillarType.MIDDLE);
        this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[0], SHAPE_PARTS[2]), PillarType.TOP);
        this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[0], SHAPE_PARTS[1]), PillarType.BOTTOM);
        this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS), PillarType.SINGLE);
    }

    private void addShapeWithRotations(VoxelShape shape, PillarType pillarType) {
        Map<Direction.Axis, VoxelShape> rotatedShapes = new HashMap<>();
        rotatedShapes.put(Direction.Axis.Y, shape);
        rotatedShapes.put(Direction.Axis.X, VoxelShapeHelper.rotate(shape, Direction.Axis.X));
        rotatedShapes.put(Direction.Axis.Z, VoxelShapeHelper.rotate(shape, Direction.Axis.Z));

        SHAPES.put(pillarType, rotatedShapes);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPES.get(state.getValue(TYPE)).get(state.getValue(AXIS));
    }
}
