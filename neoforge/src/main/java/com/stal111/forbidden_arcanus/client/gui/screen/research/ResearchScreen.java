package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.ResearchTabButton;
import com.stal111.forbidden_arcanus.common.inventory.research.ResearchDeskMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 09.11.2023
 */
public class ResearchScreen extends AbstractContainerScreen<ResearchDeskMenu> {

    private static final ResourceLocation FRAME_TOP_LEFT_CORNER = ForbiddenArcanus.location("textures/gui/research/frame/top_left_corner.png");
    private static final ResourceLocation FRAME_TOP_RIGHT_CORNER = ForbiddenArcanus.location("textures/gui/research/frame/top_right_corner.png");
    private static final ResourceLocation FRAME_BOTTOM_LEFT_CORNER = ForbiddenArcanus.location("textures/gui/research/frame/bottom_left_corner.png");
    private static final ResourceLocation FRAME_BOTTOM_RIGHT_CORNER = ForbiddenArcanus.location("textures/gui/research/frame/bottom_right_corner.png");
    private static final ResourceLocation FRAME_TOP_CENTER = ForbiddenArcanus.location("textures/gui/research/frame/top_center.png");
    private static final ResourceLocation FRAME_TOP = ForbiddenArcanus.location("textures/gui/research/frame/quantum_catcher_top.png");
    private static final ResourceLocation FRAME_BOTTOM = ForbiddenArcanus.location("textures/gui/research/frame/bottom.png");
    private static final ResourceLocation FRAME_LEFT = ForbiddenArcanus.location("textures/gui/research/frame/left.png");
    private static final ResourceLocation FRAME_RIGHT = ForbiddenArcanus.location("textures/gui/research/frame/right.png");

    private final List<ResearchTabButton> tabButtons = new ArrayList<>();
    @Nullable
    private ResearchTabButton selectedTabButton;

    private AbstractTab selectedTab;

    public ResearchScreen(ResearchDeskMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void containerTick() {
        this.selectedTab.tick();
    }

    @Override
    protected void init() {
        super.init();

        this.tabButtons.clear();

        int y = this.height / 4;

        for (ResearchCategory tab : ResearchCategory.values()) {
            ResearchTabButton button = new ResearchTabButton(tab, 0, y, 35, 27);

            this.addRenderableWidget(button);
            this.tabButtons.add(button);

            y += 28;
        }

        this.selectedTabButton = this.tabButtons.get(0);

        this.setTab(this.selectedTabButton.getCategory().createTab(this.width, this.height));
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        this.selectedTab.renderBg(guiGraphics, partialTick, mouseX, mouseY);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int x = 0;

        while (x < this.width) {
            int width = Math.min(42, this.width - x);

            guiGraphics.blit(FRAME_TOP, x, 0, 0, 0, width, 22, 42, 22);
            guiGraphics.blit(FRAME_BOTTOM, x, this.height - 22, 0, 0, width, 22, 42, 22);

            x += width;
        }

        int y = 0;

        while (y < this.height) {
            int height = Math.min(28, this.height - y);

            guiGraphics.blit(FRAME_LEFT, 0, y, 0, 0, 22, height, 22, 28);
            guiGraphics.blit(FRAME_RIGHT, this.width - 22, y, 0, 0, 22, height, 22, 28);

            y += height;
        }

        guiGraphics.blit(FRAME_TOP_LEFT_CORNER, 0, 0, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_TOP_RIGHT_CORNER, this.width - 22, 0, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_BOTTOM_LEFT_CORNER, 0, this.height - 22, 0, 0, 22, 22, 22, 22);
        guiGraphics.blit(FRAME_BOTTOM_RIGHT_CORNER, this.width - 22, this.height - 22, 0, 0, 22, 22, 22, 22);

        guiGraphics.blit(FRAME_TOP_CENTER, this.width / 2 - 25, 0, 0, 0, 50, 22, 50, 22);

        RenderSystem.disableBlend();
    }

    public void setTab(AbstractTab tab) {
        this.selectedTab = tab;

        this.selectedTab.init();
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ResearchTabButton tabButton : this.tabButtons) {
            if (tabButton.mouseClicked(mouseX, mouseY, button)) {
                if (this.selectedTabButton != null) {
                    this.selectedTabButton.setStateTriggered(false);
                }

                this.selectedTabButton = tabButton;
                this.selectedTabButton.setStateTriggered(true);

                this.setTab(this.selectedTabButton.getCategory().createTab(this.width, this.height));

                return true;
            }
        }

        this.selectedTab.mouseClicked(mouseX, mouseY, button);

        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.selectedTab.mouseDragged(mouseX, mouseY, button, dragX, dragY);

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.selectedTab.mouseScrolled(mouseX, mouseY, scrollX, scrollY);

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
