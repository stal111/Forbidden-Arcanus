package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class EssenceBar extends AbstractWidget {

    private final Supplier<EssenceStorage> storageSupplier;
    private final ResourceLocation texture;

    public EssenceBar(int x, int y, int width, int height, Supplier<EssenceStorage> storageSupplier, ResourceLocation texture) {
        super(x, y, width, height, Component.empty());
        this.storageSupplier = storageSupplier;
        this.texture = texture;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        EssenceStorage essenceStorage = this.storageSupplier.get();
        int ySize = Mth.floor(this.height * essenceStorage.getFillPercentage());
        int yOffset = this.height - ySize;

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.texture, this.width, this.height, 0, yOffset, this.getX(), this.getY() + yOffset, this.width, ySize);

        if (this.isHovered()) {
            guiGraphics.setTooltipForNextFrame(essenceStorage.asComponent(ChatFormatting.WHITE), mouseX, mouseY);
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
