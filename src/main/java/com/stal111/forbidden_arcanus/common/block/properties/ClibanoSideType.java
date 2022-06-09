package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * Clibano Side Type <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.ClibanoSideType
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public enum ClibanoSideType implements StringRepresentable {
    OFF("off"),
    FIRE("fire"),
    BLUE_FIRE("blue_fire");;

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
