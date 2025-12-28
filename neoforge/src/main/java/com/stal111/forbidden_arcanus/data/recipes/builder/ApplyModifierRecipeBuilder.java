package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author stal111
 * @since 2022-10-21
 */
public record ApplyModifierRecipeBuilder(Ingredient template, Ingredient addition, Holder<ItemModifier> modifier) implements RecipeBuilder {

    public static ApplyModifierRecipeBuilder of(ItemLike template, ItemLike addition, Holder<ItemModifier> modifier) {
        return new ApplyModifierRecipeBuilder(Ingredient.of(template), Ingredient.of(addition), modifier);
    }

    public static ApplyModifierRecipeBuilder of(ItemLike template, Ingredient addition, Holder<ItemModifier> modifier) {
        return new ApplyModifierRecipeBuilder(Ingredient.of(template), addition, modifier);
    }

    @Nonnull
    @Override
    public RecipeBuilder unlockedBy(@Nonnull String criterionName, @Nonnull Criterion<?> criterion) {
        return this;
    }

    @Nonnull
    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(@NotNull RecipeOutput output, @NotNull ResourceKey<Recipe<?>> resourceKey) {
        ApplyModifierRecipe recipe = new ApplyModifierRecipe(this.template, this.addition, this.modifier);
        Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
                .rewards(AdvancementRewards.Builder.recipe(resourceKey))
                .requirements(AdvancementRequirements.Strategy.OR);

        output.accept(resourceKey, recipe, builder.build(resourceKey.identifier().withPrefix("recipes/")));
    }
}
