package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

/**
 * <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType
 *
 * @author stal111
 * @since 2022-06-02
 */
public enum ClibanoFireType implements StringRepresentable {
    FIRE("fire", 1.0D),
    BLUE_FIRE("blue_fire", 1.5D),
    PURPLE_FIRE("purple_fire", 2.5D);

    private final String name;
    private final double cookingSpeedMultiplier;

    ClibanoFireType(String name, double cookingSpeedMultiplier) {
        this.name = name;
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return this.name;
    }

    public double getCookingSpeedMultiplier() {
        return this.cookingSpeedMultiplier;
    }

    public static Optional<ClibanoFireType> byName(String name) {
        return Arrays.stream(values()).filter(fireType -> fireType.getSerializedName().equals(name)).findFirst();
    }
}
