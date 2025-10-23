package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class ClibanoScreen extends AbstractContainerScreen<ClibanoMenu> {

    private static final ResourceLocation CONTAINER_TEXTURE = ForbiddenArcanus.location("textures/gui/container/clibano_combustion.png");
    private static final ResourceLocation LIT_PROGRESS_SPRITE = ForbiddenArcanus.location("container/clibano/lit_progress");

    private final MaterialListComponent materialList;

    public ClibanoScreen(ClibanoMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 173;
        this.titleLabelY -= 2;
        this.inventoryLabelY += 9;

        this.materialList = new MaterialListComponent(menu.getStoredMaterials());
    }

    @Override
    protected void init() {
        super.init();

        int totalWidth = this.imageWidth + 2 + this.materialList.getWidth();
        int totalLeft = (this.width - totalWidth) / 2;

        this.leftPos = totalLeft + 2 + this.materialList.getWidth();

        this.materialList.init(this.height, this.minecraft, totalLeft);

        this.addRenderableWidget(this.materialList);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        super.render(guiGraphics, x, y, partialTicks);

        this.renderTooltip(guiGraphics, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        float litProgress = this.menu.getLitProgress();
        if (litProgress > 0.0F) {
            int height = Mth.ceil(litProgress * 15.0F);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE, 18, 18, 0, 16 - height, this.leftPos + 37, this.topPos + 39 + 15 - height, 18, height);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean isDoubleClick) {
        if (this.materialList.mouseClicked(mouseButtonEvent, isDoubleClick)) {
            this.setFocused(this.materialList);
            return true;
        }

        return super.mouseClicked(mouseButtonEvent, isDoubleClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.materialList.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) {
            this.setFocused(this.materialList);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
