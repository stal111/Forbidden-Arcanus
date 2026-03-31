package com.stal111.forbidden_arcanus.client.gui.components.tab;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.MouseButtonEvent;

public abstract class AbstractTab implements Renderable, GuiEventListener {

    public abstract void init(ScreenAccess screen);

    public void tick() {}

    public abstract void renderBg(ScreenAccess screen, GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY);

    public abstract boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY);

    public abstract boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY);

    public abstract boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick);
}
