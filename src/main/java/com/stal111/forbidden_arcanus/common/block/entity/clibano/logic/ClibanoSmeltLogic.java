package com.stal111.forbidden_arcanus.common.block.entity.clibano.logic;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

/**
 * @author stal111
 * @since 17.02.2024
 */
public abstract class ClibanoSmeltLogic {

    public final ClibanoAccessor clibano;

    public int[] cookingProgress = new int[2];
    public int[] cookingDuration = new int[2];

    protected ClibanoSmeltLogic(ClibanoAccessor clibano) {
        this.clibano = clibano;
    }

    public void tick(boolean isLit) {
        if (!isLit) {
            this.cookingProgress[0] = Mth.clamp(0, this.cookingProgress[0] - 2, this.cookingDuration[0]);
            this.cookingProgress[1] = Mth.clamp(0, this.cookingProgress[1] - 2, this.cookingDuration[1]);
        }
    }

    public abstract boolean canSmelt();

    public abstract void onFireTypeChange(ClibanoFireType fireType);

    public void resetCookingProgress(int slot) {
        this.cookingProgress[slot] = 0;
    }

    public abstract void updateRecipes(List<RecipeHolder<ClibanoRecipe>> recipeHolders);

    protected void updateCookingProgress(ClibanoFireType fireType, RecipeHolder<ClibanoRecipe> recipeHolder, int slot) {
        if (recipeHolder != null) {
            int oldDuration = this.cookingDuration[slot];

            this.cookingDuration[slot] = recipeHolder.value().getCookingTime(fireType);
            this.cookingProgress[slot] = (int) (((float) this.cookingProgress[slot] / oldDuration) * this.cookingDuration[slot]);
        }
    }
}
