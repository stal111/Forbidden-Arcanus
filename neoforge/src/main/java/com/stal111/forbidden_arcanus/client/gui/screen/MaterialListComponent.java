package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;

public class MaterialListComponent implements Renderable, GuiEventListener, NarratableEntry {

    private static final ResourceLocation MATERIAL_LIST_TEXTURE = ForbiddenArcanus.location("textures/gui/container/material_list.png");

    protected Minecraft minecraft;

    private int xOrigin;
    private int height;

    public void init(int height, Minecraft minecraft, int xOrigin) {
        this.minecraft = minecraft;
        this.height = height;
        this.xOrigin = xOrigin;
    }

    private int getYOrigin() {
        return (this.height - 173) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.xOrigin;
        int y = this.getYOrigin();
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, MATERIAL_LIST_TEXTURE, x, y, 0.0F, 0.0F, 127, 173, 256, 256);

    }

    public int getWidth() {
        return 127;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarratableEntry.NarrationPriority.HOVERED;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }
}
