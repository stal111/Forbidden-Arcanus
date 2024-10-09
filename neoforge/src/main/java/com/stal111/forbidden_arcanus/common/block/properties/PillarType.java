package com.stal111.forbidden_arcanus.common.block.properties;

import com.stal111.forbidden_arcanus.common.block.PillarBlock;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

/**
 * Pillar Type <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.PillarType
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-26
 */
public enum PillarType implements StringRepresentable {
    SINGLE("single"),
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    PillarType(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static PillarType getTypeForConnections(boolean connectUp, boolean connectDown) {
        if (connectUp && connectDown) {
            return PillarType.MIDDLE;
        } else if (connectUp) {
            return PillarType.BOTTOM;
        } else if (connectDown) {
            return PillarType.TOP;
        }

        return PillarType.SINGLE;
    }

    public PillarType getOpposite() {
        return switch (this) {
            case SINGLE -> PillarType.SINGLE;
            case TOP -> PillarType.BOTTOM;
            case MIDDLE -> PillarType.MIDDLE;
            case BOTTOM -> PillarType.TOP;
        };
    }
}
