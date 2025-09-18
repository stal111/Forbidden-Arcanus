package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.item.crafting.CombineAurealTankRecipe
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.SpecialRecipeBuilder
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.valhelsia.dataforge.recipe.RecipeSubProvider
import java.util.function.Function

class SpecialRecipesProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput
) : RecipeSubProvider(provider, recipeOutput) {
    override fun buildRecipes() {
        SpecialRecipeBuilder.special { CombineAurealTankRecipe(it) }.save(this.recipeOutput, "combine_aureal_tank")
    }
}
