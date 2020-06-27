package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;

public class ImageElement extends GuiElement {

    public ImageElement(int posX, int posY) {
        super(posX, posY, 113, 65);
    }

    @Override
    public String getName() {
        return "image";
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        blit(matrixStack, 300, 275, 29, getSizeX(), getSizeY(), 256, 512);
    }
}
