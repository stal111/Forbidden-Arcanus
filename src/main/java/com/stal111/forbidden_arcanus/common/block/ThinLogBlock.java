package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.Util;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Thin Log Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ThinLogBlock
 *
 * @author stal111
 * @version 1.18.1 - 2.1.0
 * @since 2021-12-31
 */
public class ThinLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final EnumMap<Direction.Axis, VoxelShape> SHAPES = VoxelShapeHelper.rotateAxis(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D));

    private static final VoxelShape CONNECT_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);

    private final Map<BlockState, VoxelShape> shapesCache;

    public static final EnumMap<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(new EnumMap<>(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public ThinLogBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AXIS, Direction.Axis.Y).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
        this.shapesCache = this.getShapeForEachState(this::calculateShape);

    }

    private VoxelShape calculateShape(BlockState state) {
        List<VoxelShape> connectedSides = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Direction.Axis axis = state.getValue(AXIS);

            if (axis == direction.getAxis()) {
                continue;
            }

            if (state.getValue(PROPERTY_BY_DIRECTION.get(getRotatedDirection(direction, axis)))) {
                connectedSides.add(VoxelShapeHelper.rotateShape(CONNECT_SHAPE, direction));
            }
        }

        return Shapes.or(SHAPES.get(state.getValue(AXIS)), connectedSides.toArray(new VoxelShape[0]));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
        BlockState state = Objects.requireNonNull(super.getStateForPlacement(context));
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        for (Direction direction : Direction.values()) {
            Direction.Axis axis = state.getValue(AXIS);

            if (axis == direction.getAxis()) {
                continue;
            }

            state = state.setValue(PROPERTY_BY_DIRECTION.get(getRotatedDirection(direction, axis)), this.shouldConnect(state, context.getLevel().getBlockState(context.getClickedPos().relative(direction))));
        }
        return state.setValue(WATERLOGGED, flag);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction direction, @Nonnull BlockState neighborState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos neighborPos) {
        Direction.Axis axis = state.getValue(AXIS);

        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction.getAxis() == axis) {
            return state;
        }

        return state.setValue(PROPERTY_BY_DIRECTION.get(getRotatedDirection(direction, axis)), this.shouldConnect(state, neighborState));
    }

    private boolean shouldConnect(BlockState state, BlockState neighborState) {
        return neighborState.getBlock() instanceof ThinLogBlock && state.getValue(AXIS) != neighborState.getValue(AXIS);
    }

    public static Direction getRotatedDirection(Direction direction, Direction.Axis axis) {
        if (axis == Direction.Axis.X) {
            return switch (direction) {
                case UP -> Direction.EAST;
                case DOWN -> Direction.WEST;
                default -> direction;
            };
        } else if (axis == Direction.Axis.Z) {
            return switch (direction) {
                case UP -> Direction.NORTH;
                case DOWN -> Direction.SOUTH;
                default -> direction;
            };
        }

        return direction;
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
