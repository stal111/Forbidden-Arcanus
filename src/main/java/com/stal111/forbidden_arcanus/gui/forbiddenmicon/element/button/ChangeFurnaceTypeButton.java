package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button;

import com.stal111.forbidden_arcanus.gui.element.button.ButtonElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;

public class ChangeFurnaceTypeButton extends ButtonElement {

    private final FurnaceType furnaceType;

    public ChangeFurnaceTypeButton(int posX, int posY, int blitOffset, FurnaceType furnaceType, IPressable onPress) {
        super(posX, posY, blitOffset, 417, 161, 14, 15, ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES, onPress);
        this.furnaceType = furnaceType;
    }

    @Override
    public String getName() {
        return "change_furnace_type_button";
    }

    @Override
    public void render(int x, int y) {
        int i = getStartX() + (furnaceType == FurnaceType.SMOKER ? -16 : furnaceType == FurnaceType.BLAST_FURNACE ? 16 : 0);

        bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
        blit(getBlitOffset(), i, isActivated() ? getStartY() - 16 : getStartY(), 14, 15, 256, 512);
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + getSizeX() && y < getPosY() + getSizeY()) {
            renderFancyTooltip(Collections.singletonList(new TranslationTextComponent("forbiddenmicon.recipe.smelting." + getFurnaceType().toString().toLowerCase()).getFormattedText()), x, y);
        }
    }

    public FurnaceType getFurnaceType() {
        return furnaceType;
    }

    public enum FurnaceType {
        FURNACE(IRecipeSerializer.SMELTING, IRecipeType.SMELTING),
        SMOKER(IRecipeSerializer.SMOKING, IRecipeType.SMOKING),
        BLAST_FURNACE(IRecipeSerializer.BLASTING, IRecipeType.BLASTING);

        private final IRecipeSerializer<?> recipeSerializer;
        private final IRecipeType<AbstractCookingRecipe> recipeType;

        FurnaceType(IRecipeSerializer<?> recipeSerializer, IRecipeType<?> recipeType) {
            this.recipeSerializer = recipeSerializer;
            this.recipeType = (IRecipeType<AbstractCookingRecipe>) recipeType;
        }

        public IRecipeSerializer<?> getRecipeSerializer() {
            return recipeSerializer;
        }

        public IRecipeType<AbstractCookingRecipe> getRecipeType() {
            return recipeType;
        }
    }
}
