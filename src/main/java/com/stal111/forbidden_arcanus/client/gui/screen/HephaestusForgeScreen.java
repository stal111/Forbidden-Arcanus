package com.stal111.forbidden_arcanus.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.container.EnhancerSlot;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeLevel;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
public class HephaestusForgeScreen extends ContainerScreen<HephaestusForgeContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge.png");

    public HephaestusForgeScreen(HephaestusForgeContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize += 52;
        this.titleX += 26;
        this.titleY -= 2;
        this.playerInventoryTitleX += 26;
        this.playerInventoryTitleY += 2;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        for(int i = 0; i < this.container.inventorySlots.size(); i++) {
            Slot slot = this.container.inventorySlots.get(i);

            if (slot instanceof EnhancerSlot) {
                int posX = mouseX - this.guiLeft;
                int posY = mouseY - this.guiTop;

                if (posX >= (slot.xPos - 1) && posX < (slot.xPos + 16 + 1) && posY >= (slot.yPos - 1) && posY < (slot.yPos + 16 + 1)) {
                    hoveredSlot = slot;
                }
            }
        }

        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.title, (float) this.titleX, (float) this.titleY, 4210752);
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        this.blit(matrixStack, this.getGuiLeft() + 26, this.getGuiTop(), 0, 0, this.getXSize() - 52, this.getYSize());

        this.blit(matrixStack, this.getGuiLeft(), this.getGuiTop() + 16, 176, 61, 29, 51);
        this.blit(matrixStack, this.getGuiLeft() + 26 + 172, this.getGuiTop() + 16, 206, 61, 29, 51);


        for (int i = 0; i <= 3; i++) {
            if (!((EnhancerSlot) this.container.getSlot(i)).isUnlocked()) {
                this.blit(matrixStack, this.getGuiLeft() + (i < 2 ? 30 : 126) + 26, this.getGuiTop() + (i == 0 || i == 3 ? 22 : 44), 196, 40, 20, 20);
            }
        }

        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(this.container.getHephaestusForgeData().get(0));

        this.renderBar(matrixStack, 1, level.getMaxAureal(), 8, 177);
        this.renderBar(matrixStack, 2, level.getMaxCorruption(), 8, 183);
        this.renderBar(matrixStack, 3, level.getMaxSouls(), 20, 189);
        this.renderBar(matrixStack, 4, level.getMaxBlood(), 151, 195);
        this.renderBar(matrixStack, 5, level.getMaxExperience(), 163, 201);

        int ySize = Math.toIntExact(Math.round(32.0F * container.getHephaestusForgeData().get(0) / container.getTileEntity().getLevel().getMaxAureal()));
        this.blit(matrixStack, this.getGuiLeft() + 26 + 8, this.getGuiTop() + 22 + 32 - ySize, 177, 3 + 32 - ySize, 4, ySize);
    }

    @Override
    protected void renderHoveredTooltip(@Nonnull MatrixStack matrixStack, int x, int y) {
        super.renderHoveredTooltip(matrixStack, x, y);

        int posX = x - this.getGuiLeft() - 26;
        int posY = y - this.getGuiTop();

        this.renderBarsTooltip(matrixStack, posX, posY, x, y);

        Slot slot = getSlotUnderMouse();

        if (slot instanceof EnhancerSlot && !((EnhancerSlot) slot).isUnlocked()) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("gui.forbidden_arcanus.hephaestus_forge.unlocked_at_level").appendString(": " + ((EnhancerSlot) slot).getRequiredLevel().getName()), x, y);
        }
    }

    private void renderBarsTooltip(MatrixStack matrixStack, int x, int y, int screenX, int screenY) {
        if (!(y >= 19 && y <= 68)) {
            return;
        }

        IIntArray data = this.container.getHephaestusForgeData();
        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(data.get(0));

        if (x >= 6 && x <= 13) {
            List<ITextComponent> textComponents = new ArrayList<>();
            textComponents.add(new TranslationTextComponent("forbidden_arcanus.aureal").appendString(": " + data.get(1) + "/" + level.getMaxAureal()));
            textComponents.add(new TranslationTextComponent("forbidden_arcanus.corruption").appendString(": " + data.get(2) + "/" + level.getMaxCorruption()));

            this.renderTooltip(matrixStack, Lists.transform(textComponents, ITextComponent::func_241878_f), screenX, screenY);
        } else if (x >= 18 && x <= 25) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("forbidden_arcanus.souls").appendString(": " + data.get(3) + "/" + level.getMaxSouls()), screenX, screenY);
        } else if (x >= 149 && x <= 156) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("forbidden_arcanus.blood").appendString(": " + data.get(4) + "/" + level.getMaxBlood()), screenX, screenY);
        } else if (x >= 161 && x <= 168) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("forbidden_arcanus.experience").appendString(": " + data.get(5) + "/" + level.getMaxExperience()), screenX, screenY);
        }
    }

    private void renderBar(MatrixStack matrixStack, int data, int max, int x, int textureX) {
        int ySize = Math.toIntExact(Math.round(32.0F * this.container.getHephaestusForgeData().get(data) / max));
        this.blit(matrixStack, this.getGuiLeft() + 26 + x, this.getGuiTop() + 22 + 32 - ySize, textureX, 3 + 32 - ySize, 4, ySize);
    }
}
