package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.util.IStringSerializable;

public enum ArcaneCrystalObeliskPart implements IStringSerializable {
    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    ArcaneCrystalObeliskPart(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return this.name;
    }
}
