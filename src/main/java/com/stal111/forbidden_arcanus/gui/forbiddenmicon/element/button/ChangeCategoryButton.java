package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.element.button.ButtonElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconCategory;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;

public class ChangeCategoryButton extends ButtonElement {

    private final ForbiddenmiconCategory category;
    private final boolean leftSide;

    public ChangeCategoryButton(int posX, int posY, int blitOffset, ForbiddenmiconCategory category, boolean leftSide, IPressable onPress) {
        super(posX, posY, blitOffset, 130, 220, 18, 14, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
        this.category = category;
        this.leftSide = leftSide;
    }

    @Override
    public String getName() {
        return "change_category_button";
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        int startX = 130;
        int startY = 220;
        int sizeX = 18;
        int sizeY = 14;

        if (!leftSide) {
            startX -= 22;
        }
        if (isActivated()) {
            startY -= 32;
            sizeX = 24;
        }

        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        blit(matrixStack, getBlitOffset(), startX, startY, sizeX, sizeY, 256, 512);
        blit(matrixStack, getPosX() + 7, getPosY() + 2, getBlitOffset() + 1, getCategory().getStartX(), getCategory().getStartY(), 8, 8, 256, 512);
    }

    @Override
    public void renderHoverEffect(MatrixStack matrixStack, int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + (isActivated() ? 24 : 18) && y < getPosY() + getSizeY()) {
            if (!isActivated()) {
                bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
                blit(matrixStack, getBlitOffset(), getStartX(), getStartY() - 16, 24, 14, 256, 512);
                blit(matrixStack, getPosX() + 7, getPosY() + 2, getBlitOffset() + 1, getCategory().getStartX(), getCategory().getStartY(), 8, 8, 256, 512);
            }
            renderFancyTooltip(matrixStack, Collections.singletonList(new TranslationTextComponent("forbiddenmicon.category." + getCategory().toString().toLowerCase())), x, y);
        }
    }

    public ForbiddenmiconCategory getCategory() {
        return category;
    }
}
