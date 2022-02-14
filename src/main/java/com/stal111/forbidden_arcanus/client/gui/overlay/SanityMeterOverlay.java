package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.util.Collections;

/**
 * Sanity Meter Overlay <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.gui.overlay.SanityMeterOverlay
 *
 * @author stal111
 * @version 1.18.1 - 2.1.0
 * @since 2022-02-14
 */
public class SanityMeterOverlay extends GuiComponent implements IIngameOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        Player player = minecraft.player;

        if (player == null || !player.getInventory().hasAnyOf(Collections.singleton(ModItems.SANITY_METER.get()))) {
            return;
        }

        IAureal aureal = AurealHelper.getCapability(player);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

        GuiComponent.blit(poseStack, window.getGuiScaledWidth() / 2 - 9, window.getGuiScaledHeight() - 39 - 13, 24, 18, 18, 19, 256, 256);

        this.renderOverlay(poseStack, window, aureal, false);
        this.renderOverlay(poseStack, window, aureal, true);
    }

    private void renderOverlay(PoseStack matrixStack, Window window, IAureal aureal, boolean corruption) {
        int ySize = Math.toIntExact(Math.round(12.0F * (corruption ? aureal.getCorruption() / 100.0F : aureal.getAureal() / 200.0F)));
        GuiComponent.blit(matrixStack, window.getGuiScaledWidth() / 2 - 6, window.getGuiScaledHeight() - 36 - ySize, 27, (corruption ? 51 : 38) + 12 - ySize, 12, ySize, 256, 256);
    }
}
