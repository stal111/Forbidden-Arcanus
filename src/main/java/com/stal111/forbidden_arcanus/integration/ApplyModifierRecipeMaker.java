package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.common.recipe.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.UpgradeRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Apply Modifier Recipe Maker <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.integration.ApplyModifierRecipeMaker
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-30
 */
public class ApplyModifierRecipeMaker {

    public static List<UpgradeRecipe> getRecipes() {
        if (Minecraft.getInstance().level == null) {
            return Collections.emptyList();
        }
        List<UpgradeRecipe> recipes = new ArrayList<>();

        Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING).stream()
                .filter(upgradeRecipe -> upgradeRecipe.getSerializer() == ModRecipeSerializers.APPLY_MODIFIER.get())
                .map(upgradeRecipe -> (ApplyModifierRecipe) upgradeRecipe)
                .forEach(applyModifierRecipe -> {
                    ItemModifier modifier = applyModifierRecipe.getModifier();
                    ResourceLocation id = new ResourceLocation(ForbiddenArcanus.MOD_ID, "jei.apply_" + Objects.requireNonNull(modifier.getRegistryName()).getPath() + "_modifier");

                    modifier.getValidItems().forEach(stack -> {
                        ModifierHelper.setModifier(stack, modifier);

                        recipes.add(new UpgradeRecipe(id, Ingredient.of(stack.getItem()), applyModifierRecipe.getAddition(), stack));
                    });
                });
        return recipes;
    }
}
