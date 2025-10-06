package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 18.11.2023
 */
public abstract class AbstractTab {

    private final int width;
    private final int height;

    protected AbstractTab(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void init();

    public void tick() {}

    public abstract void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY);

    public abstract boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY);

    public abstract boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY);

    public abstract boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick);

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
