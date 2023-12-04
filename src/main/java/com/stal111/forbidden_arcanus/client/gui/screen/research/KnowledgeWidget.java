package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.stal111.forbidden_arcanus.common.research.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;

/**
 * @author stal111
 * @since 23.11.2023
 */
public class KnowledgeWidget {

    private final DisplayInfo display;

    private final int x;
    private final int y;

    public KnowledgeWidget(DisplayInfo display, int x, int y) {
        this.display = display;
        this.x = x + display.getRelativeX() * 25;
        this.y = y + display.getRelativeY() * 25;
    }

    public void render(GuiGraphics guiGraphics, int scrollX, int scrollY) {
        guiGraphics.blitSprite(this.display.getFrame().getFrameTexture(), this.x + scrollX + 3, this.y + scrollY, 26, 26);

        this.display.getIcon().renderIcon(guiGraphics, this.x + scrollX + 8, this.y + scrollY + 5);
    }
}
