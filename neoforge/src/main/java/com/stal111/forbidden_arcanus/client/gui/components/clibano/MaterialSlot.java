package com.stal111.forbidden_arcanus.client.gui.components.clibano;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MaterialSlot extends AbstractButton {

    private static final ResourceLocation MATERIAL_SLOT_SPRITE = ForbiddenArcanus.location("container/clibano/material_slot");
    private static final ResourceLocation MATERIAL_SLOT_ENABLED_SPRITE = ForbiddenArcanus.location("container/clibano/material_slot_enabled");

    private boolean enabled;

    public MaterialSlot(int x, int y, Component message, boolean enabled) {
        super(x, y, 25, 32, message);
        this.enabled = enabled;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.enabled = !this.enabled;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTick) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getSprite(), this.getX(), this.getY(), this.width, this.height);
    }

    private ResourceLocation getSprite() {
        return this.enabled ? MATERIAL_SLOT_ENABLED_SPRITE : MATERIAL_SLOT_SPRITE;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
