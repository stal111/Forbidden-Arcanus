package com.stal111.forbidden_arcanus.common.block.entity.clibano.logic;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoInputSlot;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 17.02.2024
 */
public class DefaultSmeltLogic extends ClibanoSmeltLogic {

    @Nullable
    private RecipeHolder<ClibanoRecipe> firstRecipe;
    @Nullable
    private RecipeHolder<ClibanoRecipe> secondRecipe;

    public DefaultSmeltLogic(ClibanoAccessor clibano, @Nullable RecipeHolder<ClibanoRecipe> firstRecipe, @Nullable RecipeHolder<ClibanoRecipe> secondRecipe) {
        super(clibano);
        this.firstRecipe = firstRecipe;
        this.secondRecipe = secondRecipe;
    }

    @Override
    public void tick(boolean isLit) {
        if (!isLit) {
            this.cookingProgress[0] = Mth.clamp(0, this.cookingProgress[0] - 2, this.cookingDuration[0]);
            this.cookingProgress[1] = Mth.clamp(0, this.cookingProgress[1] - 2, this.cookingDuration[1]);
        }

        if (this.clibano.canSmelt(this.firstRecipe, ClibanoInputSlot.FIRST)) {
            this.cookingDuration[0] = this.clibano.getCookingTime(this.firstRecipe);

            this.cookingProgress[0]++;

            if (this.cookingProgress[0] == this.cookingDuration[0]) {
                this.clibano.finishRecipe(this.firstRecipe, ClibanoInputSlot.FIRST);
            }
        } else {
            this.resetCookingProgress(0);
        }

        if (this.clibano.canSmelt(this.secondRecipe, ClibanoInputSlot.SECOND)){
            this.cookingDuration[1] = this.clibano.getCookingTime(this.secondRecipe);

            this.cookingProgress[1]++;

            if (this.cookingProgress[1] == this.cookingDuration[1]) {
                this.clibano.finishRecipe(this.secondRecipe, ClibanoInputSlot.SECOND);
            }
        } else {
            this.resetCookingProgress(1);
        }
    }

    @Override
    public boolean canSmelt() {
        return this.clibano.canSmelt(this.firstRecipe, ClibanoInputSlot.FIRST) || this.clibano.canSmelt(this.secondRecipe, ClibanoInputSlot.SECOND);
    }

    @Override
    public void onFireTypeChange(ClibanoFireType fireType) {
        this.updateCookingProgress(fireType, this.firstRecipe, 0);
        this.updateCookingProgress(fireType, this.secondRecipe, 1);
    }

    @Override
    public void resetCookingProgress(int slot) {
        this.cookingProgress[slot] = 0;
    }

    @Override
    public final void updateRecipes(List<RecipeHolder<ClibanoRecipe>> recipeHolders) {
        this.updateRecipe(0, this.firstRecipe, recipeHolders);
        this.updateRecipe(1, this.secondRecipe, recipeHolders);
    }

    private void updateRecipe(int slot, @Nullable RecipeHolder<ClibanoRecipe> previousRecipe, List<RecipeHolder<ClibanoRecipe>> recipeHolders) {
        if (recipeHolders.size() > slot && (previousRecipe == null || !previousRecipe.equals(recipeHolders.get(slot)))) {
            if (slot == 0) {
                this.firstRecipe = recipeHolders.get(slot);
            } else {
                this.secondRecipe = recipeHolders.get(slot);
            }
            this.resetCookingProgress(slot);
        }
    }

    private void updateCookingProgress(ClibanoFireType fireType, RecipeHolder<ClibanoRecipe> recipeHolder, int slot) {
        if (recipeHolder != null) {
            int oldDuration = this.cookingDuration[slot];

            this.cookingDuration[slot] = recipeHolder.value().getCookingTime(fireType);
            this.cookingProgress[slot] = (int) (((float) this.cookingProgress[slot] / oldDuration) * this.cookingDuration[slot]);
        }
    }
}
