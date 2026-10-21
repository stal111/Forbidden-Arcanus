package com.stal111.forbidden_arcanus.client.gui.overlay;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.client.gui.label.BlockFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.EntityFlyingLabel;
import com.stal111.forbidden_arcanus.client.gui.label.FlyingLabel;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlyingLabelOverlay implements GuiLayer {

    public static final int Y_OFFSET = -20;
    public static final int BACKGROUND_COLOR = 0x66000000;
    public static final int BORDER_COLOR = 0x44000000;
    public static final int TEXT_COLOR = 0xFFFFFFFF;

    public static final int HORIZONTAL_PADDING = 5;
    public static final int VERTICAL_PADDING = 3;
    public static final int LINE_SPACING = 3;

    private static final float FADE_SPEED = 0.35F;

    private float currentAlpha = 0.0F;
    private List<Component> lastComponents = new ArrayList<>();

    @Override
    public void render(@NotNull GuiGraphicsExtractor guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.options.hideGui || minecraft.player == null || minecraft.level == null || minecraft.screen != null) {
            this.currentAlpha = 0.0F;
            this.lastComponents = List.of();
            return;
        }

        HitResult hitResult = minecraft.hitResult;
        ItemStack stack = minecraft.player.getMainHandItem();

        int centerX = guiGraphics.guiWidth() / 2;
        int centerY = guiGraphics.guiHeight() / 2;

        List<Component> components = new ArrayList<>();

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            if (hitResult instanceof BlockHitResult result) {
                components = this.collectComponents(BlockFlyingLabel.class, stack, result);
            } else if (hitResult instanceof EntityHitResult result) {
                components = this.collectComponents(EntityFlyingLabel.class, stack, result);
            }
        }

        float delta = Math.min(deltaTracker.getRealtimeDeltaTicks(), 3.0F);

        if (!components.isEmpty()) {
            this.currentAlpha = Math.min(1.0F, this.currentAlpha + FADE_SPEED * delta);
            this.lastComponents = components;
        } else {
            this.currentAlpha = Math.max(0.0F, this.currentAlpha - FADE_SPEED * delta);
        }

        if (this.currentAlpha > 0.001F && !this.lastComponents.isEmpty()) {
            float renderAlpha = this.currentAlpha * this.currentAlpha * (3.0F - 2.0F * this.currentAlpha);

            renderComponents(guiGraphics, this.lastComponents, centerX, centerY, renderAlpha);
        } else if (this.currentAlpha <= 0.001F) {
            this.lastComponents = List.of();
        }
    }

    public static void renderComponents(GuiGraphicsExtractor guiGraphics, List<Component> components, int centerX, int centerY, float alpha) {
        if (components.isEmpty() || alpha <= 0.01F) {
            return;
        }

        Font font = Minecraft.getInstance().font;

        int maxWidth = 0;
        for (Component line : components) {
            int width = font.width(line);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        int lineHeight = font.lineHeight;
        int totalTextHeight = components.size() * lineHeight + (components.size() - 1) * LINE_SPACING;

        int boxWidth = maxWidth + HORIZONTAL_PADDING * 2;
        int boxHeight = totalTextHeight + VERTICAL_PADDING * 2;

        int left = centerX - boxWidth / 2;
        int top = centerY + Y_OFFSET - VERTICAL_PADDING;
        int right = left + boxWidth;
        int bottom = top + boxHeight;

        renderBackground(guiGraphics, left, top, right, bottom, BACKGROUND_COLOR, alpha);

        int textAlphaColor = ARGB.multiplyAlpha(TEXT_COLOR, alpha);
        int textX = left + HORIZONTAL_PADDING;
        int currentY = centerY + Y_OFFSET;

        for (Component line : components) {
            guiGraphics.text(font, line, textX, currentY, textAlphaColor);
            currentY += lineHeight + LINE_SPACING;
        }
    }

    public static void renderBackground(GuiGraphicsExtractor guiGraphics, int left, int top, int right, int bottom, int backgroundColor, float alpha) {
        if (alpha <= 0.01F) {
            return;
        }

        int bg = ARGB.multiplyAlpha(backgroundColor, alpha);
        int border = ARGB.multiplyAlpha(BORDER_COLOR, alpha);

        guiGraphics.fill(left - 1, top + 1, right + 1, bottom - 1, border);
        guiGraphics.fill(left + 1, top - 1, right - 1, bottom + 1, border);

        guiGraphics.fill(left, top, right, bottom, bg);
    }

    private <R extends HitResult> List<Component> collectComponents(Class<? extends FlyingLabel<R>> clazz, ItemStack stack, R result) {
        Minecraft minecraft = Minecraft.getInstance();

        List<Component> components = new ArrayList<>();

        ClientSetup.FLYING_LABELS.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .forEach(flyingLabel -> flyingLabel.getComponents(minecraft.level, minecraft.player, stack, result, components::add));

        return components;
    }
}
