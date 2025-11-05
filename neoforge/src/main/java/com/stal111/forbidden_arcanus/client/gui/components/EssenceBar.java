package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.function.IntSupplier;

public class EssenceBar extends AbstractWidget {

    private final EssenceType type;
    private final IntSupplier amount;
    private final IntSupplier limit;
    private final ResourceLocation texture;

    public EssenceBar(int x, int y, int width, int height, EssenceType type, IntSupplier amount, IntSupplier limit, ResourceLocation texture) {
        super(x, y, width, height, Component.empty());
        this.type = type;
        this.amount = amount;
        this.limit = limit;
        this.texture = texture;
    }

    public MutableComponent buildComponent() {
        return this.type.getComponent().copy().append(": " + this.amount.getAsInt() + "/" + this.limit.getAsInt());
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int ySize = Math.toIntExact(Math.round(32.0F * this.amount.getAsInt() / this.limit.getAsInt()));
        int yOffset = this.height - ySize;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.texture, this.width, this.height, 0, yOffset, this.getX(), this.getY() + yOffset, this.width, ySize);

        if (this.isHovered()) {
            guiGraphics.setTooltipForNextFrame(this.buildComponent(), mouseX, mouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    protected boolean isValidClickButton(MouseButtonInfo buttonInfo) {
        return false;
    }
}
