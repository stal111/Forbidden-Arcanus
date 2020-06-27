package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.util.IStringSerializable;

public enum CandelabraAttachment implements IStringSerializable {
    FLOOR("floor"),
    CEILING("ceiling"),
    SINGLE_WALL("wall");

    private final String name;

    CandelabraAttachment(String name) {
        this.name = name;
    }

    @Override
    public String func_176610_l() {
        return this.name;
    }
}
