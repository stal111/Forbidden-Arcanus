package com.stal111.forbidden_arcanus.common.block.entity.clibano.logic;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
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

    public abstract void tick(boolean isLit);

    public abstract boolean canSmelt();

    public abstract void onFireTypeChange(ClibanoFireType fireType);
    public abstract void resetCookingProgress(int slot);

    public abstract void updateRecipes(List<RecipeHolder<ClibanoRecipe>> recipeHolders);
}
