package com.stal111.forbidden_arcanus.client.gui.screen.research.tab;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ScreenAccess;
import com.stal111.forbidden_arcanus.client.gui.screen.research.KnowledgeWidget;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 18.11.2023
 */
public class ResearchTab extends AbstractTab {

    private static final Identifier BACKGROUND = ForbiddenArcanus.identifier("textures/gui/research/fiery_background.png");
    private static final Identifier BACKGROUND_STARS = ForbiddenArcanus.identifier("textures/gui/research/background_stars.png");
    private static final Identifier BACKGROUND_STELLAR_DUST_0 = ForbiddenArcanus.identifier("textures/gui/research/background_stellar_dust_0.png");
    private static final Identifier BACKGROUND_STELLAR_DUST_1 = ForbiddenArcanus.identifier("textures/gui/research/background_stellar_dust_1.png");

    private final List<KnowledgeWidget> knowledgeWidgets = new ArrayList<>();

    private double scrollX;
    private double scrollY;

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    @Override
    public void init(ScreenAccess screen) {
        for (Knowledge entry : Minecraft.getInstance().level.registryAccess().lookupOrThrow(FARegistries.KNOWLEDGE)) {
            this.knowledgeWidgets.add(new KnowledgeWidget(entry.displayInfo(), 0, 0));
        }
    }

    @Override
    public void tick() {
        for (KnowledgeWidget widget : this.knowledgeWidgets) {
            widget.tick();
        }
    }

    @Override
    public void renderBg(ScreenAccess screen, GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);

        //TODO
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, 0, 0, -i * 0.9F, -j * 0.9F, screen.getWidth(), screen.getHeight(), 512, 512);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_STELLAR_DUST_0, (int) (i * 1.15F), (int) (j * 1.15F), 0, 0, 512, 512, 512, 512);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_STELLAR_DUST_1, (int) (screen.getWidth() / 2 + i * 1.15F), (int) (screen.getHeight() / 2 + j * 1.15F), 0, 0, 512, 512, 512, 512);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_STARS, 0, 0, -i * 1.35F, -j * 1.35F, screen.getWidth(), screen.getHeight(), 512, 512);

//        RenderSystem.disableBlend();

        for (KnowledgeWidget widget : this.knowledgeWidgets) {
            widget.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY) {
        //TODO
//        this.scroll(dragX, dragY);

        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll(scrollX * 16.0D, scrollY * 16.0D);

        return false;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    public void scroll(double dragX, double dragY) {
//        this.scrollX = Mth.clamp(this.scrollX + dragX, -(this.maxX - 234), 0.0D);
//
//        this.scrollY = Mth.clamp(this.scrollY + dragY, -(this.maxY - 113), 0.0D);

        this.scrollX += dragX;
        this.scrollY += dragY;

        for (KnowledgeWidget widget : this.knowledgeWidgets) {
            widget.setX(widget.calculatePositionX((int) this.scrollX));
            widget.setY(widget.calculatePositionY((int) this.scrollY));
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        for (KnowledgeWidget widget : this.knowledgeWidgets) {
            if (widget.mouseClicked(event, isDoubleClick)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

    }
}
