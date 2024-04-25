package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2022-02-14
 */
public class FlightTimerOverlay implements LayeredDraw.Layer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/flight_timer_overlay.png");

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        //TODO: Fix this
//        minecraft.player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
//            SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));
//
//            if (counter.isActive() && counter.getValue() >= 0 && RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_RENDER.get()) {
//                ChatFormatting color = counter.getValue() / 20 <= 20 ? ChatFormatting.RED : ChatFormatting.WHITE;
//                int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
//                int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();
//
//                guiGraphics.blit(TEXTURE, posX, posY, 0, 0, 0, 57, 25, 64, 32);
//
//                guiGraphics.renderFakeItem(ModItems.ORB_OF_TEMPORARY_FLIGHT.get().getDefaultInstance(), posX + 5, (int) (posY + 5.5F));
//
//                int i = counter.getValue() < 12000 ? 27 : 25;
//
//                guiGraphics.drawString(minecraft.font, StringUtil.formatTickDuration(counter.getValue()), posX + i, posY + 9, color.getColor());
//            }
//        });
    }
}
