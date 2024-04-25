package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class ModRecipeTypes implements RegistryClass {

    public static final MappedRegistryHelper<RecipeType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.RECIPE_TYPE);

    public static final RegistryEntry<RecipeType<?>, RecipeType<ClibanoRecipe>> CLIBANO_COMBUSTION = registerRecipeType("clibano_combustion");

    static <T extends Recipe<?>> RegistryEntry<RecipeType<?>, RecipeType<T>> registerRecipeType(String name) {
        return HELPER.register(name, () -> new RecipeType<>() {
            public String toString() {
                return name;
            }
        });
    }
}
