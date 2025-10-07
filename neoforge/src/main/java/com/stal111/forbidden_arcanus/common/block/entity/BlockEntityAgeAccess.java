package com.stal111.forbidden_arcanus.common.block.entity;

public interface BlockEntityAgeAccess {
    int getAgeInTicks();

    default float getAgeInTicks(float partialTick) {
        return getAgeInTicks() + partialTick;
    }
}
