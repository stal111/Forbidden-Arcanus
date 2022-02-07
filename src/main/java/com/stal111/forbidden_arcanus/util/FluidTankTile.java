package com.stal111.forbidden_arcanus.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.capability.templates.FluidTank;

/**
 * Fluid Tank Tile
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.FluidTankTile
 *
 * @author stal111
 * @version 1.18.1 - 2.1.0
 * @since 2021-02-19
 */
public class FluidTankTile extends FluidTank {

    private final BlockEntity blockEntity;

    public FluidTankTile(int capacity, BlockEntity blockEntity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged() {
        BlockState state = this.blockEntity.getBlockState();
        Level level = this.blockEntity.getLevel();
        BlockPos pos = this.blockEntity.getBlockPos();

        if (level != null) {
            level.sendBlockUpdated(pos, state, state, 8);
            level.getLightEngine().checkBlock(pos);
        }

        this.blockEntity.setChanged();
    }
}
