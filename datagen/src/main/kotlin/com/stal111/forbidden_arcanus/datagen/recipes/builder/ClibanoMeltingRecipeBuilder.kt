package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class ClibanoMeltingRecipeBuilder(
    private val result: MoltenMaterial,
    private val ingredient: Ingredient,
    private val cookingTime: Int = 100,
    private val requiredEnhancer: Holder<EnhancerDefinition>? = null,
) : SimpleRecipeBuilder() {

    constructor(
        result: MoltenMaterial,
        ingredient: ItemLike,
        cookingTime: Int = 100,
        requiredEnhancer: Holder<EnhancerDefinition>? = null,
    ) : this(result, Ingredient.of(ingredient), cookingTime, requiredEnhancer)

    override fun defaultId(): ResourceKey<Recipe<*>> = recipeKey(
        "clibano/melting/" + this.result.type.unwrapKey().orElseThrow().identifier().path
    )

    override fun createRecipe(): Recipe<*> = ClibanoMeltingRecipe(
        this.ingredient,
        this.result,
        ClibanoCookingTimes.of(this.cookingTime),
        this.requiredEnhancer,
    )
}
