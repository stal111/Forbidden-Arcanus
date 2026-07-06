package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import net.minecraft.advancements.Criterion
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe

class ClibanoMeltingRecipeBuilder(
    private val result: MoltenMaterial,
    private val ingredient: Ingredient?,
    private val cookingTime: Int
) : RecipeBuilder {
    private var requiredEnhancer: Holder<EnhancerDefinition>? = null

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        throw UnsupportedOperationException("ClibanoMeltingRecipe does not support advancements")
    }

    override fun group(group: String?): RecipeBuilder {
        throw UnsupportedOperationException("ClibanoMeltingRecipe does not support groups")
    }

    override fun defaultId() = ResourceKey.create(
        Registries.RECIPE,
        this.result.type.unwrapKey().orElseThrow().identifier().withPrefix("clibano/melting/")
    )

    fun enhancer(enhancer: Holder<EnhancerDefinition>): ClibanoMeltingRecipeBuilder {
        this.requiredEnhancer = enhancer

        return this
    }

    override fun save(output: RecipeOutput, resourceKey: ResourceKey<Recipe<*>>) {
        val recipe = ClibanoMeltingRecipe(
            this.ingredient,
            this.result,
            ClibanoCookingTimes.of(this.cookingTime),
            this.requiredEnhancer
        )

        output.accept(resourceKey, recipe, null)
    }
}
