package com.stal111.forbidden_arcanus.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndCrystalGemBlock extends CutoutBlock {

    public EndCrystalGemBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidState) {
        return true;
    }
}
