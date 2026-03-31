package com.stal111.forbidden_arcanus.client.gui.screen.animation;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class ScreenAnimation implements Renderable {

    private static final float DEFAULT_FRAME_DURATION = 1.5F;

    private final Identifier[] textures;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final float frameDuration;

    private int animationTick = 0;
    private boolean active = false;

    public ScreenAnimation(int x, int y, int width, int height, Identifier texture, int frames) {
        this(x, y, width, height, texture, frames, DEFAULT_FRAME_DURATION);
    }

    public ScreenAnimation(int x, int y, int width, int height, Identifier texture, int frames, float frameDuration) {
        this.textures = new Identifier[frames];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frameDuration = frameDuration;

        for (int i = 0; i < frames; i++) {
            this.textures[i] = texture.withSuffix("_" + i);
        }
    }

    public void start() {
        this.animationTick = 0;
        this.active = true;
    }

    public void tick() {
        if (!this.active) {
            return;
        }
        this.animationTick++;

        if (this.animationTick >= this.frameDuration * this.textures.length) {
            this.active = false;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.active) {
            return;
        }
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.textures[(int) (this.animationTick / this.frameDuration)], this.x, this.y, this.width, this.height);
    }
}
