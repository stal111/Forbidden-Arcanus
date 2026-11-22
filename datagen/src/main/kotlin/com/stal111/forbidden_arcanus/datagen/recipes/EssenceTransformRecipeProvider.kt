package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.essence.EssenceType
import com.stal111.forbidden_arcanus.common.essence.EssenceValue
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.datagen.recipes.builder.EssenceTransformRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class EssenceTransformRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput,
) : RecipeSubProvider(provider, recipeOutput) {

    override fun buildRecipes() {
        transform(
            input = ModTags.Items.ESSENCE_TRANSFORMS_INTO_GROWING_EDELWOOD,
            essence = EssenceValue(EssenceType.ECTOPLASM, 76),
            result = ModBlocks.GROWING_EDELWOOD.get(),
        )
        transform(
            input = ModTags.Items.ESSENCE_TRANSFORMS_INTO_AURUM_SAPLING,
            essence = EssenceValue(EssenceType.AUREAL, 58),
            result = ModBlocks.AURUM_SAPLING.get(),
        )
        transform(
            input = ModTags.Items.ESSENCE_TRANSFORMS_INTO_FUNGYSS,
            essence = EssenceValue(EssenceType.AUREAL, 84),
            result = ModBlocks.FUNGYSS.get(),
        )
        transform(
            input = Items.BLUE_ORCHID,
            essence = EssenceValue(EssenceType.AUREAL, 65),
            result = ModBlocks.YELLOW_ORCHID.get(),
        )
    }

    private fun transform(
        input: TagKey<Item>,
        essence: EssenceValue,
        result: ItemLike,
    ) = add(EssenceTransformRecipeBuilder(Ingredient.of(items.getOrThrow(input)), essence, result))

    private fun transform(
        input: ItemLike,
        essence: EssenceValue,
        result: ItemLike,
    ) = add(EssenceTransformRecipeBuilder(input, essence, result))
}
