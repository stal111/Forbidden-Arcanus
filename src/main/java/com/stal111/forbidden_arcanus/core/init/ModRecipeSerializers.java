package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.recipe.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class ModRecipeSerializers implements RegistryClass {

    public static final MappedRegistryHelper<RecipeSerializer<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.RECIPE_SERIALIZER);

    public static final RegistryEntry<RecipeSerializer<ApplyModifierRecipe>> APPLY_MODIFIER = HELPER.register("apply_modifier", ApplyModifierRecipe.Serializer::new);
    public static final RegistryEntry<RecipeSerializer<ClibanoRecipe>> CLIBANO_SERIALIZER = HELPER.register("clibano_combustion", ClibanoRecipe.Serializer::new);
    public static final RegistryEntry<RecipeSerializer<CombineAurealTankRecipe>> COMBINE_AUREAL_TANK = HELPER.register("combine_aureal_tank", () -> new SimpleCraftingRecipeSerializer<>(CombineAurealTankRecipe::new));

}
