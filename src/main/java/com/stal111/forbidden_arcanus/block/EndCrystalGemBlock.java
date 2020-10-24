package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

public class EndCrystalGemBlock extends CutoutBlock {

    public EndCrystalGemBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
