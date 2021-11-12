package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum ConnectedBlockType implements StringRepresentable {

    SINGLE("single"),
    TOP("top"),
    CENTER("center"),
    BOTTOM("bottom");

    private final String name;

    ConnectedBlockType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static ConnectedBlockType getTypeForConnections(boolean connectUp, boolean connectDown, Direction.Axis axis) {
        ConnectedBlockType connectedBlockType = ConnectedBlockType.SINGLE;

        if (connectUp && connectDown) {
            connectedBlockType = ConnectedBlockType.CENTER;
        } else if (connectUp) {
            connectedBlockType = ConnectedBlockType.BOTTOM;
        } else if (connectDown) {
            connectedBlockType = ConnectedBlockType.TOP;
        }

        if (axis == Direction.Axis.Z) {
            if (connectedBlockType == ConnectedBlockType.BOTTOM) {
                connectedBlockType = ConnectedBlockType.TOP;
            } else if (connectedBlockType == ConnectedBlockType.TOP) {
                connectedBlockType = ConnectedBlockType.BOTTOM;
            }
        }

        return connectedBlockType;
    }
}
