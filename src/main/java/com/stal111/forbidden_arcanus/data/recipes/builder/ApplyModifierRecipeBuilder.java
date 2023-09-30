package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    public void save(@Nonnull RecipeOutput recipeOutput) {
        ResourceLocation key = this.modifier.getRegistryName();
        this.save(recipeOutput, new ResourceLocation(key.getNamespace(), "smithing/apply_" + key.getPath() + "_modifier"));
    }

    @Override
    public void save(@Nonnull RecipeOutput recipeOutput, @Nonnull ResourceLocation recipeId) {
        recipeOutput.accept(new ApplyModifierRecipeBuilder.Result(recipeId, this.template, this.addition, this.modifier));
    }

    private record Result(ResourceLocation recipeId,
                          Ingredient template,
                          Ingredient addition,
                          ItemModifier modifier) implements FinishedRecipe {

        @Override
            public void serializeRecipeData(JsonObject json) {
                json.add("template", this.template.toJson(false));
                json.add("addition", this.addition.toJson(false));
                json.addProperty("modifier", this.modifier.getRegistryName().toString());
            }

        @Override
        public @NotNull ResourceLocation id() {
            return this.recipeId;
        }

        @Override
        public @NotNull RecipeSerializer<?> type() {
            return ModRecipeSerializers.APPLY_MODIFIER.get();
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return null;
        }
    }
}
