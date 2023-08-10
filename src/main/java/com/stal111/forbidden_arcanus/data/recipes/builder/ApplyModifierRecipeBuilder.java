package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2022-10-21
 */
public record ApplyModifierRecipeBuilder(Ingredient template, Ingredient addition, ItemModifier modifier) implements RecipeBuilder {

    public static ApplyModifierRecipeBuilder of(ItemLike template, ItemLike addition, ItemModifier modifier) {
        return new ApplyModifierRecipeBuilder(Ingredient.of(template), Ingredient.of(addition), modifier);
    }

    public static ApplyModifierRecipeBuilder of(ItemLike template, Ingredient addition, ItemModifier modifier) {
        return new ApplyModifierRecipeBuilder(Ingredient.of(template), addition, modifier);
    }

    @Nonnull
    @Override
    public RecipeBuilder unlockedBy(@Nonnull String criterionName, @Nonnull CriterionTriggerInstance criterionTrigger) {
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
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        ResourceLocation key = this.modifier.getRegistryName();
        this.save(finishedRecipeConsumer, new ResourceLocation(key.getNamespace(), "smithing/apply_" + key.getPath() + "_modifier"));
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        finishedRecipeConsumer.accept(new ApplyModifierRecipeBuilder.Result(recipeId, this.template, this.addition, this.modifier));
    }

    private record Result(ResourceLocation recipeId,
                          Ingredient template,
                          Ingredient addition,
                          ItemModifier modifier) implements FinishedRecipe {

        @Override
            public void serializeRecipeData(JsonObject json) {
                json.add("template", this.template.toJson());
                json.add("addition", this.addition.toJson());
                json.addProperty("modifier", this.modifier.getRegistryName().toString());
            }

            @Nonnull
            public RecipeSerializer<?> getType() {
                return ModRecipes.APPLY_MODIFIER.get();
            }

            /**
             * Gets the ID for the recipe.
             */
            @Nonnull
            public ResourceLocation getId() {
                return this.recipeId;
            }

            /**
             * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
             */
            @Nullable
            public JsonObject serializeAdvancement() {
                return null;
            }

            /**
             * Gets the ID for the advancement associated with this recipe.
             */
            @Nullable
            public ResourceLocation getAdvancementId() {
                return null;
            }
        }
}
