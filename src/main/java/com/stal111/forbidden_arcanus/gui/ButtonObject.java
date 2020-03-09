package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.ResourceLocation;

public class ButtonObject extends GuiObject {

    private final int startX;
    private final int startY;

    private final int blitOffset;

    private final ResourceLocation resourceLocation;

    private boolean activated = false;

    private final IPressable onPress;

    public ButtonObject(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, ResourceLocation resourceLocation, IPressable onPress) {
        super(posX, posY, sizeX, sizeY);
        this.startX = startX;
        this.startY = startY;
        this.blitOffset = blitOffset;
        this.resourceLocation = resourceLocation;
        this.onPress = onPress;
    }

    @Override
    public void render(int x, int y) {
        bindTexture(resourceLocation);
        blit(blitOffset, startX, startY, getSizeX(), getSizeY(), 256, 512);
    }

    @Override
    public boolean onClicked(double x, double y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + getSizeX() && y < getPosY() + getSizeY()) {
            onPress.onPress(this);
            return true;
        }
        return false;
    }

    public ButtonObject setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getBlitOffset() {
        return blitOffset;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public interface IPressable {
        void onPress(ButtonObject buttonObject);
    }
}
