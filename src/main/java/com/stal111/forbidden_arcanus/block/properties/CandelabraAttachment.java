package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.util.StringRepresentable;

public enum CandelabraAttachment implements StringRepresentable {
    FLOOR("floor"),
    CEILING("ceiling"),
    SINGLE_WALL("wall");

    private final String name;

    CandelabraAttachment(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
