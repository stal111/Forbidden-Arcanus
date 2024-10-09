package com.stal111.forbidden_arcanus.common.block.entity.clibano.logic;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoInputSlot;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 21.02.2024
 */
public class DoubleSmeltLogic extends ClibanoSmeltLogic {

    @Nullable
    private RecipeHolder<ClibanoRecipe> recipe;

    public DoubleSmeltLogic(ClibanoAccessor clibano, @Nullable RecipeHolder<ClibanoRecipe> recipe) {
        super(clibano);
        this.recipe = recipe;
    }

    @Override
    public void tick(boolean isLit) {
        super.tick(isLit);

        if (this.canSmelt()) {
            this.cookingDuration[0] = this.clibano.getCookingTime(this.recipe);
            this.cookingDuration[1] = this.clibano.getCookingTime(this.recipe);

            this.cookingProgress[0]++;
            this.cookingProgress[1]++;

            if (this.cookingProgress[0] == this.cookingDuration[0]) {
                this.clibano.finishRecipe(this.recipe, ClibanoInputSlot.BOTH);
            }
        } else {
            this.resetCookingProgress(0);
            this.resetCookingProgress(1);
        }
    }

    @Override
    public boolean canSmelt() {
        return this.clibano.canSmelt(this.recipe, ClibanoInputSlot.BOTH);
    }

    @Override
    public void onFireTypeChange(ClibanoFireType fireType) {
        this.updateCookingProgress(fireType, this.recipe, 0);
        this.updateCookingProgress(fireType, this.recipe, 1);
    }

    @Override
    public void updateRecipes(List<RecipeHolder<ClibanoRecipe>> recipeHolders) {
        this.recipe = recipeHolders.isEmpty() ? null : recipeHolders.get(0);
    }
}
