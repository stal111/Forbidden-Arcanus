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

/**
 * @author stal111
 * @since 2022-02-14
 */
public class SanityMeterOverlay implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        Player player = minecraft.player;

        if (player == null || !player.getInventory().hasAnyOf(Collections.singleton(ModItems.SANITY_METER.get()))) {
            return;
        }

        IAureal aureal = AurealHelper.getCapability(player);

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 - 8, window.getGuiScaledHeight() - 39 - 14, 0, 0, 16, 16, 256, 128);

        this.renderOverlay(guiGraphics, window, aureal, false);
        this.renderOverlay(guiGraphics, window, aureal, true);
    }

    private void renderOverlay(GuiGraphics guiGraphics, Window window, IAureal aureal, boolean corruption) {
        int ySize = Math.toIntExact(Math.round(11 * (corruption ? aureal.getCorruption() / 100.0F : aureal.getAureal() / 200.0F)));

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 - 6, window.getGuiScaledHeight() - 39 - ySize, 2, (corruption ? 46 : 30) - ySize, 12, ySize, 256, 128);
    }
}
