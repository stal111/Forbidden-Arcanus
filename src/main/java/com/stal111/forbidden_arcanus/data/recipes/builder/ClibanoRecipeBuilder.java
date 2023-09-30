package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author stal111
 * @since 2022-06-26
 */
public record ClibanoRecipeBuilder(RecipeCategory category, Item result, Ingredient ingredient, float experience, int cookingTime, Map<String, Criterion<?>> criteria, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType requiredFireType) implements RecipeBuilder {

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime) {
        return ClibanoRecipeBuilder.of(category, result, ingredient, experience, cookingTime, ClibanoRecipe.ResidueInfo.NONE);
    }

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime, ClibanoRecipe.ResidueInfo residueInfo) {
        return ClibanoRecipeBuilder.of(category, result.asItem(), ingredient, experience, cookingTime, residueInfo, ClibanoFireType.FIRE);
    }

    public static ClibanoRecipeBuilder of(RecipeCategory category, ItemLike result, Ingredient ingredient, float experience, int cookingTime, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType requiredFireType) {
        return new ClibanoRecipeBuilder(category, result.asItem(), ingredient, experience, cookingTime, new LinkedHashMap<>(), residueInfo, requiredFireType);
    }

    @NotNull
    @Override
    public RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);

        return this;
    }

    @NotNull
    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @NotNull
    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(this.getResult());

        this.save(recipeOutput, new ResourceLocation(ForbiddenArcanus.MOD_ID, "clibano_combustion/" + recipeId.getPath() + "_from_clibano_combusting"));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);

        recipeOutput.accept(new Result(recipeId, this.ingredient, this.result, this.experience, this.cookingTime, builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")), this.residueInfo, this.requiredFireType));
    }

    private void ensureValid(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }

    private static class Result implements FinishedRecipe {

        private final ResourceLocation recipeId;
        private final Ingredient ingredient;
        private final Item result;
        private final float experience;
        private final int cookingTime;
        private final AdvancementHolder advancement;

        private final ClibanoRecipe.ResidueInfo residueInfo;
        private final ClibanoFireType fireType;

        public Result(ResourceLocation recipeId, Ingredient ingredient, Item result, float experience, int cookingTime, AdvancementHolder advancement, ClibanoRecipe.ResidueInfo residueInfo, ClibanoFireType fireType) {
            this.recipeId = recipeId;
            this.ingredient = ingredient;
            this.result = result;
            this.experience = experience;
            this.cookingTime = cookingTime;
            this.advancement = advancement;
            this.residueInfo = residueInfo;
            this.fireType = fireType;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson(false));
            json.addProperty("result", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            json.addProperty("experience", this.experience);
            json.addProperty("cooking_time", this.cookingTime);

            if (fireType != ClibanoFireType.FIRE) {
                json.addProperty("fire_type", this.fireType.getSerializedName());
            }

            this.residueInfo.toJson(json);
        }

        @Override
        public @NotNull ResourceLocation id() {
            return this.recipeId;
        }

        @Override
        public @NotNull RecipeSerializer<?> type() {
            return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return this.advancement;
        }
    }
}
