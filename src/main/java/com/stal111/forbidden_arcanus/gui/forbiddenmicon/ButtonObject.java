package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.GuiObject;
import net.minecraft.util.ResourceLocation;

public class ButtonObject extends GuiObject {

    private final int sizeX;
    private final int sizeY;

    private final int startX;
    private final int startY;

    private final int blitOffset;

    private final ResourceLocation resourceLocation;

    private boolean activated = false;

    private final IPressable onPress;

    public ButtonObject(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, ResourceLocation resourceLocation, IPressable onPress) {
        super(posX, posY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.startX = startX;
        this.startY = startY;
        this.blitOffset = blitOffset;
        this.resourceLocation = resourceLocation;
        this.onPress = onPress;
    }

    @Override
    public void render(int x, int y) {
        bindTexture(resourceLocation);
        blit(blitOffset, startX, startY, sizeX, sizeY, 256, 512);
    }

    @Override
    public void renderHoverEffect(int x, int y) {

    }

    @Override
    public boolean onClicked(double x, double y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + sizeX && y < getPosY() + sizeY) {
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
