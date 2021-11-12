package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.util.StringRepresentable;

public enum ArcaneCrystalObeliskPart implements StringRepresentable {
    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    ArcaneCrystalObeliskPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
