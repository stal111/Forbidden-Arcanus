package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button;

import com.stal111.forbidden_arcanus.gui.element.button.ButtonElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.RecipePreviewElement;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;

public class ChangeRecipeTypeButton extends ButtonElement {

    private final String hoverText;
    private final RecipePreviewElement.RecipeCategory recipeCategory;

    public ChangeRecipeTypeButton(RecipePreviewElement.RecipeCategory recipeCategory, int blitOffset, int startX, int startY, IPressable onPress, String hoverText) {
        super(0, 0, blitOffset, startX, startY, 14, 15, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
        this.recipeCategory = recipeCategory;
        this.hoverText = hoverText;
    }

    @Override
    public String getName() {
        return "change_recipe_type_button";
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

    public RecipePreviewElement.RecipeCategory getRecipeCategory() {
        return recipeCategory;
    }
}
