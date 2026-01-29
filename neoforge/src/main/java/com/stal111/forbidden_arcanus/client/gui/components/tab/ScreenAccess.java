package com.stal111.forbidden_arcanus.client.gui.components.tab;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public interface ScreenAccess {
    int getGuiLeft();
    int getGuiTop();
    int getWidth();
    int getHeight();
    <W extends GuiEventListener & Renderable & NarratableEntry> W addRenderableWidget(W widget);
    <W extends Renderable> W addRenderableOnly(W renderable);
}
