package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.PillarType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Objects;

/**
 * Pillar Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.PillarBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-26
 */
public class PillarBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<PillarType> TYPE = ModBlockStateProperties.PILLAR_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape[] SHAPE_PARTS = {
            Block.box(0, 14, 0, 16, 16, 16),
            Block.box(1, 13, 1, 15, 14, 15),
            Block.box(2, 0, 2, 14, 16, 14),
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(1, 2, 1, 15, 3, 15)
    };

    private final EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> shapes;

    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(TYPE, PillarType.SINGLE).setValue(AXIS, Direction.Axis.Y).setValue(WATERLOGGED, false));
        this.shapes = this.buildShapes();
    }

    private EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> buildShapes() {
        EnumMap<PillarType, EnumMap<Direction.Axis, VoxelShape>> map = new EnumMap<>(PillarType.class);

        map.put(PillarType.MIDDLE, this.addShapeWithRotations(SHAPE_PARTS[2]));
        map.put(PillarType.TOP, this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[0], SHAPE_PARTS[1], SHAPE_PARTS[2])));
        map.put(PillarType.BOTTOM, this.addShapeWithRotations(VoxelShapeHelper.combineAll(SHAPE_PARTS[3], SHAPE_PARTS[4], SHAPE_PARTS[2])));
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
        return this.shapes.get(state.getValue(TYPE)).get(state.getValue(AXIS));
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(WATERLOGGED, flag);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction direction, @Nonnull BlockState neighborState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction.getAxis() != state.getValue(AXIS)) {
            return state;
        }

        return state.setValue(TYPE, this.tryConnect(state, level, currentPos));
    }

    private PillarType tryConnect(BlockState state, LevelAccessor level, BlockPos pos) {
        Direction.Axis axis = state.getValue(AXIS);

        BlockState stateDown = level.getBlockState(pos.relative(Direction.get(Direction.AxisDirection.NEGATIVE, axis)));
        BlockState stateUp = level.getBlockState(pos.relative(Direction.get(Direction.AxisDirection.POSITIVE, axis)));

        boolean axisUpEqual = stateUp.is(this) && stateUp.getValue(AXIS) == axis;
        boolean axisDownEqual = stateDown.is(this) && stateDown.getValue(AXIS) == axis;

        return PillarType.getTypeForConnections(axisUpEqual, axisDownEqual);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
