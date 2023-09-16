package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Collections;

public class AurealMeterOverlay implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

    private static final int BAR_WIDTH = 75;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Window window = Minecraft.getInstance().getWindow();
        Player player = Minecraft.getInstance().player;

        if (player == null || !player.getInventory().hasAnyOf(Collections.singleton(ModItems.SANITY_METER.get()))) {
            return;
        }
        IAureal aureal = AurealHelper.getCapability(player);

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 + 10, window.getGuiScaledHeight() - 25 - 24, 18, 9, 81, 9, 256, 128);

        this.renderOverlay(guiGraphics, window, aureal);
    }

    private void renderOverlay(GuiGraphics guiGraphics, Window window, IAureal aureal) {
        int xSize = Math.toIntExact(Math.round(BAR_WIDTH * ((aureal.getAureal()) / 200.0F)));
        int startOffset = BAR_WIDTH - xSize;

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 + 13 + startOffset, window.getGuiScaledHeight() - 25 - 23, 21 + startOffset, 19, xSize, 7, 256, 128);
    }
}

