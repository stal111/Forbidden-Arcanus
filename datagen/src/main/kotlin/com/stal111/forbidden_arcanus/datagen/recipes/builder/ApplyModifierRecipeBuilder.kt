package com.stal111.forbidden_arcanus.datagen.recipes.builder

import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike

class ApplyModifierRecipeBuilder(
    private val template: Ingredient,
    private val addition: Ingredient,
    private val modifier: Holder<ItemModifier>,
) : RecipeBuilder {

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

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        throw UnsupportedOperationException("ApplyModifierRecipeBuilder does not support custom advancements")
    }

    override fun group(group: String?): RecipeBuilder {
        throw UnsupportedOperationException("ApplyModifierRecipeBuilder does not support groups")
    }

    override fun defaultId(): ResourceKey<Recipe<*>> = ResourceKey.create(
        Registries.RECIPE,
        this.modifier.unwrapKey().orElseThrow().identifier().withPrefix("apply_modifier/")
    )

    override fun save(output: RecipeOutput, resourceKey: ResourceKey<Recipe<*>>) {
        val recipe = ApplyModifierRecipe(Recipe.CommonInfo(true), this.template, this.addition, this.modifier)
        val advancement = output.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
            .rewards(AdvancementRewards.Builder.recipe(resourceKey))
            .requirements(AdvancementRequirements.Strategy.OR)

        output.accept(resourceKey, recipe, advancement.build(resourceKey.identifier().withPrefix("recipes/")))
    }
}
