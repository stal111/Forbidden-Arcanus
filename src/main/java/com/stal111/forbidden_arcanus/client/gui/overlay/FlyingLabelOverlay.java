package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.client.gui.label.BlockFlyingLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class FlyingLabelOverlay implements LayeredDraw.Layer {

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, float partialTick) {
        if (Minecraft.getInstance().hitResult instanceof BlockHitResult result) {
            ClientSetup.FLYING_LABELS.forEach(flyingLabel -> {
                if (flyingLabel instanceof BlockFlyingLabel label) {
                    if (label.shouldRender(result)) {
                        int centerX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
                        int centerY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;

                        label.render(guiGraphics, partialTick, centerX, centerY);
                    }
                }
            });
        }
    }
}
