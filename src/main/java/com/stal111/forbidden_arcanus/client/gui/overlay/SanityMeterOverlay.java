package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.AurealProvider;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

import java.util.Collections;

/**
 * @author stal111
 * @since 2022-02-14
 */
public class SanityMeterOverlay implements IGuiOverlay {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/hud.png");

    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
        Window window = Minecraft.getInstance().getWindow();
        Player player = Minecraft.getInstance().player;

        if (player == null || !player.getInventory().hasAnyOf(Collections.singleton(ModItems.SANITY_METER.get()))) {
            return;
        }

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 - 8, window.getGuiScaledHeight() - 39 - 14, 0, 0, 16, 16, 256, 128);

        AurealProvider provider = player.getCapability(AurealProvider.ENTITY_AUREAL);

        if (provider != null) {
            this.renderOverlay(guiGraphics, window, provider.getAureal());
        }
    }

    private void renderOverlay(GuiGraphics guiGraphics, Window window, int aureal) {
        int ySize = Math.toIntExact(Math.round(11 * aureal / 200.0F));

        guiGraphics.blit(TEXTURE, window.getGuiScaledWidth() / 2 - 6, window.getGuiScaledHeight() - 39 - ySize, 2, 30 - ySize, 12, ySize, 256, 128);
    }
}
