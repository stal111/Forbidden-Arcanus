package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import com.stal111.forbidden_arcanus.client.gui.components.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ScreenAccess;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class EmptyTab extends AbstractTab {

    @Override
    public void init(ScreenAccess screen) {

    }

    @Override
    public void renderBg(ScreenAccess screen, GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

    }
}
