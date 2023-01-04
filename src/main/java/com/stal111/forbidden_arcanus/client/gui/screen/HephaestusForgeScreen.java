package com.stal111.forbidden_arcanus.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.inventory.EnhancerSlot;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Hephaestus Forge Screen
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen
 *
 * @author stal111
 * @since 2021-06-28
 */
public class HephaestusForgeScreen extends AbstractContainerScreen<HephaestusForgeMenu> {

    public static final ResourceLocation TEXTURES = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge.png");

    public HephaestusForgeScreen(HephaestusForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.titleLabelY -= 2;
        this.inventoryLabelY += 2;
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);

            if (slot instanceof EnhancerSlot enhancerSlot) {
                enhancerSlot.updateLocked(this.menu.getLevel());
            }
        }
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);

        for (int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);

            if (slot instanceof EnhancerSlot) {
                int posX = mouseX - this.leftPos;
                int posY = mouseY - this.topPos;

                if (posX >= (slot.x - 1) && posX < (slot.x + 16 + 1) && posY >= (slot.y - 1) && posY < (slot.y + 16 + 1)) {
                    this.hoveredSlot = slot;
                }
            }
        }

        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack poseStack, int x, int y) {
        this.font.draw(poseStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 4210752);
        this.font.draw(poseStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack poseStack, float partialTicks, int x, int y) {
        this.renderBackground(poseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURES);

        this.blit(poseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.getXSize(), this.getYSize());

        this.blit(poseStack, this.getGuiLeft() - 26, this.getGuiTop() + 16, 176, 61, 29, 51);
        this.blit(poseStack, this.getGuiLeft() + 172, this.getGuiTop() + 16, 206, 61, 29, 51);

        for (Slot slotItemHandler : this.menu.slots) {
            if (slotItemHandler instanceof EnhancerSlot enhancerSlot) {
                this.renderEnhancerSlot(enhancerSlot, poseStack, this.getGuiLeft(), this.getGuiTop());
            }
        }

        HephaestusForgeLevel level = this.menu.getLevel();

        this.renderBar(poseStack, 1, level.getMaxAureal(), 8, 177);
        this.renderBar(poseStack, 2, level.getMaxCorruption(), 8, 183);
        this.renderBar(poseStack, 3, level.getMaxSouls(), 20, 189);
        this.renderBar(poseStack, 4, level.getMaxBlood(), 151, 195);
        this.renderBar(poseStack, 5, level.getMaxExperience(), 163, 201);
    }

    @Override
    protected void renderTooltip(@Nonnull PoseStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);

        int posX = x - this.getGuiLeft();
        int posY = y - this.getGuiTop();

        this.renderBarsTooltip(matrixStack, posX, posY, x, y);

        Slot slot = this.getSlotUnderMouse();

        if (slot instanceof EnhancerSlot enhancerSlot && this.menu.isSlotLocked(enhancerSlot)) {
            this.renderTooltip(matrixStack, Component.translatable("gui.forbidden_arcanus.hephaestus_forge.unlocked_at_level").append(": " + enhancerSlot.getAdditionalData()), x, y);
        }
    }

    private void renderBarsTooltip(PoseStack matrixStack, int x, int y, int screenX, int screenY) {
        if (!(y >= 19 && y <= 68)) {
            return;
        }

        ContainerData data = this.menu.getHephaestusForgeData();
        HephaestusForgeLevel level = this.menu.getLevel();


        if (x >= 6 && x <= 13) {
            List<Component> textComponents = new ArrayList<>();
            textComponents.add(Component.translatable("forbidden_arcanus.aureal").append(": " + data.get(1) + "/" + level.getMaxAureal()));
            textComponents.add(Component.translatable("forbidden_arcanus.corruption").append(": " + data.get(2) + "/" + level.getMaxCorruption()));

            this.renderTooltip(matrixStack, Lists.transform(textComponents, Component::getVisualOrderText), screenX, screenY);
        } else if (x >= 18 && x <= 25) {
            this.renderTooltip(matrixStack, Component.translatable("forbidden_arcanus.souls").append(": " + data.get(3) + "/" + level.getMaxSouls()), screenX, screenY);
        } else if (x >= 149 && x <= 156) {
            this.renderTooltip(matrixStack, Component.translatable("forbidden_arcanus.blood").append(": " + data.get(4) + "/" + level.getMaxBlood()), screenX, screenY);
        } else if (x >= 161 && x <= 168) {
            this.renderTooltip(matrixStack, Component.translatable("forbidden_arcanus.experience").append(": " + data.get(5) + "/" + level.getMaxExperience()), screenX, screenY);
        }
    }

    private void renderBar(PoseStack matrixStack, int data, int max, int x, int textureX) {
        int ySize = Math.toIntExact(Math.round(32.0F * this.menu.getHephaestusForgeData().get(data) / max));
        this.blit(matrixStack, this.getGuiLeft() + x, this.getGuiTop() + 22 + 32 - ySize, textureX, 3 + 32 - ySize, 4, ySize);
    }

    public void renderEnhancerSlot(EnhancerSlot slot, PoseStack poseStack, int guiLeft, int guiTop) {
        if (this.menu.isSlotLocked(slot)) {
            this.blit(poseStack, guiLeft + slot.x - 2, guiTop + slot.y - 2, 176, 40, 20, 20);
        }
    }
}
