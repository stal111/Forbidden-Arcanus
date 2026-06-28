package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import net.minecraft.advancements.Criterion
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe

class ClibanoMeltingRecipeBuilder(
    private val result: MoltenMaterial,
    private val ingredient: Ingredient?,
    private val cookingTime: Int
) : RecipeBuilder {
    private var requiredEnhancer: Holder<EnhancerDefinition>? = null

    private val advancementBuilder = RecipeUnlockAdvancementBuilder()

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        this.advancementBuilder.unlockedBy(name, criterion)

        return this
    }

    override fun group(group: String?): RecipeBuilder {
        return this
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

        output.accept(resourceKey, recipe, advancementBuilder.build(output, resourceKey, RecipeCategory.MISC))
    }
}
