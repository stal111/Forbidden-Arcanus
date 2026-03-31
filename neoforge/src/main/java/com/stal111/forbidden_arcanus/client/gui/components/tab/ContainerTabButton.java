package com.stal111.forbidden_arcanus.client.gui.components.tab;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class ContainerTabButton extends ImageButton {

    private final Identifier icon;
    private boolean selected = false;

    public ContainerTabButton(int x, int y, int width, int height, ContainerTabButton.Type type, Identifier icon, Runnable onSelected) {
        super(x, y, width, height, type.sprites, _ -> onSelected.run());
        this.icon = icon;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        Identifier resourceLocation = this.sprites.get(true, this.selected);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourceLocation, this.getX(), this.getY(), this.width, this.height);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.icon, this.getX() + 4 + (this.selected ? 2 : 0), this.getY() + 5, 16, 16);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public enum Type {
        PURPLE(new WidgetSprites(ForbiddenArcanus.identifier("widget/purple_tab"), ForbiddenArcanus.identifier("widget/purple_tab_selected"))),
        RED(new WidgetSprites(ForbiddenArcanus.identifier("widget/red_tab"), ForbiddenArcanus.identifier("widget/red_tab_selected")));

        private final WidgetSprites sprites;

        Type(WidgetSprites sprites) {
            this.sprites = sprites;
        }
    }
}
