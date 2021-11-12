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
 * @version 16.2.0
 * @since 2021-02-19
 */
public class FluidTankTile extends FluidTank {

    private final BlockEntity tileEntity;

    public FluidTankTile(int capacity, BlockEntity tileEntity) {
        super(capacity);
        this.tileEntity = tileEntity;
    }

    @Override
    protected void onContentsChanged() {
        BlockState state = tileEntity.getBlockState();
        Level world = tileEntity.getLevel();
        BlockPos pos = tileEntity.getBlockPos();

        world.sendBlockUpdated(pos, state, state, 8);
        world.getLightEngine().checkBlock(pos);

        tileEntity.setChanged();
    }
}
