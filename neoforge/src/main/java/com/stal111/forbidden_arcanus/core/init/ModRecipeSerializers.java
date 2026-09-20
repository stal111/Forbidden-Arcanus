package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.crafting.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class ModRecipeSerializers implements RegistryClass {

    public static final MappedRegistryHelper<RecipeSerializer<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.RECIPE_SERIALIZER);

    public static final Supplier<RecipeSerializer<ApplyModifierRecipe>> APPLY_MODIFIER = HELPER.register("apply_modifier", () -> ApplyModifierRecipe.SERIALIZER);
    public static final Supplier<RecipeSerializer<ClibanoMeltingRecipe>> CLIBANO_MELTING_SERIALIZER = HELPER.register("clibano_melting", () -> ClibanoMeltingRecipe.SERIALIZER);
    public static final Supplier<RecipeSerializer<ClibanoAlloyingRecipe>> CLIBANO_ALLOYING_SERIALIZER = HELPER.register("clibano_alloying", () -> ClibanoAlloyingRecipe.SERIALIZER);
    public static final Supplier<RecipeSerializer<CombineAurealTankRecipe>> COMBINE_AUREAL_TANK = HELPER.register("combine_aureal_tank", () -> CombineAurealTankRecipe.SERIALIZER);
    public static final Supplier<RecipeSerializer<EssenceTransformRecipe>> ESSENCE_TRANSFORM = HELPER.register("essence_transform", () -> EssenceTransformRecipe.SERIALIZER);

}
