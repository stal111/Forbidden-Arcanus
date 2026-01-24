package com.stal111.forbidden_arcanus.common.item.wand;

import net.minecraft.util.StringRepresentable;

public enum WandPart implements StringRepresentable {
    POMMEL("pommel"),
    TRANSITION("transition");

    public static final StringRepresentable.EnumCodec<WandPart> CODEC = StringRepresentable.fromEnum(WandPart::values);

    private final String name;

    WandPart(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
