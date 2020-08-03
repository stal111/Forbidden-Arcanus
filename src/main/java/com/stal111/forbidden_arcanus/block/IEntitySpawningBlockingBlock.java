package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.SpawningBlockingMode;

public interface IEntitySpawningBlockingBlock {

    int getBlockRadius();
    SpawningBlockingMode getBlockingMode();
}
