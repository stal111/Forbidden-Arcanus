package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.gui.GuiGraphics;

/**
 * @author stal111
 * @since 08.05.2024
 */
public abstract class FlyingLabel {

    private final Type type;

    public FlyingLabel(Type type) {
        this.type = type;
    }

    public abstract void render(GuiGraphics guiGraphics, float partialTick, int centerX, int centerY);

    public enum Type {
        BLOCK
    }
}
