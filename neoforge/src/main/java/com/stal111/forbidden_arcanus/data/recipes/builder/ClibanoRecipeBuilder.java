package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author stal111
 * @since 23.06.2024
 */
public class ClibanoRecipeBuilder implements RecipeBuilder {

    private final MoltenMaterial result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private @Nullable Holder<EnhancerDefinition> requiredEnhancer;

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ClibanoRecipeBuilder(
            MoltenMaterial result,
            Ingredient ingredient,
            float experience,
            int cookingTime
    ) {
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);

        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, this.result.type().unwrapKey().orElseThrow().identifier().withPrefix("clibano_combustion/"));
    }

    public ClibanoRecipeBuilder enhancer(Holder<EnhancerDefinition> enhancer) {
        this.requiredEnhancer = enhancer;

        return this;
    }

    @Override
    public void save(@NotNull RecipeOutput output, @NotNull ResourceKey<Recipe<?>> resourceKey) {
        this.ensureValid(resourceKey);

        Advancement.Builder advancement$builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);

        ClibanoRecipe recipe = new ClibanoRecipe(this.ingredient, this.result, this.experience, ClibanoCookingTimes.of(this.cookingTime), this.requiredEnhancer);

        output.accept(resourceKey, recipe, advancement$builder.build(resourceKey.identifier().withPrefix("recipes/")));
    }

    private void ensureValid(ResourceKey<Recipe<?>> recipe) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipe);
        }
    }
}
