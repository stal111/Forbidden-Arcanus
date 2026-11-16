package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFrameBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 05.11.2023
 */
public class CreateClibanoInteraction extends TransformPatternInteraction {

    public CreateClibanoInteraction(Predicate<BlockState> predicate, BlockPattern pattern) {
        super(predicate, pattern);
    }

    @Override
    protected void transform(TransformPatternContext context, BlockPattern.BlockPatternMatch match) {
        BlockPos centerPos = match.getBlock(1, 1, 1).getPos();
        BlockPos corePos = match.getBlock(1, 1, 2).getPos();
        Direction frontDirection = match.getForwards();
        Level level = context.level();

        // Corner blocks

        BlockPos bottomPos = corePos.below().relative(frontDirection.getClockWise());
        BlockState cornerState = ModBlocks.CLIBANO_CORNER.get().defaultBlockState();

        this.placeFrameBlock(level, centerPos, bottomPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection));
        this.placeFrameBlock(level, centerPos, bottomPos.relative(frontDirection.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getClockWise()));
        this.placeFrameBlock(level, centerPos, bottomPos.relative(frontDirection.getOpposite(), 2).relative(frontDirection.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getOpposite()));
        this.placeFrameBlock(level, centerPos, bottomPos.relative(frontDirection.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getCounterClockWise()));

        BlockPos topPos = corePos.above().relative(frontDirection.getClockWise());
        cornerState = cornerState.setValue(BlockStateProperties.BOTTOM, false);

        this.placeFrameBlock(level, centerPos, topPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection));
        this.placeFrameBlock(level, centerPos, topPos.relative(frontDirection.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getClockWise()));
        this.placeFrameBlock(level, centerPos, topPos.relative(frontDirection.getOpposite(), 2).relative(frontDirection.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getOpposite()));
        this.placeFrameBlock(level, centerPos, topPos.relative(frontDirection.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, frontDirection.getCounterClockWise()));

        // Center blocks & sides

        BlockState centerState = ModBlocks.CLIBANO_CENTER.get().defaultBlockState();
        BlockState horizontalSideState = ModBlocks.CLIBANO_SIDE_HORIZONTAL.get().defaultBlockState();
        BlockState verticalSideState = ModBlocks.CLIBANO_SIDE_VERTICAL.get().defaultBlockState();

        for (Direction direction : Direction.values()) {
            BlockPos relativePos = centerPos.relative(direction);

            this.placeFrameBlock(level, centerPos, relativePos, centerState
                    .setValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE, ClibanoCenterType.getFromDirection(direction, frontDirection))
                    .setValue(BlockStateProperties.FACING, direction));

            if (direction.getAxis() != Direction.Axis.Y) {
                this.placeFrameBlock(level, centerPos, relativePos.relative(direction.getClockWise()), horizontalSideState
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .setValue(ModBlockStateProperties.MIRRORED, direction == frontDirection.getCounterClockWise()));

                this.placeFrameBlock(level, centerPos, relativePos.relative(Direction.UP), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction));
                this.placeFrameBlock(level, centerPos, relativePos.relative(Direction.DOWN), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(ModBlockStateProperties.MIRRORED, true));
            }
        }

        // Main block
        level.setBlock(centerPos, ModBlocks.CLIBANO_MAIN_PART.get().defaultBlockState(), 2);

        if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(centerPos) instanceof ClibanoMainBlockEntity blockEntity) {
            blockEntity.setFrontDirection(frontDirection);

            for (Direction direction : Direction.values()) {
                if (serverLevel.getBlockEntity(centerPos.relative(direction)) instanceof ClibanoFrameBlockEntity clibanoBlockEntity) {
                    clibanoBlockEntity.setMainDirection(direction.getOpposite());
                }
            }
        }
    }

    private void placeFrameBlock(Level level, BlockPos centerPos, BlockPos pos, BlockState state) {
        BlockState oldState = level.getBlockState(pos);

        super.placeBlock(level, pos, state);

        if (level.getBlockEntity(pos) instanceof ClibanoFrameBlockEntity blockEntity) {
            blockEntity.setFrameData(new ClibanoFrameBlockEntity.FrameData(oldState, centerPos));
        }
    }
}
