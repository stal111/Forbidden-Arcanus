package com.stal111.forbidden_arcanus.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.container.EnhancerSlot;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeLevel;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Hephaestus Forge Screen
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-28
 */
public class HephaestusForgeScreen extends AbstractContainerScreen<HephaestusForgeContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge.png");

    public HephaestusForgeScreen(HephaestusForgeContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth += 52;
        this.titleLabelX += 26;
        this.titleLabelY -= 2;
        this.inventoryLabelX += 26;
        this.inventoryLabelY += 2;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        for(int i = 0; i < this.menu.slots.size(); i++) {
            Slot slot = this.menu.slots.get(i);

            if (slot instanceof EnhancerSlot) {
                int posX = mouseX - this.leftPos;
                int posY = mouseY - this.topPos;

                if (posX >= (slot.x - 1) && posX < (slot.x + 16 + 1) && posY >= (slot.y - 1) && posY < (slot.y + 16 + 1)) {
                    hoveredSlot = slot;
                }
            }
        }

        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, (float) this.titleLabelX, (float) this.titleLabelY, 4210752);
        this.font.draw(matrixStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);

        //TODO
       // RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
       // this.getMinecraft().getTextureManager().bind(TEXTURES);

        this.blit(matrixStack, this.getGuiLeft() + 26, this.getGuiTop(), 0, 0, this.getXSize() - 52, this.getYSize());

        this.blit(matrixStack, this.getGuiLeft(), this.getGuiTop() + 16, 176, 61, 29, 51);
        this.blit(matrixStack, this.getGuiLeft() + 26 + 172, this.getGuiTop() + 16, 206, 61, 29, 51);


        for (int i = 0; i <= 3; i++) {
            if (!((EnhancerSlot) this.menu.getSlot(i)).isUnlocked()) {
                this.blit(matrixStack, this.getGuiLeft() + (i < 2 ? 30 : 126) + 26, this.getGuiTop() + (i == 0 || i == 3 ? 22 : 44), 196, 40, 20, 20);
            }
        }

        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(this.menu.getHephaestusForgeData().get(0));

        this.renderBar(matrixStack, 1, level.getMaxAureal(), 8, 177);
        this.renderBar(matrixStack, 2, level.getMaxCorruption(), 8, 183);
        this.renderBar(matrixStack, 3, level.getMaxSouls(), 20, 189);
        this.renderBar(matrixStack, 4, level.getMaxBlood(), 151, 195);
        this.renderBar(matrixStack, 5, level.getMaxExperience(), 163, 201);

        int ySize = Math.toIntExact(Math.round(32.0F * menu.getHephaestusForgeData().get(0) / menu.getTileEntity().getForgeLevel().getMaxAureal()));
        this.blit(matrixStack, this.getGuiLeft() + 26 + 8, this.getGuiTop() + 22 + 32 - ySize, 177, 3 + 32 - ySize, 4, ySize);
    }

    @Override
    protected void renderTooltip(@Nonnull PoseStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);

        int posX = x - this.getGuiLeft() - 26;
        int posY = y - this.getGuiTop();

        this.renderBarsTooltip(matrixStack, posX, posY, x, y);

        Slot slot = getSlotUnderMouse();

        if (slot instanceof EnhancerSlot && !((EnhancerSlot) slot).isUnlocked()) {
            this.renderTooltip(matrixStack, new TranslatableComponent("gui.forbidden_arcanus.hephaestus_forge.unlocked_at_level").append(": " + ((EnhancerSlot) slot).getRequiredLevel().getName()), x, y);
        }
    }

    private void renderBarsTooltip(PoseStack matrixStack, int x, int y, int screenX, int screenY) {
        if (!(y >= 19 && y <= 68)) {
            return;
        }

        ContainerData data = this.menu.getHephaestusForgeData();
        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(data.get(0));

        if (x >= 6 && x <= 13) {
            List<Component> textComponents = new ArrayList<>();
            textComponents.add(new TranslatableComponent("forbidden_arcanus.aureal").append(": " + data.get(1) + "/" + level.getMaxAureal()));
            textComponents.add(new TranslatableComponent("forbidden_arcanus.corruption").append(": " + data.get(2) + "/" + level.getMaxCorruption()));

            this.renderTooltip(matrixStack, Lists.transform(textComponents, Component::getVisualOrderText), screenX, screenY);
        } else if (x >= 18 && x <= 25) {
            this.renderTooltip(matrixStack, new TranslatableComponent("forbidden_arcanus.souls").append(": " + data.get(3) + "/" + level.getMaxSouls()), screenX, screenY);
        } else if (x >= 149 && x <= 156) {
            this.renderTooltip(matrixStack, new TranslatableComponent("forbidden_arcanus.blood").append(": " + data.get(4) + "/" + level.getMaxBlood()), screenX, screenY);
        } else if (x >= 161 && x <= 168) {
            this.renderTooltip(matrixStack, new TranslatableComponent("forbidden_arcanus.experience").append(": " + data.get(5) + "/" + level.getMaxExperience()), screenX, screenY);
        }
    }

    private void renderBar(PoseStack matrixStack, int data, int max, int x, int textureX) {
        int ySize = Math.toIntExact(Math.round(32.0F * this.menu.getHephaestusForgeData().get(data) / max));
        this.blit(matrixStack, this.getGuiLeft() + 26 + x, this.getGuiTop() + 22 + 32 - ySize, textureX, 3 + 32 - ySize, 4, ySize);
    }
}
