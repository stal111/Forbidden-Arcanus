package com.stal111.forbidden_arcanus.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

/**
 * Clibano Screen <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.gui.screen.ClibanoScreen
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-25
 */
public class ClibanoScreen extends AbstractContainerScreen<ClibanoMenu> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/clibano_combustion.png");

    public ClibanoScreen(ClibanoMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight += 7;
        this.titleLabelY -= 2;
        this.inventoryLabelY += 9;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);

        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(poseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURES);

        this.blit(poseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.getXSize(), this.getYSize());

        // Cooking Indicators
        if (this.menu.getCookingDuration().getFirst() != 0) {
            int xSize = Math.toIntExact(Math.round(22.0F * this.menu.getCookingProgress().getFirst() / this.menu.getCookingDuration().getFirst()));

            this.blit(poseStack, this.getGuiLeft() + 83, this.getGuiTop() + 34, 176, 32, xSize, 7);
        }

        if (this.menu.getCookingDuration().getSecond() != 0) {
            int xSize = Math.toIntExact(Math.round(22.0F * this.menu.getCookingProgress().getSecond() / this.menu.getCookingDuration().getSecond()));

            this.blit(poseStack, this.getGuiLeft() + 83, this.getGuiTop() + 43, 176, 41, xSize, 7);
        }

        // Soul Indicator
        if (this.menu.isSoulActive()) {
            int ySize = Math.toIntExact(Math.round(18.0F * this.menu.getSoulDuration() / ClibanoMainBlockEntity.SOUL_DURATION));

            this.blit(poseStack, this.getGuiLeft() + 52, this.getGuiTop() + 38 + 18 - ySize, 233, 18 - ySize, 18, ySize);
        }

        if (this.menu.getBurnDuration() != 0) {
            int ySize = Math.toIntExact(Math.round(15.0F * this.menu.getBurnTime() / this.menu.getBurnDuration()));

            this.blit(poseStack, this.getGuiLeft() + 55, this.getGuiTop() + 39 + 15 - ySize, 179, 1 + 15 - ySize, 12, ySize);
        }
    }
}
