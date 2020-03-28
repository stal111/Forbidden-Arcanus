package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.gui.ButtonObject;
import com.stal111.forbidden_arcanus.gui.ForbiddenmiconScreen;

public class SwitchRecipeButton extends ButtonObject {

    private boolean pressable = true;

    public SwitchRecipeButton(int posX, int posY, int blitOffset, int startX, int startY, IPressable onPress) {
        super(posX, posY, blitOffset, startX, startY, 9, 11, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
    }

    @Override
    public void render(int x, int y) {
        if (!isPressable()) {
            bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            blit(getBlitOffset(), getStartX(), getStartY() + 16, 9, 11, 256, 512);
        } else {
            super.render(x, y);
        }
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + 8 && y < getPosY() + 11) {
            if (isPressable()) {
                bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
                blit(getBlitOffset(), getStartX(), getStartY() - 16, 9, 11, 256, 512);
            }
        }
        super.renderHoverEffect(x, y);
    }

    public boolean isPressable() {
        return pressable;
    }

    public void setPressable(boolean pressable) {
        this.pressable = pressable;
    }
}
