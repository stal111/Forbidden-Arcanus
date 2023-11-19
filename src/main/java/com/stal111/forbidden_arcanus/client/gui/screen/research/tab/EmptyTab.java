package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class EmptyTab extends AbstractTab {

    public EmptyTab(int width, int height) {
        super(width, height);
    }

    @Override
    public void init() {

    }

    @Override
    public void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }
}
