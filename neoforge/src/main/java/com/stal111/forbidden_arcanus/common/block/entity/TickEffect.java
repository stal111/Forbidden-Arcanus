package com.stal111.forbidden_arcanus.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface TickEffect {
    void tick(Level level, BlockPos pos, BlockState state);
    int getTickInterval();
}
