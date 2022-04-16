package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

/**
 * Cherry Flower Vines Plant Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.CherryFlowerVinesPlantBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-15
 */
public class CherryFlowerVinesPlantBlock extends GrowingPlantBodyBlock implements CherryFlowerVines {

    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public CherryFlowerVinesPlantBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false);
    }

    @Nonnull
    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ModBlocks.CHERRY_FLOWER_VINES.get();
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState state, @Nonnull LevelReader level, @Nonnull BlockPos pos) {
        BlockState relativeState = level.getBlockState(pos.relative(this.growthDirection.getOpposite()));

        return relativeState.is(this.getHeadBlock()) || relativeState.is(this.getBodyBlock()) || relativeState.is(ModBlocks.CHERRYWOOD_LEAVES.get());    }

    @Override
    public void entityInside(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        CherryFlowerVines.entityInside(state, level, pos, entity);
    }
}
