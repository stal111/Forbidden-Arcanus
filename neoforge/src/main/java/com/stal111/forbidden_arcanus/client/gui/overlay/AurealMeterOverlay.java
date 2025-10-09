package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class AurealMeterOverlay implements GuiLayer {

    private static final ResourceLocation TEXTURE = ForbiddenArcanus.location("textures/gui/hud.png");

    private static final int BAR_WIDTH = 75;

    private void renderOverlay(GuiGraphics guiGraphics, Window window, int aureal, int limit) {
        int xSize = Math.toIntExact(Math.round(BAR_WIDTH * ((float) aureal / limit)));
        int startOffset = BAR_WIDTH - xSize;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, window.getGuiScaledWidth() / 2 + 13 + startOffset, window.getGuiScaledHeight() - 25 - 23, 21 + startOffset, 19, xSize, 7, 256, 128);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Window window = Minecraft.getInstance().getWindow();
        Player player = Minecraft.getInstance().player;

        if (player == null || !player.getMainHandItem().has(ModDataComponents.SHOWS_AUREAL_METER.get())) {
            return;
        }

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, window.getGuiScaledWidth() / 2 + 10, window.getGuiScaledHeight() - 25 - 24, 18, 9, 81, 9, 256, 128);

        EssenceHelper.getEssenceProvider(player).ifPresent(provider -> {
            this.renderOverlay(guiGraphics, window, provider.getAmount(EssenceType.AUREAL), provider.getLimit(EssenceType.AUREAL));
        });
    }
}

