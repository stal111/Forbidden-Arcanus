package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ClibanoFireType implements StringRepresentable {
    FIRE("fire", 13, 1.0D),
    SOUL_FIRE("soul_fire", 9, 1.5D),
    ENCHANTED_FIRE("enchanted_fire", 14, 2.5D);

    public static final StringRepresentable.EnumCodec<ClibanoFireType> CODEC = StringRepresentable.fromEnum(ClibanoFireType::values);

    private final String name;
    private final int lightLevel;
    private final double cookingSpeedMultiplier;

    ClibanoFireType(String name, int lightLevel, double cookingSpeedMultiplier) {
        this.name = name;
        this.lightLevel = lightLevel;
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return this.name;
    }

    public int getLightLevel() {
        return this.lightLevel;
    }

    public double getCookingSpeedMultiplier() {
        return this.cookingSpeedMultiplier;
    }
}
