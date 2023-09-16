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

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Window window = Minecraft.getInstance().getWindow();
        Player player = Minecraft.getInstance().player;

        if (player == null || !player.getInventory().hasAnyOf(Collections.singleton(ModItems.SANITY_METER.get()))) {
            return;
        }
        IAureal aureal = AurealHelper.getCapability(player);

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 + 9, window.getGuiScaledHeight() - 25 - 23, 17, 9, 81, 10, 256, 128);

        this.renderOverlay(guiGraphics, window, aureal);
        this.renderOverlay(guiGraphics, window, aureal);
    }
    private void renderOverlay(GuiGraphics guiGraphics, Window window, IAureal aureal) {
        int xSize = Math.toIntExact(Math.round(80.0F * ((aureal.getAureal()) / 200.0F)));
        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 + 11,window.getGuiScaledHeight() - 25 - 23, 19, 18,xSize, 18, 256, 128);
    }
}

