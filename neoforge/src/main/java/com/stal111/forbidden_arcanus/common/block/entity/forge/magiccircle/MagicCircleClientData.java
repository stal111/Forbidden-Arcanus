package com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import org.jspecify.annotations.Nullable;

public class MagicCircleClientData implements BlockEntityAgeAccess {
    private @Nullable MagicCircleType magicCircleType;
    private int ageInTicks;

    public void setMagicCircleType(MagicCircleType type) {
        this.magicCircleType = type;
        this.ageInTicks = 0;
    }

    public void incrementAgeInTicks() {
        this.ageInTicks++;
    }

    public @Nullable MagicCircleType getMagicCircleType() {
        return this.magicCircleType;
    }

    @Override
    public int getAgeInTicks() {
        return this.ageInTicks;
    }
}
