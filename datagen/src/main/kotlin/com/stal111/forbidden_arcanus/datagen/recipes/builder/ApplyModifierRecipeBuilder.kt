package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class ApplyModifierRecipeBuilder(
    private val template: Ingredient,
    private val addition: Ingredient,
    private val modifier: Holder<ItemModifier>,
) : SimpleRecipeBuilder() {

    constructor(
        template: ItemLike,
        addition: ItemLike,
        modifier: Holder<ItemModifier>,
    ) : this(Ingredient.of(template), Ingredient.of(addition), modifier)

    constructor(
        template: ItemLike,
        addition: Ingredient,
        modifier: Holder<ItemModifier>,
    ) : this(Ingredient.of(template), addition, modifier)

    override fun defaultId(): ResourceKey<Recipe<*>> = recipeKey(
        "apply_modifier/" + this.modifier.unwrapKey().orElseThrow().identifier().path
    )

    override fun createRecipe(): Recipe<*> = ApplyModifierRecipe(
        Recipe.CommonInfo(true),
        this.template,
        this.addition,
        this.modifier,
    )
}
