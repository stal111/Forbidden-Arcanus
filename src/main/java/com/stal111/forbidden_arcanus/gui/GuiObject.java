package com.stal111.forbidden_arcanus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiObject {

    private final int posX;
    private final int posY;

    private final Minecraft minecraft = Minecraft.getInstance();

    private static List<GuiObject> objects = new ArrayList<>();

    public GuiObject(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {
        objects.forEach(GuiObject::init);
    }

    public abstract void render(int x, int y);

    public void renderHoverEffect(int x, int y) {
    }

    public boolean onClicked(double x, double y) {
        return false;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public static List<GuiObject> getObjects() {
        return objects;
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        getMinecraft().getTextureManager().bindTexture(resourceLocation);
    }

    public void blit(int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        blit(getPosX(), getPosY(), blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }

    public void blit(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        AbstractGui.blit(posX, posY, blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }
}
