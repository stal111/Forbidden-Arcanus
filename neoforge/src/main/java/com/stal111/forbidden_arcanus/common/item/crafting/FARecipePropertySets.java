package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.mixin.RecipeManagerAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;

import java.util.Optional;

public class FARecipePropertySets {

    public static final ResourceKey<RecipePropertySet> CLIBANO_MELTING_INPUT = register("clibano_melting_input");

    private static ResourceKey<RecipePropertySet> register(String id) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, ForbiddenArcanus.identifier(id));
    }

    public static void addToMap() {
        ImmutableMap.Builder<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor> propertySets = ImmutableMap.<ResourceKey<RecipePropertySet>, RecipeManager.IngredientExtractor>builder()
                .put(CLIBANO_MELTING_INPUT, (recipe) -> recipe instanceof ClibanoMeltingRecipe clibanoMeltingRecipe ? Optional.of(clibanoMeltingRecipe.ingredient()) : Optional.empty())
                .putAll(RecipeManagerAccessor.getPropertySets());

        RecipeManagerAccessor.setPropertySets(propertySets.build());
    }
}
