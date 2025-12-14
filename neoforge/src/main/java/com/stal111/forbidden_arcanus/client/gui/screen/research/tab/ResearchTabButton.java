package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.research.ResearchCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class ResearchTabButton extends ImageButton {

    public static final WidgetSprites PURPLE_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("research/purple_tab"), ForbiddenArcanus.identifier("research/purple_tab_selected"));
    public static final WidgetSprites RED_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("research/red_tab"), ForbiddenArcanus.identifier("research/red_tab_selected"));

    private final ResearchCategory category;
    private boolean selected = false;

    public ResearchTabButton(ResearchCategory category, int x, int y, int width, int height) {
        super(x, y, width, height, category.getTabSprites(), button -> {});
        this.category = category;
    }

    @Override
    public void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Identifier resourceLocation = this.sprites.get(true, this.selected);

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourceLocation, this.getX(), this.getY(), this.width, this.height);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.category.getIconLocation(), this.getX() + 4 + (this.selected ? 2 : 0), this.getY() + 5, 0, 0, 16, 16, 16, 16);
    }

    public ResearchCategory getCategory() {
        return this.category;
    }

    public void select() {
        this.selected = true;
    }

    public void unselect() {
        this.selected = false;
    }
}
