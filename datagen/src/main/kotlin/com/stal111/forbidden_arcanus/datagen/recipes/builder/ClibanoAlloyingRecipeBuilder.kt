package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class ClibanoAlloyingRecipeBuilder(
    private val requiredMaterials: List<MoltenMaterial>,
    private val result: ItemStackTemplate,
    private val duration: Int = 100,
) : SimpleRecipeBuilder() {

    constructor(
        requiredMaterials: List<MoltenMaterial>,
        result: ItemLike,
        duration: Int = 100,
    ) : this(requiredMaterials, ItemStackTemplate(result.asItem()), duration)

    override fun defaultId(): ResourceKey<Recipe<*>> = recipeKey(
        "clibano/alloying/" + result.typeHolder().unwrapKey().orElseThrow().identifier().path
    )

    override fun createRecipe(): Recipe<*> = ClibanoAlloyingRecipe(
        requiredMaterials,
        result,
        duration,
    )
}
