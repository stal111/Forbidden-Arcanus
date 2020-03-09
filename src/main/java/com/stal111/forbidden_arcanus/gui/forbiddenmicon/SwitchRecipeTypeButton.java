package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.ButtonObject;
import com.stal111.forbidden_arcanus.gui.ForbiddenmiconScreen;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;

public class SwitchRecipeTypeButton extends ButtonObject {

    private final String hoverText;
    private final RecipePreviewObject.RecipeCategory recipeCategory;

    public SwitchRecipeTypeButton(RecipePreviewObject.RecipeCategory recipeCategory, int blitOffset, int startX, int startY, IPressable onPress, String hoverText) {
        super(0, 0, blitOffset, startX, startY, 14, 15, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
        this.recipeCategory = recipeCategory;
        this.hoverText = hoverText;
    }

    @Override
    public void render(int x, int y) {
        if (isActivated()) {
            bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            blit(getBlitOffset(), getStartX() - 18, getStartY(), 14, 17, 256, 512);
        } else {
            super.render(x, y);
        }
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        int i = isActivated() ? 17 : 15;
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + 14 && y < getPosY() + i) {
            renderFancyTooltip(Collections.singletonList(new TranslationTextComponent("forbiddenmicon.recipe." + hoverText).getFormattedText()), x, y);
        }
    }

    public RecipePreviewObject.RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }
}
