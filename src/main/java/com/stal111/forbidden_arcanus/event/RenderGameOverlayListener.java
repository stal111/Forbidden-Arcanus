package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.config.RenderingConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
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
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE || player == null) {
            return;
        }

        player.getCapability(CounterProvider.CAPABILITY).ifPresent(iTimer -> {
            SimpleCounter counter = iTimer.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

            if (counter.isActive() && counter.getValue() >= 0 && RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_RENDER.get()) {
                FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

                int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
                int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();

                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/orb_of_temporary_flight_time.png"));
                AbstractGui.blit(event.getMatrixStack(), posX, posY, 0, 0, 0, 57, 25, 25, 57);

                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get()), posX + 5, (int) (posY + 5.5F));

                TextFormatting color = counter.getValue() / 20 <= 20 ? TextFormatting.RED : TextFormatting.WHITE;
                int i = counter.getValue() < 12000 ? 27 : (int) 25.5F;

                fontRenderer.drawString(event.getMatrixStack(), StringUtils.ticksToElapsedTime(counter.getValue()), posX + i, posY + 9, color.getColor());
            }
        });

        player.getCapability(AurealProvider.CAPABILITY, null).ifPresent(aureal -> {
            if (player.inventory.hasItemStack(new ItemStack(NewModItems.SANITY_METER.get()))) {
                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png"));
                AbstractGui.blit(event.getMatrixStack(), event.getWindow().getScaledWidth() / 2 - 9, event.getWindow().getScaledHeight() - 39 - 13, 0, 24, 18, 18, 19, 256, 256);

                renderAurealOverlay(event, aureal);
                renderCorruptOverlay(event, aureal);
            }
        });
    }

    private static void renderAurealOverlay(RenderGameOverlayEvent event, IAureal aureal) {
        int ySize = Math.toIntExact(Math.round(12.0F * aureal.getAureal() / 200));
        int yOffset = 38 + 12;

        yOffset = yOffset - ySize;
        AbstractGui.blit(event.getMatrixStack(), event.getWindow().getScaledWidth() / 2 - 6, (event.getWindow().getScaledHeight() - 61 + (yOffset - ySize) / 2), 0, 27, yOffset, 12, ySize, 256, 256);
    }

    private static void renderCorruptOverlay(RenderGameOverlayEvent event, IAureal aureal) {
        int ySize = Math.toIntExact(Math.round(12.0F * aureal.getCorruption() / 100));
        int yOffset = 51 + 12;

        yOffset = yOffset - ySize;
        AbstractGui.blit(event.getMatrixStack(), event.getWindow().getScaledWidth() / 2 - 6, (event.getWindow().getScaledHeight() - 67 + (yOffset - ySize) / 2), 0, 27, yOffset, 12, ySize, 256, 256);
    }
}
