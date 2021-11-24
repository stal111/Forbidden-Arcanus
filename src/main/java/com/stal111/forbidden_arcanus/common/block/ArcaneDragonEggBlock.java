package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;

/**
 * Arcane Dragon Egg Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ArcaneDragonEggBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-23
 */
public class ArcaneDragonEggBlock extends FallingBlock {

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(3, 1, 3, 13, 13, 13),
            Block.box(2, 3, 2, 14, 11, 14),
            Block.box(5, 15, 5, 11, 16, 11)
    );

    public ArcaneDragonEggBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected int getDelayAfterPlace() {
        return 5;
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }
}
