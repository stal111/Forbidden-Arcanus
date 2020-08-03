package com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IEntitySpawningBlockingBlocks {
    List<CompoundNBT> getSpawningBlockingBlocks();
    void addSpawningBlockingBlock(BlockPos pos, SpawningBlockingMode spawningBlockingMode);
    void removeSpawningBlockingBlock(BlockPos pos);
}
