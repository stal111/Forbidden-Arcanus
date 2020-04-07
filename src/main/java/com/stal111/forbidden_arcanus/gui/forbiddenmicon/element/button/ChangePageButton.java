package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button;

import com.stal111.forbidden_arcanus.gui.element.button.ButtonElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;

public class ChangePageButton extends ButtonElement {

    private boolean pressable = true;

    public ChangePageButton(int posX, int posY, int blitOffset, int startX, int startY, IPressable onPress) {
        super(posX, posY, blitOffset, startX, startY, 14, 10, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (isMouseOver(x, y)) {
            bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            blit(getBlitOffset(), getStartX() + 52, getStartY(), getSizeX(), getSizeY(), 256, 512);
        }
    }

    @Override
    public boolean onClicked(double x, double y) {
        if (isPressable()) {
            super.onClicked(x, y);
        }
        return false;
    }

    public boolean isPressable() {
        return pressable;
    }

    public void setPressable(boolean pressable) {
        this.pressable = pressable;
    }
}
