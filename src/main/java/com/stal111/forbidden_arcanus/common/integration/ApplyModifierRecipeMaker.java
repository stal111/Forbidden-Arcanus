package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Apply Modifier Recipe Maker <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.integration.ApplyModifierRecipeMaker
 *
 * @author stal111
 * @since 2021-11-30
 */
public class ApplyModifierRecipeMaker {

    public static List<RecipeHolder<SmithingRecipe>> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;

        if (level == null) {
            return Collections.emptyList();
        }
        List<RecipeHolder<SmithingRecipe>> recipes = new ArrayList<>();

        var applyModifierRecipes = level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING).stream()
                .filter(upgradeRecipe -> upgradeRecipe.value().getSerializer() == ModRecipeSerializers.APPLY_MODIFIER.get())
                .map(holder -> new RecipeHolder<>(holder.id(), (ApplyModifierRecipe) holder.value()))
                .toList();

        for (Item item : BuiltInRegistries.ITEM) {
            ItemStack stack = item.getDefaultInstance();

            for (var recipeHolder : applyModifierRecipes) {
                var recipe = recipeHolder.value();

                SmithingRecipeInput input = new SmithingRecipeInput(recipe.template().getItems()[0], stack, recipe.addition().getItems()[0]);

                if (recipe.matches(input, level)) {
                    recipes.add(createRecipe(recipeHolder, input, stack, level.registryAccess()));
                }
            }
        }

        return recipes;
    }

    private static RecipeHolder<SmithingRecipe> createRecipe(RecipeHolder<ApplyModifierRecipe> recipeHolder, SmithingRecipeInput input, ItemStack stack, RegistryAccess registryAccess) {
        var id = recipeHolder.id().withSuffix("_" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath());
        var recipe = recipeHolder.value();

        return new RecipeHolder<>(id, new SmithingTransformRecipe(recipe.template(), Ingredient.of(stack), recipe.addition(), recipe.assemble(input, registryAccess)));
    }
}
