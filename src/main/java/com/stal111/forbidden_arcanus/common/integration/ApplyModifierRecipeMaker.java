package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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

        level.getRecipeManager().getAllRecipesFor(RecipeType.SMITHING).stream()
                .filter(upgradeRecipe -> upgradeRecipe.value().getSerializer() == ModRecipeSerializers.APPLY_MODIFIER.get())
                .map(upgradeRecipe -> (ApplyModifierRecipe) upgradeRecipe.value())
                .forEach(applyModifierRecipe -> {
                    Holder<ItemModifier> modifier = applyModifierRecipe.modifier();
                    ResourceLocation id = ForbiddenArcanus.location("jei.apply_" + modifier.value().getRegistryName().getPath() + "_modifier");

                    modifier.value().getValidItems(level.registryAccess()).forEach(stack -> {
                        ModifierHelper.setModifier(stack, modifier);

                        recipes.add(new RecipeHolder<>(id, new SmithingTransformRecipe(applyModifierRecipe.template(), Ingredient.of(stack.getItem()), applyModifierRecipe.addition(), stack)));
                    });
                });

        return recipes;
    }
}
