package com.stal111.forbidden_arcanus.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.config.RenderingConfig;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderGameOverlayListener {

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        MainWindow window = event.getWindow();
        MatrixStack matrixStack = event.getMatrixStack();

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE || player == null) {
            return;
        }

        player.getCapability(CounterProvider.CAPABILITY).ifPresent(iCounter -> {
            SimpleCounter counter = iCounter.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

            if (counter.isActive() && counter.getValue() >= 0 && RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_RENDER.get()) {
                int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
                int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();

                minecraft.getTextureManager().bindTexture(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/orb_of_temporary_flight_time.png"));
                AbstractGui.blit(matrixStack, posX, posY, 0, 0, 0, 57, 25, 25, 57);

                minecraft.getItemRenderer().renderItemAndEffectIntoGUI(NewModItems.Stacks.ORB_OF_TEMPORARY_FLIGHT, posX + 5, (int) (posY + 5.5F));

                TextFormatting color = counter.getValue() / 20 <= 20 ? TextFormatting.RED : TextFormatting.WHITE;
                int i = counter.getValue() < 12000 ? 27 : (int) 25.5F;

                minecraft.fontRenderer.drawString(matrixStack, StringUtils.ticksToElapsedTime(counter.getValue()), posX + i, posY + 9, color.getColor());
            }
        });

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            if (player.inventory.hasItemStack(NewModItems.Stacks.SANITY_METER)) {
                minecraft.getTextureManager().bindTexture(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png"));
                AbstractGui.blit(matrixStack, window.getScaledWidth() / 2 - 9, window.getScaledHeight() - 39 - 13, 24, 18, 18, 19, 256, 256);

                renderOverlay(matrixStack, window, aureal, false);
                renderOverlay(matrixStack, window, aureal, true);
            }
        });
    }

    private static void renderOverlay(MatrixStack matrixStack, MainWindow window, IAureal aureal, boolean corruption) {
        int ySize = Math.toIntExact(Math.round(12.0F * (corruption ? aureal.getCorruption() / 100.0F : aureal.getAureal() / 200.0F)));
        AbstractGui.blit(matrixStack, window.getScaledWidth() / 2 - 6, window.getScaledHeight() - 36 - ySize, 27, (corruption ? 51 : 38) + 12 - ySize, 12, ySize, 256, 256);
    }
}
