package com.stal111.forbidden_arcanus.client.gui.screen.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ScreenAccess;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class EditSpellsTab extends AbstractTab {

    public static final Identifier BACKGROUND = ForbiddenArcanus.identifier("textures/gui/container/wand_desk_spells.png");

    @Override
    public void init(ScreenAccess screen) {

    }

    @Override
    public void renderBg(ScreenAccess screen, GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, screen.getGuiLeft(), screen.getGuiTop(), 0, 0, screen.getWidth(), screen.getHeight(), 256, 256);
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
