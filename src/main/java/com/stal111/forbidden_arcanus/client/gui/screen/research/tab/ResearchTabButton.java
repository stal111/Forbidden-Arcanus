package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.research.ResearchCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class ResearchTabButton extends StateSwitchingButton {

    public static final WidgetSprites PURPLE_SPRITES = new WidgetSprites(new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/purple_tab"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/purple_tab_selected"));
    public static final WidgetSprites RED_SPRITES = new WidgetSprites(new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/red_tab"), new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/red_tab_selected"));

    private final ResearchCategory category;

    public ResearchTabButton(ResearchCategory category, int x, int y, int width, int height) {
        super(x, y, width, height, category == ResearchCategory.RESEARCH);
        this.category = category;

        this.initTextureValues(category.getTabSprites());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.sprites == null) {
            return;
        }

        ResourceLocation resourceLocation = this.sprites.get(true, this.isStateTriggered);

        guiGraphics.blitSprite(resourceLocation, this.getX(), this.getY(), this.width, this.height);

        guiGraphics.blit(this.category.getIconLocation(), this.getX() + 4 + (this.isStateTriggered ? 2 : 0), this.getY() + 5, 0, 0, 16, 16, 16, 16);
    }

    public ResearchCategory getCategory() {
        return this.category;
    }
}
