package com.stal111.forbidden_arcanus.common.block.entity.forge.tick;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.TickEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class UpdateBlockStateTickEffect implements TickEffect {

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof HephaestusForgeBlock block) {
            block.updateState(state, level, pos);
        }
    }

    @Override
    public int getTickInterval() {
        return 80;
    }
}
