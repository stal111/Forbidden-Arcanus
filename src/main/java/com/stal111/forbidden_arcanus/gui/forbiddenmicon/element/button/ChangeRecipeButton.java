package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.element.button.ButtonElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;

public class ChangeRecipeButton extends ButtonElement {

    private boolean pressable = true;

    public ChangeRecipeButton(int posX, int posY, int blitOffset, int startX, int startY, IPressable onPress) {
        super(posX, posY, blitOffset, startX, startY, 9, 11, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
    }

    @Override
    public String getName() {
        return "change_recipe_button";
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        if (!isPressable()) {
            bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            blit(matrixStack, getBlitOffset(), getStartX(), getStartY() + 16, 9, 11, 256, 512);
        } else {
            super.render(matrixStack, x, y);
        }
    }

    @Override
    public void renderHoverEffect(MatrixStack matrixStack, int x, int y) {
        if (isMouseOver(x, y)) {
            if (isPressable()) {
                bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
                blit(matrixStack, getBlitOffset(), getStartX(), getStartY() - 16, 9, 11, 256, 512);
            }
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
