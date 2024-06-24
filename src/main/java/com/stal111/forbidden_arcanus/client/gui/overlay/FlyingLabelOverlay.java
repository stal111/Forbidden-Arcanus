package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.client.gui.label.BlockFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.EntityFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.FlyingLabel;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class FlyingLabelOverlay implements LayeredDraw.Layer {

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        HitResult hitResult = minecraft.hitResult;
        ItemStack stack = minecraft.player.getMainHandItem();

        int centerX = minecraft.getWindow().getGuiScaledWidth() / 2;
        int centerY = minecraft.getWindow().getGuiScaledHeight() / 2;

        if (hitResult instanceof BlockHitResult result) {
            this.renderLabels(BlockFlyingLabel.class, guiGraphics, stack, deltaTracker, centerX, centerY, result);
        } else if (hitResult instanceof EntityHitResult result) {
            this.renderLabels(EntityFlyingLabel.class, guiGraphics, stack, deltaTracker, centerX, centerY, result);
        }
    }

    private <R extends HitResult> void renderLabels(Class<? extends FlyingLabel<R>> clazz, GuiGraphics guiGraphics, ItemStack stack, DeltaTracker deltaTracker, int centerX, int centerY, R result) {
        ClientSetup.FLYING_LABELS.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .forEach(flyingLabel -> flyingLabel.render(guiGraphics, stack, deltaTracker, centerX, centerY, result));
    }
}
