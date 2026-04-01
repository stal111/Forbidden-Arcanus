package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBar;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBarType;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.inventory.LockableSlot;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Screen
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen
 *
 * @author stal111
 * @since 2021-06-28
 */
public class HephaestusForgeScreen extends AbstractContainerScreen<HephaestusForgeMenu> {

    public static final Identifier TEXTURES = ForbiddenArcanus.identifier("textures/gui/container/hephaestus_forge.png");

    public HephaestusForgeScreen(HephaestusForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.titleLabelY -= 2;
        this.inventoryLabelY += 2;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new EssenceBar(this.getGuiLeft() + 11, this.getGuiTop() + 22, EssenceBarType.HEPHAESTUS_FORGE_AUREAL, () -> this.menu.getEssenceStorage(EssenceType.AUREAL)));
        this.addRenderableWidget(new EssenceBar(this.getGuiLeft() + 23, this.getGuiTop() + 22, EssenceBarType.HEPHAESTUS_FORGE_ECTOPLASM, () ->  this.menu.getEssenceStorage(EssenceType.ECTOPLASM)));
        this.addRenderableWidget(new EssenceBar(this.getGuiLeft() + 147, this.getGuiTop() + 22, EssenceBarType.HEPHAESTUS_FORGE_BLOOD, () ->  this.menu.getEssenceStorage(EssenceType.BLOOD)));
        this.addRenderableWidget(new EssenceBar(this.getGuiLeft() + 159, this.getGuiTop() + 22, EssenceBarType.HEPHAESTUS_FORGE_EXPERIENCE, () ->  this.menu.getEssenceStorage(EssenceType.EXPERIENCE)));
    }

    @Override
    public void extractRenderState(@Nonnull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);

            if (slot instanceof LockableSlot) {
                int posX = mouseX - this.leftPos;
                int posY = mouseY - this.topPos;

                if (posX >= (slot.x - 1) && posX < (slot.x + 16 + 1) && posY >= (slot.y - 1) && posY < (slot.y + 16 + 1)) {
                    this.hoveredSlot = slot;
                }
            }
        }

        this.extractTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void extractBackground(@Nonnull GuiGraphicsExtractor guiGraphics, int x, int y, float partialTick) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.getXSize(), this.getYSize(), 256, 256);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, this.getGuiLeft() - 26, this.getGuiTop() + 16, 176, 61, 29, 51, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURES, this.getGuiLeft() + 172, this.getGuiTop() + 16, 206, 61, 29, 51, 256, 256);
    }

    @Override
    protected void extractSlots(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.extractSlots(guiGraphics, mouseX, mouseY);

        for (Slot slot : this.menu.slots) {
            if (slot instanceof LockableSlot lockableSlot && lockableSlot.isLocked()) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LockableSlot.LOCKED_SLOT_SPRITE, slot.x - 2, slot.y - 2, 20, 20);
            }
        }
    }

    @Override
    protected void extractTooltip(@Nonnull GuiGraphicsExtractor guiGraphics, int x, int y) {
        super.extractTooltip(guiGraphics, x, y);

        Slot slot = this.getSlotUnderMouse();

        if (slot instanceof LockableSlot lockableSlot && lockableSlot.isLocked()) {
            guiGraphics.setTooltipForNextFrame(this.font, lockableSlot.getLockedDescription(), x, y);
        }
    }
}
