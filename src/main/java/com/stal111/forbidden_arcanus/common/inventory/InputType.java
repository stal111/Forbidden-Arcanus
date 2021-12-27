package com.stal111.forbidden_arcanus.common.inventory;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * Input Type
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.InputType
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-02
 */
public enum InputType implements StringRepresentable {
    AUREAL("aureal"),
    SOULS("souls"),
    BLOOD("blood"),
    EXPERIENCE("experience");

    private final String name;

    InputType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
