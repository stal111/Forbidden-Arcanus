package com.stal111.forbidden_arcanus.common.block.properties.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-05-22
 */
public enum ClibanoCenterType implements StringRepresentable {
    SIDE("side", null),
    TOP("top", null),
    FRONT_OFF("front_off", null),
    FRONT_FIRE("front_fire", ClibanoFireType.FIRE),
    FRONT_SOUL_FIRE("front_soul_fire", ClibanoFireType.SOUL_FIRE),
    FRONT_ENCHANTED_FIRE("front_enchanted_fire", ClibanoFireType.ENCHANTED_FIRE);

    private final String name;
    private final @Nullable ClibanoFireType fireType;

    ClibanoCenterType(String name, @Nullable ClibanoFireType fireType) {
        this.name = name;
        this.fireType = fireType;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Nullable
    public ClibanoFireType getFireType() {
        return this.fireType;
    }

    public boolean isFront() {
        return this == FRONT_OFF || this.name.contains("front");
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
