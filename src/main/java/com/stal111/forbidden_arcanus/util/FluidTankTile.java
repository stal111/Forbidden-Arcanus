package com.stal111.forbidden_arcanus.util;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    private final TileEntity tileEntity;

    public FluidTankTile(int capacity, TileEntity tileEntity) {
        super(capacity);
        this.tileEntity = tileEntity;
    }

    @Override
    protected void onContentsChanged() {
        BlockState state = tileEntity.getBlockState();
        World world = tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();

        world.notifyBlockUpdate(pos, state, state, 8);
        world.getLightManager().checkBlock(pos);

        tileEntity.markDirty();
    }
}
