package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/**
 * No Fluid Overlay Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.NoFluidOverlayBlock
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-11-15
 */
public class NoFluidOverlayBlock extends Block {

    public NoFluidOverlayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
