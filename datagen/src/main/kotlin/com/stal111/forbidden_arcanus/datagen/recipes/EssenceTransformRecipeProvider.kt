package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.essence.EssenceType
import com.stal111.forbidden_arcanus.common.essence.EssenceValue
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.datagen.recipes.builder.EssenceTransformRecipeBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class EssenceTransformRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput
) : RecipeSubProvider(provider, recipeOutput) {

    override fun buildRecipes() {
        add(
            EssenceTransformRecipeBuilder(
                input = Ingredient.of(Items.DEAD_BUSH),
                essence = EssenceValue(EssenceType.ECTOPLASM, 100),
                result = ItemStackTemplate(ModBlocks.GROWING_EDELWOOD.get().asItem())
            )
        )
    }
}