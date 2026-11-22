package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.essence.EssenceValue
import com.stal111.forbidden_arcanus.common.item.crafting.EssenceTransformRecipe
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class EssenceTransformRecipeBuilder(
    private val input: Ingredient,
    private val essence: EssenceValue,
    private val result: ItemStackTemplate,
) : SimpleRecipeBuilder() {

    constructor(
        input: Ingredient,
        essence: EssenceValue,
        result: ItemLike,
    ) : this(input, essence, ItemStackTemplate(result.asItem()))

    constructor(
        input: ItemLike,
        essence: EssenceValue,
        result: ItemLike,
    ) : this(Ingredient.of(input), essence, ItemStackTemplate(result.asItem()))

    override fun defaultId(): ResourceKey<Recipe<*>> = recipeKey(
        "essence_transform/" + this.result.typeHolder().unwrapKey().orElseThrow().identifier().path
    )

    override fun createRecipe(): Recipe<*> = EssenceTransformRecipe(
        this.input,
        this.essence,
        this.result,
    )
}
