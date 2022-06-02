package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * Clibano Center Type <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public enum ClibanoCenterType implements StringRepresentable {
    SIDE("side"),
    TOP("top"),
    FRONT_OFF("front_off"),
    FRONT_BLUE_FIRE("front_blue_fire");;

    private final String name;

    ClibanoCenterType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
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
