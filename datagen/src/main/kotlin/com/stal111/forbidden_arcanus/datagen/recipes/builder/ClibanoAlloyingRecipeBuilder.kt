package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe
import net.minecraft.advancements.Criterion
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Recipe

class ClibanoAlloyingRecipeBuilder(
    private val requiredMaterials: List<MoltenMaterial>,
    private val result: ItemStackTemplate,
) : RecipeBuilder {

    private val advancementBuilder = RecipeUnlockAdvancementBuilder()

    override fun unlockedBy(
        name: String,
        criterion: Criterion<*>
    ): RecipeBuilder {
        this.advancementBuilder.unlockedBy(name, criterion)

        return this
    }

    override fun group(p0: String?): RecipeBuilder {
        return this
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
            this.requiredMaterials,
            this.result,
        )

        output.accept(resourceKey, recipe, advancementBuilder.build(output, resourceKey, RecipeCategory.MISC))
    }
}