package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.core.init.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.valhelsia.dataforge.recipe.RecipeSubProvider
import net.valhelsia.dataforge.recipe.getName

class StonecutterRecipeProvider(provider: HolderLookup.Provider, recipeOutput: RecipeOutput) :
    RecipeSubProvider(provider, recipeOutput) {
    override fun buildRecipes() {
        this.add(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2)
        this.add(ModBlocks.SOULLESS_SANDSTONE_WALL.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2)
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.CUT_SOULLESS_SANDSTONE.get(), 2)
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2)
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get())
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), 2)
    }

    private fun add(result: ItemLike, material: ItemLike, count: Int = 1) {
        this.add(
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(material),
                RecipeCategory.BUILDING_BLOCKS,
                result,
                count
            ).unlockedBy(material),
            result.getName() + "_from_" + material.getName() + "_stonecutting"
        )
    }
}
