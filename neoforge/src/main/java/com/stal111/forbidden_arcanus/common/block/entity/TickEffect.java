package com.stal111.forbidden_arcanus.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface TickEffect {
    void tick(ServerLevel level, BlockPos pos, BlockState state);
    int getTickInterval();
}
