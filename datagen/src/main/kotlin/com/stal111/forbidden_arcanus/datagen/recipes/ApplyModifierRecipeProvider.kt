package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.datagen.recipes.builder.ApplyModifierRecipeBuilder
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.ItemLike
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class ApplyModifierRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput,
) : RecipeSubProvider(provider, recipeOutput) {

    override fun buildRecipes() {
        modifier(ModItems.ETERNAL_STELLA, BuiltInItemModifiers.ETERNAL)
        modifier(ModItems.SMELTER_PRISM, BuiltInItemModifiers.FIERY)
        modifier(ModItems.FERROGNETIC_MIXTURE, BuiltInItemModifiers.MAGNETIZED)
        modifier(ModItems.TERRASTOMP_PRISM, BuiltInItemModifiers.DEMOLISHING)
        modifier(ModItems.SEA_PRISM, BuiltInItemModifiers.AQUATIC)
        modifier(ModItems.SOUL_BINDING_CRYSTAL, BuiltInItemModifiers.SOULBOUND)
    }

    private fun modifier(
        addition: ItemLike,
        modifier: ResourceKey<ItemModifier>,
        template: ItemLike = ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE,
    ) = add(ApplyModifierRecipeBuilder(template, addition, registries.holderOrThrow(modifier)))
}
