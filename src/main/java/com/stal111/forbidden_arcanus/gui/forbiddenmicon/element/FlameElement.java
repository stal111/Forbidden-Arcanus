package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element;

import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class FlameElement extends GuiElement {

    private int cookingTime;
    private float experience;
    private final FireType fireType;

    public FlameElement(int posX, int posY, int cookingTime, float experience, FireType fireType) {
        super(posX, posY, 10, 13);
        this.cookingTime = cookingTime;
        this.experience = experience;
        this.fireType = fireType;
    }

    @Override
    public void render(int x, int y) {
        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        int i = fireType == FireType.FIRE ? 328 : 340;
        blit(300, i, 242, getSizeX(), getSizeY(), 256, 512);
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + 10 && y < getPosY() + 13) {
            List<String> list = new ArrayList<>();
            list.add(new TranslationTextComponent("forbiddenmicon.recipe.cookingTime").getFormattedText() + ": " + cookingTime);
            list.add(new TranslationTextComponent("forbiddenmicon.recipe.experience").getFormattedText() + ": " + experience);
            renderFancyTooltip(list, x, y);
        }
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public enum FireType {
        FIRE,
        SOUL_FIRE;
    }
}
