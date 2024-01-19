package com.stal111.forbidden_arcanus.data.recipes.builder;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.recipe.CombineResiduesRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author stal111
 * @since 2022-07-05
 */
public record CombineResiduesRecipeBuilder(String residueName, int residueAmount, ItemStack result) implements RecipeBuilder {

    public static CombineResiduesRecipeBuilder of(String residueName, int residueAmount, ItemStack result) {
        return new CombineResiduesRecipeBuilder(residueName, residueAmount, result);
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

    @Nonnull
    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(@Nonnull RecipeOutput recipeOutput) {
        ResourceLocation recipeId = RecipeBuilder.getDefaultRecipeId(this.getResult());
        this.save(recipeOutput, new ResourceLocation(ForbiddenArcanus.MOD_ID, "combine_residues/" + this.residueName + "_residues_to_" + recipeId.getPath()));
    }

    @Override
    public void save(@Nonnull RecipeOutput recipeOutput, @Nonnull ResourceLocation recipeId) {
        CombineResiduesRecipe recipe = new CombineResiduesRecipe(CraftingBookCategory.MISC, this.residueName, (short) this.residueAmount, this.result);

        recipeOutput.accept(recipeId, recipe, recipeOutput.advancement().build(recipeId.withPrefix("recipes/combine_residues/")));
    }
}
