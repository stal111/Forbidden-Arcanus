package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author stal111
 * @since 23.06.2024
 */
public class ClibanoRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final Item result;
    private final ItemStack stackResult;
    private final Either<Ingredient, Pair<Ingredient, Ingredient>> ingredients;
    private final float experience;
    private final int cookingTime;
    private @Nullable ResidueChance residueChance;
    private ClibanoFireType requiredFireType = ClibanoFireType.FIRE;
    private @Nullable Holder<EnhancerDefinition> requiredEnhancer;

    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private @Nullable String group;

    public ClibanoRecipeBuilder(
            RecipeCategory recipeCategory,
            CookingBookCategory bookCategory,
            ItemStack result,
            Either<Ingredient, Pair<Ingredient, Ingredient>> ingredients,
            float experience,
            int cookingTime
    ) {
        this.category = recipeCategory;
        this.bookCategory = bookCategory;
        this.result = result.getItem();
        this.stackResult = result;
        this.ingredients = ingredients;
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
        this.group = group;

        return this;
    }

    public ClibanoRecipeBuilder residue(ResidueChance residueChance) {
        this.residueChance = residueChance;

        return this;
    }

    public ClibanoRecipeBuilder fireType(ClibanoFireType fireType) {
        this.requiredFireType = fireType;

        return this;
    }

    public ClibanoRecipeBuilder enhancer(Holder<EnhancerDefinition> enhancer) {
        this.requiredEnhancer = enhancer;

        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        this.ensureValid(id);

        Advancement.Builder advancement$builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);

        ClibanoRecipe recipe = new ClibanoRecipe(this.group, this.bookCategory, this.ingredients, this.stackResult, this.experience, this.cookingTime, this.residueChance, this.requiredFireType, this.requiredEnhancer);

        recipeOutput.accept(id, recipe, advancement$builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
