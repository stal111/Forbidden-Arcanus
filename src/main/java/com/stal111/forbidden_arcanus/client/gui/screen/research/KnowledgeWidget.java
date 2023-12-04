package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.research.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 23.11.2023
 */
public class KnowledgeWidget extends AbstractWidget {

    private final DisplayInfo display;

    private boolean locked = true;
    private final UnlockAnimation unlockAnimation = new UnlockAnimation();


    public KnowledgeWidget(DisplayInfo display, int x, int y) {
        super(x + display.getRelativeX() * 25, y + display.getRelativeY() * 25, 26, 26, Component.empty());
        this.display = display;
    }

    public void tick() {
        this.unlockAnimation.tick();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int scrollX = 0;
        int scrollY = 0;

        guiGraphics.blitSprite(this.display.getFrame().getFrameTexture(this.locked, this.isHoveredOrFocused()), this.getX() + scrollX + 3, this.getY() + scrollY, 26, 26);

        if (!this.locked && !this.unlockAnimation.started) {
            this.display.getIcon().renderIcon(guiGraphics, this.getX() + scrollX + 8, this.getY() + scrollY + 5);
        }

        this.unlockAnimation.render(guiGraphics, this.getX() + scrollX + 3, this.getY() + scrollY);
    }

    public int calculatePositionX(int x) {
        return x + this.display.getRelativeX() * 25;
    }

    public int calculatePositionY(int y) {
        return y + this.display.getRelativeY() * 25;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.locked = false;

        this.unlockAnimation.start();
    }

    private static class UnlockAnimation {

        private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_0"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_1"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_2"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_3"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_4"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_5"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_6"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_7"),
                new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/unlock/unlock_8"),
        };

        private static final float ANIMATION_SPEED = 1.5F;

        private int animationTick = 0;
        private boolean started = false;

        public void start() {
            this.animationTick = 0;
            this.started = true;
        }

        public void tick() {
            if (!this.started) {
                return;
            }
            this.animationTick++;

            if (this.animationTick >= ANIMATION_SPEED * TEXTURES.length) {
                this.started = false;
            }
        }

        public void render(GuiGraphics guiGraphics, int x, int y) {
            if (!this.started) {
                return;
            }
            guiGraphics.blitSprite(TEXTURES[(int) (this.animationTick / ANIMATION_SPEED)], x - 19, y - 19, 64, 64);
        }
    }
}
