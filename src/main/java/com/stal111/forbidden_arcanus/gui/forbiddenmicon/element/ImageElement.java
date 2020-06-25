package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element;

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
    public void render(int x, int y) {
        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        blit(300, 275, 29, getSizeX(), getSizeY(), 256, 512);
    }
}
