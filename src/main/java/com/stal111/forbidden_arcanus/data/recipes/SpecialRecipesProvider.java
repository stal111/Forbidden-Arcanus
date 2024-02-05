package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.common.recipe.CombineAurealTankRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.valhelsia.valhelsia_core.datagen.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.datagen.recipes.ValhelsiaRecipeProvider;

import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 24.09.2023
 */
public class SpecialRecipesProvider extends RecipeSubProvider {

    public SpecialRecipesProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
    }

    @Override
    protected void registerRecipes(CompletableFuture<HolderLookup.Provider> lookupProvider) {
        SpecialRecipeBuilder.special(CombineAurealTankRecipe::new).save(this.getRecipeOutput(), "combine_aureal_tank");
    }
}
