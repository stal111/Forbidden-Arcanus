package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBar;
import com.stal111.forbidden_arcanus.client.gui.components.EssenceBarType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class AurealMeterOverlay implements GuiLayer {

    private static final ResourceLocation BACKGROUND_SPRITE = ForbiddenArcanus.location("hud/aureal_bar_background");

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Window window = Minecraft.getInstance().getWindow();
        MouseHandler mouseHandler = Minecraft.getInstance().mouseHandler;
        Player player = Minecraft.getInstance().player;

        if (player == null || !player.getMainHandItem().has(ModDataComponents.SHOWS_AUREAL_METER.get())) {
            return;
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, 81, 9, 0, 0, window.getGuiScaledWidth() / 2 + 10, window.getGuiScaledHeight() - 25 - 24, 81, 9);

        EssenceHelper.getEssenceAccess(player).ifPresent(essenceAccess -> {
            EssenceBar essenceBar = new EssenceBar(window.getGuiScaledWidth() / 2 + 13, window.getGuiScaledHeight() - 25 - 23, EssenceBarType.PLAYER_AUREAL, () -> essenceAccess.getEssence(EssenceType.AUREAL));

            essenceBar.render(guiGraphics, Mth.floor(mouseHandler.getScaledXPos(window)), Mth.floor(mouseHandler.getScaledYPos(window)), deltaTracker.getGameTimeDeltaPartialTick(true));
        });
    }
}

