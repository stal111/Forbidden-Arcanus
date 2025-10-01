package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.MagicWandItem;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class WandIndicatorLayer implements GuiLayer {

    private static final ResourceLocation CROSSHAIR_WAND_INDICATOR_FULL_SPRITE = ForbiddenArcanus.location("hud/crosshair_wand_indicator_full");
    private static final ResourceLocation CROSSHAIR_WAND_INDICATOR_BACKGROUND_SPRITE = ForbiddenArcanus.location("hud/crosshair_wand_indicator_background");
    private static final ResourceLocation CROSSHAIR_WAND_INDICATOR_PROGRESS_SPRITE = ForbiddenArcanus.location("hud/crosshair_wand_indicator_progress");

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null || !player.getMainHandItem().is(ModItems.MAGIC_WAND)) {
            return;
        }

        int indicatorXPosition = guiGraphics.guiWidth() / 2 - 8;
        int indicatorYPosition = guiGraphics.guiHeight() / 2 - 7 + 16;

        float progressRatio = MagicWandItem.getUseProgress(player.getMainHandItem(), player);

        if (progressRatio >= 1.0F) {
            guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_WAND_INDICATOR_FULL_SPRITE, indicatorXPosition, indicatorYPosition, 16, 16);
            return;
        }

        guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_WAND_INDICATOR_BACKGROUND_SPRITE, indicatorXPosition, indicatorYPosition, 16, 4);

        int progressWidth = (int)(progressRatio * 17.0F);
        guiGraphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_WAND_INDICATOR_PROGRESS_SPRITE, 16, 4, 0, 0, indicatorXPosition, indicatorYPosition, progressWidth, 4);
    }
}
