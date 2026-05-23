package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class EssenceBar extends AbstractWidget {

    private final Supplier<EssenceStorage> storageSupplier;
    private final Identifier sprite;
    private final EssenceBarType.FillOrigin origin;

    public EssenceBar(int x, int y, EssenceBarType type, Supplier<EssenceStorage> storageSupplier) {
        super(x, y, type.width(), type.height(), Component.empty());
        this.storageSupplier = storageSupplier;
        this.sprite = type.texture();
        this.origin = type.origin();
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        EssenceStorage essenceStorage = this.storageSupplier.get();

        switch (this.origin) {
            case BOTTOM -> {
                int ySize = Mth.floor(this.height * essenceStorage.getFillPercentage());
                int yOffset = this.height - ySize;
                if (ySize > 0) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.width, this.height, 0, yOffset, this.getX(), this.getY() + yOffset, this.width, ySize);
                }
            }
            case TOP -> {
                int ySize = Mth.floor(this.height * essenceStorage.getFillPercentage());
                if (ySize > 0) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.width, this.height, 0, 0, this.getX(), this.getY(), this.width, ySize);
                }
            }
            case RIGHT -> {
                int xSize = Mth.floor(this.width * essenceStorage.getFillPercentage());
                int xOffset = this.width - xSize;
                if (xSize > 0) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.width, this.height, xOffset, 0, this.getX() + xOffset, this.getY(), xSize, this.height);
                }
            }
            case LEFT -> {
                int xSize = Mth.floor(this.width * essenceStorage.getFillPercentage());
                if (xSize > 0) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.width, this.height, 0, 0, this.getX(), this.getY(), xSize, this.height);
                }
            }
        }

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
