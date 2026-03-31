package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 12.12.2023
 */
public class ConfirmPopupScreen extends Screen {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/research/confirm_background.png");
    private static final WidgetSprites CONFIRM_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("research/confirm"), ForbiddenArcanus.identifier("research/confirm_highlighted"));
    private static final WidgetSprites CANCEL_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("research/cancel"), ForbiddenArcanus.identifier("research/cancel_highlighted"));

    private final BooleanConsumer callback;

    protected ConfirmPopupScreen(BooleanConsumer callback, Component title) {
        super(title);
        this.callback = callback;
    }

    @Override
    protected void init() {
        var confirmButton = new ImageButton(this.width / 2 - 22, this.height / 2 - 1, 16, 16, CONFIRM_SPRITES, button -> {
            this.callback.accept(true);
        }, CommonComponents.GUI_YES);
        var cancelButton = new ImageButton(this.width / 2 + 6, this.height / 2 - 1, 16, 16, CANCEL_SPRITES, button -> {
            this.callback.accept(false);
        }, CommonComponents.GUI_NO);

        this.addRenderableWidget(confirmButton);
        this.addRenderableWidget(cancelButton);
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.centeredText(this.font, this.title, this.width / 2, this.height / 2 - 10, -1);
    }

    @Override
    public void extractBackground(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.width / 2 - 40, this.height / 2 - 17, 0, 0, 80, 34, 80, 34);
    }
}
