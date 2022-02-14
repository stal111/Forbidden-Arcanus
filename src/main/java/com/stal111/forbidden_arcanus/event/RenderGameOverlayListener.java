package com.stal111.forbidden_arcanus.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderGameOverlayListener {

    private static final ResourceLocation HUD_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        Window window = event.getWindow();
        PoseStack matrixStack = event.getMatrixStack();

        if (event.getType() != RenderGameOverlayEvent.ElementType.LAYER || player == null) {
            return;
        }

        //TODO

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            if (player.getInventory().contains(ModItems.Stacks.SANITY_METER)) {

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, HUD_TEXTURE);

                GuiComponent.blit(matrixStack, window.getGuiScaledWidth() / 2 - 9, window.getGuiScaledHeight() - 39 - 13, 24, 18, 18, 19, 256, 256);

                renderOverlay(matrixStack, window, aureal, false);
                renderOverlay(matrixStack, window, aureal, true);
            }
        });
    }

    private static void renderOverlay(PoseStack matrixStack, Window window, IAureal aureal, boolean corruption) {
        int ySize = Math.toIntExact(Math.round(12.0F * (corruption ? aureal.getCorruption() / 100.0F : aureal.getAureal() / 200.0F)));
        GuiComponent.blit(matrixStack, window.getGuiScaledWidth() / 2 - 6, window.getGuiScaledHeight() - 36 - ySize, 27, (corruption ? 51 : 38) + 12 - ySize, 12, ySize, 256, 256);
    }
}
