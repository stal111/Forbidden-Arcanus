package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.essence.EssenceValue
import com.stal111.forbidden_arcanus.common.item.crafting.EssenceTransformRecipe
import net.minecraft.advancements.Criterion
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe

class EssenceTransformRecipeBuilder(
    private val input: Ingredient,
    private val essence: EssenceValue,
    private val result: ItemStackTemplate,
) : RecipeBuilder {

    override fun unlockedBy(
        name: String,
        criterion: Criterion<*>
    ): RecipeBuilder {
        throw UnsupportedOperationException("EssenceTransformRecipeBuilder does not support advancements")
    }

    override fun group(group: String?): RecipeBuilder {
        throw UnsupportedOperationException("EssenceTransformRecipeBuilder does not support groups")
    }

    override fun defaultId() = ResourceKey.create(
        Registries.RECIPE,
        this.result.typeHolder().unwrapKey().orElseThrow().identifier().withPrefix("essence_transform/")
    )

    override fun save(
        output: RecipeOutput,
        location: ResourceKey<Recipe<*>>
    ) {
        val recipe = EssenceTransformRecipe(
            this.input,
            this.essence,
            this.result
        )

        output.accept(location, recipe, null)
    }
}