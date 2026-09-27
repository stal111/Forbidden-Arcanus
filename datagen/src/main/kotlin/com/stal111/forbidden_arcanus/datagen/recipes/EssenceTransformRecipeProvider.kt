package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.essence.EssenceType
import com.stal111.forbidden_arcanus.common.essence.EssenceValue
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.datagen.recipes.builder.EssenceTransformRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
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
                input = Ingredient.of(items.getOrThrow(ModTags.Items.ESSENCE_TRANSFORMS_INTO_GROWING_EDELWOOD)),
                essence = EssenceValue(EssenceType.ECTOPLASM, 76),
                result = ItemStackTemplate(ModBlocks.GROWING_EDELWOOD.get().asItem())
            )
        )
        add(
            EssenceTransformRecipeBuilder(
                input = Ingredient.of(items.getOrThrow(ModTags.Items.ESSENCE_TRANSFORMS_INTO_AURUM_SAPLING)),
                essence = EssenceValue(EssenceType.AUREAL, 58),
                result = ItemStackTemplate(ModBlocks.AURUM_SAPLING.get().asItem())
            )
        )
        add(
            EssenceTransformRecipeBuilder(
                input = Ingredient.of(items.getOrThrow(ModTags.Items.ESSENCE_TRANSFORMS_INTO_FUNGYSS)),
                essence = EssenceValue(EssenceType.AUREAL, 84),
                result = ItemStackTemplate(ModBlocks.FUNGYSS.get().asItem())
            )
        )
        add(
            EssenceTransformRecipeBuilder(
                input = Ingredient.of(Items.BLUE_ORCHID),
                essence = EssenceValue(EssenceType.AUREAL, 65),
                result = ItemStackTemplate(ModBlocks.YELLOW_ORCHID.get().asItem())
            )
        )
    }
}