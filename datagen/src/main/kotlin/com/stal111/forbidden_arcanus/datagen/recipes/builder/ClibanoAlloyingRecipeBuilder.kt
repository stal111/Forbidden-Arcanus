package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe
import net.minecraft.advancements.Criterion
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Recipe

class ClibanoAlloyingRecipeBuilder(
    private val requiredMaterials: List<MoltenMaterial>,
    private val result: ItemStackTemplate,
    private val duration: Int = 100
) : RecipeBuilder {

    override fun unlockedBy(
        name: String,
        criterion: Criterion<*>
    ): RecipeBuilder {
        throw UnsupportedOperationException("ClibanoAlloyingRecipe does not support advancements")
    }

    override fun group(group: String?): RecipeBuilder {
        throw UnsupportedOperationException("ClibanoAlloyingRecipe does not support groups")
    }

    override fun defaultId() = ResourceKey.create(
        Registries.RECIPE,
        result.typeHolder().unwrapKey().orElseThrow().identifier().withPrefix("clibano/alloying/")
    )

    override fun save(
        output: RecipeOutput,
        resourceKey: ResourceKey<Recipe<*>>
    ) {
        val recipe = ClibanoAlloyingRecipe(
            requiredMaterials,
            result,
            duration
        )

        output.accept(resourceKey, recipe, null)
    }
}