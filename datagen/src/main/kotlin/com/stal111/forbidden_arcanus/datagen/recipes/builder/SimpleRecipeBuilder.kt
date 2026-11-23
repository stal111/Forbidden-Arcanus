package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import net.minecraft.advancements.triggers.Criterion
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Recipe

abstract class SimpleRecipeBuilder : RecipeBuilder {

    protected abstract fun createRecipe(): Recipe<*>

    protected fun recipeKey(path: String): ResourceKey<Recipe<*>> = ResourceKey.create(
        Registries.RECIPE,
        ForbiddenArcanus.identifier(path),
    )

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        throw UnsupportedOperationException("${this::class.simpleName} does not support advancements")
    }

    override fun group(group: String?): RecipeBuilder {
        throw UnsupportedOperationException("${this::class.simpleName} does not support groups")
    }

    override fun save(output: RecipeOutput, resourceKey: ResourceKey<Recipe<*>>) {
        output.accept(resourceKey, createRecipe(), null)
    }
}
