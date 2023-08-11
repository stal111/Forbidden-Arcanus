package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2022-06-26
 */
public record ClibanoRecipeBuilder(RecipeCategory category, Item result, Ingredient ingredient, float experience, int cookingTime, Advancement.Builder advancement, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType requiredFireType) implements RecipeBuilder {

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime) {
        return ClibanoRecipeBuilder.of(category, result, ingredient, experience, cookingTime, ClibanoRecipe.ResidueInfo.NONE);
    }

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime, ClibanoRecipe.ResidueInfo residueInfo) {
        return new ClibanoRecipeBuilder(category, result.asItem(), ingredient, experience, cookingTime, Advancement.Builder.advancement(), residueInfo, ClibanoFireType.FIRE);
    }

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType requiredFireType) {
        return new ClibanoRecipeBuilder(category, result.asItem(), ingredient, experience, cookingTime, Advancement.Builder.advancement(), residueInfo, requiredFireType);
    }

    @Nonnull
    @Override
    public RecipeBuilder unlockedBy(@Nonnull String criterionName, @Nonnull CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(criterionName, criterionTrigger);

        return this;
    }

    @Nonnull
    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Nonnull
    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(this.getResult());

        this.save(finishedRecipeConsumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "clibano_combustion/" + recipeId.getPath() + "_from_clibano_combusting"));
    }

    @Override
    public void save(@Nonnull Consumer<FinishedRecipe> finishedRecipeConsumer, @Nonnull ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);

         ResourceLocation advancementId = new ResourceLocation(recipeId.getNamespace(), "recipes/" + this.category.getFolderName() + "/" + recipeId.getPath());

        finishedRecipeConsumer.accept(new Result(recipeId, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, advancementId, this.residueInfo, this.requiredFireType));
    }

    private void ensureValid(ResourceLocation recipeId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }

    private static class Result implements FinishedRecipe {
        private final ResourceLocation recipeId;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final ClibanoRecipe.ResidueInfo residueInfo;
        private final ClibanoFireType fireType;

        public Result(ResourceLocation recipeId, Ingredient ingredient, Item result, float experience, int cookingTime, Advancement.Builder advancement, ResourceLocation advancementId, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType fireType) {
            this.recipeId = recipeId;
            this.ingredient = ingredient;
            this.result = result;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.residueInfo = residueInfo;
            this.fireType = fireType;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            json.addProperty("result", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            json.addProperty("experience", this.experience);
            json.addProperty("cooking_time", this.cookingTime);

            if (fireType != ClibanoFireType.FIRE) {
                json.addProperty("fire_type", this.fireType.getSerializedName());
            }

            this.residueInfo.toJson(json);
        }

        @Nonnull
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
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
            return this.advancement.serializeToJson();
        }

        /**
         * Gets the ID for the advancement associated with this recipe.
         */
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
