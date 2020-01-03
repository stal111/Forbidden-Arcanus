package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.util.IStringSerializable;

public enum ConnectedBlockType implements IStringSerializable {

    SINGLE("single"),
    TOP("top"),
    CENTER("center"),
    BOTTOM("bottom");

    private final String name;

    ConnectedBlockType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
