package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-05-22
 */
public enum ClibanoSideType implements StringRepresentable {
    OFF("off"),
    FIRE("fire"),
    SOUL_FIRE("soul_fire"),
    ENCHANTED_FIRE("enchanted_fire");

    private final String name;

    ClibanoSideType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
