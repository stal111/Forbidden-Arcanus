package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.research.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author stal111
 * @since 23.11.2023
 */
public class KnowledgeWidget extends AbstractWidget {

    private static final ResourceLocation TITLE_BOX = ForbiddenArcanus.location("research/title_box");
    private static final ResourceLocation INFO_BOX = ForbiddenArcanus.location("research/info_box");

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

        if (this.isMouseOver(mouseX, mouseY)) {
            this.renderHover(guiGraphics, mouseX, mouseY);
        }

        guiGraphics.blitSprite(this.display.getFrame().getFrameTexture(this.locked, this.isHoveredOrFocused()), this.getX() + scrollX, this.getY() + scrollY, 26, 26);

        if (!this.locked && !this.unlockAnimation.started) {
            this.display.getIcon().renderIcon(guiGraphics, this.getX() + scrollX + 5, this.getY() + scrollY + 5);
        }

        this.unlockAnimation.render(guiGraphics, this.getX() + scrollX, this.getY() + scrollY);
    }

    private void renderHover(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int width = Math.max(90, 90 + Minecraft.getInstance().font.width(this.display.getTitle()));

        List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(this.display.getDescription(), width - 10);

        for (int i = 0; i < lines.size(); i++) {
           this.renderBox(INFO_BOX, guiGraphics, this.getX() - 3, this.getY() + 22 + i * 10, width, i == lines.size() - 1 ? 20 : 18);
        }

        this.renderBox(TITLE_BOX, guiGraphics, this.getX() - 3, this.getY() + 3, width, 20);

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.display.getTitle(), this.getX() - 3 + width / 2, this.getY() + 10, -1);

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(Minecraft.getInstance().font, lines.get(i), this.getX() - 3 + 5, this.getY() + 28 + i * 10, -1);
        }
    }

    private void renderBox(ResourceLocation texture, GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.blitSprite(texture, 120, 20, 0, 0, x, y, 45, height);

        int x2 = this.getX() - 3 + 45;

        int i = width - 90;

        while (i > 0) {
            int j = Math.min(i, 30);

            guiGraphics.blitSprite(texture, 120, 20, 45, 0, x2, y, j, height);
            i -= j;
            x2 += j;
        }

        guiGraphics.blitSprite(texture, 120, 20, 120 - 45, 0, x + width - 45, y, 45, height);
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
        Minecraft.getInstance().pushGuiLayer(new ConfirmPopupScreen(confirm -> {
            if (confirm) {
                this.locked = false;

                this.unlockAnimation.start();
            }
            Minecraft.getInstance().popGuiLayer();
        }, Component.literal("Unlock?")));
    }

    private static class UnlockAnimation {

        private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
                ForbiddenArcanus.location("research/frame/unlock/unlock_0"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_1"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_2"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_3"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_4"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_5"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_6"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_7"),
                ForbiddenArcanus.location("research/frame/unlock/unlock_8"),
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
