package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.RenderingConfig;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

/**
 * @author stal111
 * @since 2022-02-14
 */
public class FlightTimerOverlay extends GuiComponent implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/flight_timer_overlay.png");

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        minecraft.player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
            SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

            if (counter.isActive() && counter.getValue() >= 0 && RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_RENDER.get()) {
                ChatFormatting color = counter.getValue() / 20 <= 20 ? ChatFormatting.RED : ChatFormatting.WHITE;
                int posX = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_X_POSITION.get();
                int posY = RenderingConfig.ORB_OF_TEMPORARY_FLIGHT_OVERLAY_Y_POSITION.get();

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, TEXTURE);

                GuiComponent.blit(poseStack, posX, posY, 0, 0, 0, 57, 25, 64, 32);

                minecraft.getItemRenderer().renderAndDecorateItem(ModItems.Stacks.ORB_OF_TEMPORARY_FLIGHT, posX + 5, (int) (posY + 5.5F));

                float i = counter.getValue() < 12000 ? 27.0F : 25.5F;

                minecraft.font.draw(poseStack, StringUtil.formatTickDuration(counter.getValue()), posX + i, posY + 9, color.getColor());
            }
        });
    }
}
