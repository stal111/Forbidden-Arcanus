package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;

public record MagicCircleController(int eventId) {

    public void createMagicCircle(ServerLevel level, BlockPos pos, Holder<MagicCircleType> type) {
        int id = level.registryAccess().lookupOrThrow(FARegistries.MAGIC_CIRCLE).getId(type.value());

        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, id);
    }

    public void removeMagicCircle(ServerLevel level, BlockPos pos) {
        level.blockEvent(pos, level.getBlockState(pos).getBlock(), this.eventId, -1);
    }
}
