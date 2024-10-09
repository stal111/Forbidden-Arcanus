package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * Obelisk Part <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-26
 */
public enum ObeliskPart implements StringRepresentable {
    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    ObeliskPart(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
