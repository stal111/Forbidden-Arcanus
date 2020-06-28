package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;
import net.minecraft.util.text.*;

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
    public String getName() {
        return "flame";
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        int i = fireType == FireType.FIRE ? 328 : 340;
        blit(matrixStack, 300, i, 242, getSizeX(), getSizeY(), 256, 512);
    }

    @Override
    public void renderHoverEffect(MatrixStack matrixStack, int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + 10 && y < getPosY() + 13) {
            List<ITextComponent> list = new ArrayList<>();
            list.add(new TranslationTextComponent("forbiddenmicon.recipe.cookingTime").func_240702_b_(": " + cookingTime));
            list.add(new TranslationTextComponent("forbiddenmicon.recipe.experience").func_240702_b_(": " + experience));
            renderFancyTooltip(matrixStack, list, x, y);
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
