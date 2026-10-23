package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.AbstractClibanoSlot;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.AlloySlot;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.MaterialSlot;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.cache.ClientRecipeCache;
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
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class MaterialListComponent implements Renderable, GuiEventListener, NarratableEntry {

    private static final Identifier MATERIAL_LIST_TEXTURE = ForbiddenArcanus.identifier("textures/gui/container/material_list.png");
    private static final Identifier MATERIAL_LIST_OVERLAY_TEXTURE = ForbiddenArcanus.identifier("textures/gui/container/material_list_overlay.png");
    private static final Identifier SCROLLER_SPRITE = ForbiddenArcanus.identifier("container/clibano/scroller");
    private static final Identifier SCROLLER_DISABLED_SPRITE = ForbiddenArcanus.identifier("container/clibano/scroller_disabled");

    private static final int SCROLLER_WIDTH = 11;
    private static final int SCROLLER_HEIGHT = 37;

    protected Minecraft minecraft;

    private int xOrigin;
    private int yOrigin;

    private ScreenRectangle scrollArea;
    private int scrollbarStartX;
    private int scrollbarStartY;

    private final List<AbstractClibanoSlot> slots = new ArrayList<>();
    private final SelectedSlotState selectedSlotState;

    private int maxScroll;

    private int scrollAmount;
    private boolean scrolling;

    private final MaterialStorage materialStorage;

    public MaterialListComponent(MaterialStorage materialStorage, SelectedSlotState selectedSlotState) {
        this.materialStorage = materialStorage;
        this.selectedSlotState = selectedSlotState;
    }

    public void init(Minecraft minecraft, int xOrigin, int yOrigin) {
        this.minecraft = minecraft;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;

        this.scrollArea = new ScreenRectangle(xOrigin + 12, this.yOrigin + 34, 104, 118);
        this.scrollbarStartX = this.xOrigin + 119;
        this.scrollbarStartY = this.yOrigin + 34;

        this.scrollAmount = 0;
        this.scrolling = false;

        this.updateSlots();
    }

    public void updateSlots() {
        this.slots.clear();

        List<MoltenMaterial> materials = this.materialStorage.getAll();

        for (int i = 0; i < materials.size(); i++) {
            int row = i / 4;
            int col = i % 4;

            this.slots.add(new MaterialSlot(
                    materials.get(i),
                    this.xOrigin + 12 + col * 26,
                    this.yOrigin + 35 + row * 36 - this.scrollAmount,
                    Component.empty(),
                    this.selectedSlotState
            ));
        }

        for (RecipeHolder<ClibanoAlloyingRecipe> recipe : ClientRecipeCache.alloyingRecipes) {
            this.slots.add(new AlloySlot(
                    recipe,
                    this.xOrigin + 12 + (this.slots.size() % 4) * 26,
                    this.yOrigin + 35 + (this.slots.size() / 4) * 36 - this.scrollAmount,
                    Component.empty(),
                    this.selectedSlotState
            ));
        }

        int totalRows = (this.slots.size() + 4 - 1) / 4;
        int totalHeight = totalRows * 36 + 3;
        int visibleHeight = this.scrollArea.bottom() - this.scrollArea.top();
        this.maxScroll = Math.max(0, totalHeight - visibleHeight);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = this.xOrigin;
        int y = this.yOrigin;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, MATERIAL_LIST_TEXTURE, x, y, 0.0F, 0.0F, 133, 186, 256, 256);

        boolean inScrollArea = this.isMouseOverScrollArea(mouseX, mouseY);
        int slotMouseX = inScrollArea ? mouseX : -1;
        int slotMouseY = inScrollArea ? mouseY : -1;

        guiGraphics.enableScissor(this.scrollArea.left(), this.scrollArea.top(), this.scrollArea.right(), this.scrollArea.bottom());
        for (AbstractClibanoSlot slot : this.slots) {
            slot.extractRenderState(guiGraphics, slotMouseX, slotMouseY, partialTick);
        }
        guiGraphics.disableScissor();

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, MATERIAL_LIST_OVERLAY_TEXTURE, x, y, 0.0F, 0.0F, 133, 186, 256, 256);

        this.renderScrollbar(guiGraphics);
    }

    public void renderScrollbar(GuiGraphicsExtractor guiGraphics) {
        int scrollbarY = this.scrollbarStartY;

        if (this.maxScroll != 0) {
            scrollbarY = this.scrollAmount * (this.scrollArea.height() - SCROLLER_HEIGHT) / this.maxScroll + this.scrollbarStartY;
        }

        Identifier sprite = this.maxScroll == 0 ? SCROLLER_DISABLED_SPRITE : SCROLLER_SPRITE;

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, this.scrollbarStartX, scrollbarY, SCROLLER_WIDTH, SCROLLER_HEIGHT);
    }

    public int getWidth() {
        return 133;
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
        this.scrolling = false;

        if (this.maxScroll != 0 && this.isMouseOverScrollbar(event.x(), event.y())) {
            this.scrolling = true;
            this.scrollTo(event.y());
            return true;
        }

        if (!this.isMouseOverScrollArea(event.x(), event.y())) {
            return GuiEventListener.super.mouseClicked(event, isDoubleClick);
        }

        for (AbstractClibanoSlot slot : this.slots) {
            if (slot.mouseClicked(event, isDoubleClick)) {
                return true;
            }
        }
        return GuiEventListener.super.mouseClicked(event, isDoubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        if (this.scrolling && this.maxScroll != 0) {
            this.scrollTo(event.y());
            return true;
        }
        return GuiEventListener.super.mouseDragged(event, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (!this.isMouseOverScrollArea(mouseX, mouseY) && !this.isMouseOverScrollbar(mouseX, mouseY)) {
            return GuiEventListener.super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        this.setScrollAmount(this.scrollAmount - (int) scrollY * 10);
        return true;
    }

    private void scrollTo(double mouseY) {
        int trackStart = this.scrollbarStartY;
        int trackHeight = this.scrollArea.height() - SCROLLER_HEIGHT;

        float scrollProgress = ((float) mouseY - trackStart - SCROLLER_HEIGHT / 2.0F) / (float) trackHeight;
        this.setScrollAmount(Math.round(scrollProgress * this.maxScroll));
    }

    private void setScrollAmount(int newScroll) {
        int oldScroll = this.scrollAmount;
        this.scrollAmount = Mth.clamp(newScroll, 0, this.maxScroll);

        for (AbstractClibanoSlot slot : this.slots) {
            slot.setY(slot.getY() + (oldScroll - this.scrollAmount));
        }
    }

    private boolean isMouseOverScrollbar(double mouseX, double mouseY) {
        return mouseX >= this.scrollbarStartX && mouseX < this.scrollbarStartX + SCROLLER_WIDTH
                && mouseY >= this.scrollbarStartY && mouseY < this.scrollbarStartY + this.scrollArea.height();
    }

    private boolean isMouseOverScrollArea(double mouseX, double mouseY) {
        return mouseX >= this.scrollArea.left() && mouseX <= this.scrollArea.right() && mouseY >= this.scrollArea.top() && mouseY <= this.scrollArea.bottom();
    }
}
