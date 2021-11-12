package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.recipe.ApplyIndestructibleEnchantmentRecipe;
import com.stal111.forbidden_arcanus.recipe.EdelwoodBucketIncreaseFullnessRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> EDELWOOD_BUCKET_INCREASE_FULLNESS = register("edelwood_bucket_increase_fullness", new SimpleRecipeSerializer<>(EdelwoodBucketIncreaseFullnessRecipe::new));
    public static final RegistryObject<RecipeSerializer<?>> APPLY_INDESTRUCTIBLE_ENCHANTMENT = register("apply_indestructible_enchantment", new SimpleRecipeSerializer<>(ApplyIndestructibleEnchantmentRecipe::new));

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

}
