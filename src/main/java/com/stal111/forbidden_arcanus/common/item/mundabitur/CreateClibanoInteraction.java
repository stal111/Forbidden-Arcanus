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

    private BlockPos centerPos;

    public CreateClibanoInteraction(Predicate<BlockState> predicate, BlockPattern pattern) {
        super(predicate, pattern);
    }

    @Override
    public void interact(TransformPatternContext context) {
        Direction clickedFace = context.clickedFace();
        BlockPos pos = context.pos();
        Level level = context.level();

        // Corner blocks

        BlockPos bottomPos = pos.below().relative(clickedFace.getClockWise());
        BlockState cornerState = ModBlocks.CLIBANO_CORNER.get().defaultBlockState();

        this.placeBlock(level, bottomPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace));
        this.placeBlock(level, bottomPos.relative(clickedFace.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getClockWise()));
        this.placeBlock(level, bottomPos.relative(clickedFace.getOpposite(), 2).relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getOpposite()));
        this.placeBlock(level, bottomPos.relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getCounterClockWise()));

        BlockPos topPos = pos.above().relative(clickedFace.getClockWise());
        cornerState = cornerState.setValue(BlockStateProperties.BOTTOM, false);

        this.placeBlock(level, topPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace));
        this.placeBlock(level, topPos.relative(clickedFace.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getClockWise()));
        this.placeBlock(level, topPos.relative(clickedFace.getOpposite(), 2).relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getOpposite()));
        this.placeBlock(level, topPos.relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getCounterClockWise()));

        // Center blocks & sides

        this.centerPos = pos.relative(clickedFace.getOpposite());
        BlockState centerState = ModBlocks.CLIBANO_CENTER.get().defaultBlockState();
        BlockState horizontalSideState = ModBlocks.CLIBANO_SIDE_HORIZONTAL.get().defaultBlockState();
        BlockState verticalSideState = ModBlocks.CLIBANO_SIDE_VERTICAL.get().defaultBlockState();

        for (Direction direction : Direction.values()) {
            BlockPos relativePos = this.centerPos.relative(direction);

            this.placeBlock(level, relativePos, centerState
                    .setValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE, ClibanoCenterType.getFromDirection(direction, clickedFace))
                    .setValue(BlockStateProperties.FACING, direction));

            if (direction.getAxis() != Direction.Axis.Y) {
                this.placeBlock(level, relativePos.relative(direction.getClockWise()), horizontalSideState
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .setValue(ModBlockStateProperties.MIRRORED, direction == clickedFace.getCounterClockWise()));

                this.placeBlock(level, relativePos.relative(Direction.UP), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction));
                this.placeBlock(level, relativePos.relative(Direction.DOWN), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(ModBlockStateProperties.MIRRORED, true));

            }
        }

        // Main block
        level.setBlock(this.centerPos, ModBlocks.CLIBANO_MAIN_PART.get().defaultBlockState(), 2);

        if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(this.centerPos) instanceof ClibanoMainBlockEntity blockEntity) {
            blockEntity.setFrontDirection(clickedFace);

            for (Direction direction : Direction.values()) {
                if (serverLevel.getBlockEntity(this.centerPos.relative(direction)) instanceof ClibanoFrameBlockEntity clibanoBlockEntity) {
                    clibanoBlockEntity.setMainDirection(direction.getOpposite());
                }
            }
        }
    }

    @Override
    public void placeBlock(Level level, BlockPos pos, BlockState state) {
        BlockState oldState = level.getBlockState(pos);

        super.placeBlock(level, pos, state);

        if (level.getBlockEntity(pos) instanceof ClibanoFrameBlockEntity blockEntity) {
            blockEntity.setFrameData(new ClibanoFrameBlockEntity.FrameData(oldState, this.centerPos));
        }
    }
}
