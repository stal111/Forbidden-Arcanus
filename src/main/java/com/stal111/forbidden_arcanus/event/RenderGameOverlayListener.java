package com.stal111.forbidden_arcanus.event;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.config.RenderingConfig;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderGameOverlayListener {

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

        player.getCapability(CounterProvider.CAPABILITY).ifPresent(iCounter -> {
            SimpleCounter counter = iCounter.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

            if (counter.isActive() && counter.getValue() >= 0 && RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_RENDER.get()) {
                int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
                int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();

              //  minecraft.getTextureManager().bind(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/orb_of_temporary_flight_time.png"));
                GuiComponent.blit(matrixStack, posX, posY, 0, 0, 0, 57, 25, 25, 57);

                minecraft.getItemRenderer().renderAndDecorateItem(NewModItems.Stacks.ORB_OF_TEMPORARY_FLIGHT, posX + 5, (int) (posY + 5.5F));

                ChatFormatting color = counter.getValue() / 20 <= 20 ? ChatFormatting.RED : ChatFormatting.WHITE;
                int i = counter.getValue() < 12000 ? 27 : (int) 25.5F;

                minecraft.font.draw(matrixStack, StringUtil.formatTickDuration(counter.getValue()), posX + i, posY + 9, color.getColor());
            }
        });

        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
            if (player.getInventory().contains(NewModItems.Stacks.SANITY_METER)) {
                //minecraft.getTextureManager().bind(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png"));
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
