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
    FIRE("fire"),
    BLUE_FIRE("blue_fire"),
    PURPLE_FIRE("purple_fire");

    private final String name;

    ClibanoFireType(String name) {
        this.name = name;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return this.name;
    }

    public static Optional<ClibanoFireType> byName(String name) {
        return Arrays.stream(values()).filter(fireType -> fireType.getSerializedName().equals(name)).findFirst();
    }
}
