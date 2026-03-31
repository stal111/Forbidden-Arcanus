package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.MaterialSlot;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class MaterialListComponent implements Renderable, GuiEventListener, NarratableEntry {

    private static final Identifier MATERIAL_LIST_TEXTURE = ForbiddenArcanus.identifier("textures/gui/container/material_list.png");
    private static final Identifier SCROLLER_SPRITE = ForbiddenArcanus.identifier("container/clibano/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = ForbiddenArcanus.identifier("container/clibano/scroller_disabled");

    protected Minecraft minecraft;

    private int xOrigin;
    private int yOrigin;

    private ScreenRectangle scrollArea;
    private int scrollbarStartX;
    private int scrollbarStartY;

    private final List<MaterialSlot> slots = new ArrayList<>();

    private int maxScroll;

    private int scrollAmount;

    private final MaterialStorage materialStorage;

    public MaterialListComponent(MaterialStorage materialStorage) {
        this.materialStorage = materialStorage;
    }

    public void init(int height, Minecraft minecraft, int xOrigin) {
        this.minecraft = minecraft;
        this.xOrigin = xOrigin;
        this.yOrigin = (height - 173) / 2;

        this.scrollArea = new ScreenRectangle(xOrigin + 10, this.yOrigin + 8, 101, 157);
        this.scrollbarStartX = this.xOrigin + 113;
        this.scrollbarStartY = this.yOrigin + 8;

        this.slots.clear();

        List<MoltenMaterial> materials = this.materialStorage.getAll();

        for (int i = 0; i < materials.size(); i++) {
            int row = i / 4;
            int col = i % 4;

            this.slots.add(new MaterialSlot(
                    materials.get(i),
                    xOrigin + 10 + col * 25,
                    this.yOrigin + 10 + row * 32,
                    Component.empty(),
                    false
            ));
        }

        int totalRows = (this.slots.size() + 4 - 1) / 4;
        int totalHeight = totalRows * 32 + 3;
        int visibleHeight = this.scrollArea.bottom() - this.scrollArea.top();
        this.maxScroll = Math.max(0, totalHeight - visibleHeight);

        this.scrollAmount = 0;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.xOrigin;
        int y = this.yOrigin;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, MATERIAL_LIST_TEXTURE, x, y, 0.0F, 0.0F, 127, 173, 256, 256);

        guiGraphics.enableScissor(this.scrollArea.left(), this.scrollArea.top(), this.scrollArea.right(), this.scrollArea.bottom());
        for (MaterialSlot slot : this.slots) {
            slot.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        }
        guiGraphics.disableScissor();

        this.renderScrollbar(guiGraphics);
    }

    public void renderScrollbar(GuiGraphicsExtractor guiGraphics) {
        int scrollbarY = this.scrollbarStartY;

        if (this.maxScroll != 0) {
            scrollbarY = this.scrollAmount * (this.scrollArea.height() - 27) / this.maxScroll + this.scrollbarStartY;
        }

        Identifier sprite = this.maxScroll == 0 ? SCROLLER_DISABLED_SPRITE : SCROLLER_SPRITE;

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, this.scrollbarStartX, scrollbarY, 6, 27);
    }

    public int getWidth() {
        return 127;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarratableEntry.NarrationPriority.HOVERED;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        for (MaterialSlot slot : this.slots) {
            if (slot.mouseClicked(event, isDoubleClick)) {
                return true;
            }
        }
        return GuiEventListener.super.mouseClicked(event, isDoubleClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (mouseX < this.scrollArea.left() || mouseX > this.scrollArea.right() || mouseY < this.scrollArea.top() || mouseY > this.scrollArea.bottom()) {
            return GuiEventListener.super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        int oldScroll = this.scrollAmount;
        this.scrollAmount = Mth.clamp(this.scrollAmount - (int) scrollY * 10, 0, this.maxScroll);

        for (MaterialSlot slot : this.slots) {
            slot.setY(slot.getY() + (oldScroll - this.scrollAmount));
        }
        return GuiEventListener.super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
