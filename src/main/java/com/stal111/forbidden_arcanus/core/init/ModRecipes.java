package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.recipe.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.common.recipe.IncreaseEdelwoodBucketFullnessRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ForbiddenArcanus.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<RecipeType<ClibanoRecipe>> CLIBANO_COMBUSTION = registerRecipeType("clibano_combustion");

    public static final RegistryObject<RecipeSerializer<IncreaseEdelwoodBucketFullnessRecipe>> EDELWOOD_BUCKET_INCREASE_FULLNESS = register("increase_edelwood_bucket_fullness", new SimpleRecipeSerializer<>(IncreaseEdelwoodBucketFullnessRecipe::new));
    public static final RegistryObject<RecipeSerializer<ApplyModifierRecipe>> APPLY_MODIFIER = register("apply_modifier", new ApplyModifierRecipe.Serializer());
    public static final RegistryObject<RecipeSerializer<ClibanoRecipe>> CLIBANO_SERIALIZER = register("clibano_combustion", new ClibanoRecipe.Serializer());

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

    static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<T>() {
            public String toString() {
                return name;
            }
        });
    }
}
