package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.data.recipes.builder.ApplyModifierRecipeBuilder
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.world.level.ItemLike
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class ApplyModifierRecipeProvider(provider: HolderLookup.Provider, recipeOutput: RecipeOutput) :
    RecipeSubProvider(provider, recipeOutput) {
    override fun buildRecipes() {
        this.modifier(ModItems.ETERNAL_STELLA.get(), registries.holderOrThrow(BuiltInItemModifiers.ETERNAL))
        this.modifier(ModItems.SMELTER_PRISM.get(), registries.holderOrThrow(BuiltInItemModifiers.FIERY))
        this.modifier(ModItems.FERROGNETIC_MIXTURE.get(), registries.holderOrThrow(BuiltInItemModifiers.MAGNETIZED))
        this.modifier(ModItems.TERRASTOMP_PRISM.get(), registries.holderOrThrow(BuiltInItemModifiers.DEMOLISHING))
        this.modifier(ModItems.SEA_PRISM.get(), registries.holderOrThrow(BuiltInItemModifiers.AQUATIC))
        this.modifier(ModItems.SOUL_BINDING_CRYSTAL.get(), registries.holderOrThrow(BuiltInItemModifiers.SOULBOUND))
    }

    private fun modifier(addition: ItemLike, modifier: Holder<ItemModifier>) {
        this.add(ApplyModifierRecipeBuilder.of(ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE.get(), addition, modifier), "apply_modifier/" + modifier.key!!.location().path)
    }
}
