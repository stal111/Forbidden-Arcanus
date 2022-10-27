package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-05-22
 */
public enum ClibanoCenterType implements StringRepresentable {
    SIDE("side", 0),
    TOP("top", 0),
    FRONT_OFF("front_off", 0),
    FRONT_FIRE("front_fire", 13),
    FRONT_SOUL_FIRE("front_soul_fire", 13),
    FRONT_ENCHANTED_FIRE("front_enchanted_fire", 13);

    private final String name;
    private final int lightLevel;

    ClibanoCenterType(String name, int lightLevel) {
        this.name = name;
        this.lightLevel = lightLevel;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int getLightLevel() {
        return this.lightLevel;
    }

    public static ClibanoCenterType getFromDirection(Direction direction, Direction front) {
        if (direction == Direction.UP) {
            return ClibanoCenterType.TOP;
        } else if (direction == front) {
            return ClibanoCenterType.FRONT_OFF;
        } else {
            return ClibanoCenterType.SIDE;
        }
    }
}
