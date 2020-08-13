package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.recipe.ApplyIndestructibleEnchantmentRecipe;
import com.stal111.forbidden_arcanus.recipe.EdelwoodBucketIncreaseFullnessRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> EDELWOOD_BUCKET_INCREASE_FULLNESS = register("edelwood_bucket_increase_fullness", new SpecialRecipeSerializer<>(EdelwoodBucketIncreaseFullnessRecipe::new));
    public static final RegistryObject<IRecipeSerializer<?>> APPLY_INDESTRUCTIBLE_ENCHANTMENT = register("apply_indestructible_enchantment", new SpecialRecipeSerializer<>(ApplyIndestructibleEnchantmentRecipe::new));

    private static <T extends IRecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

}
