package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.recipe.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.common.recipe.IncreaseEdelwoodBucketFullnessRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<RecipeSerializer<IncreaseEdelwoodBucketFullnessRecipe>> EDELWOOD_BUCKET_INCREASE_FULLNESS = register("increase_edelwood_bucket_fullness", new SimpleRecipeSerializer<>(IncreaseEdelwoodBucketFullnessRecipe::new));
    public static final RegistryObject<RecipeSerializer<ApplyModifierRecipe>> APPLY_MODIFIER = register("apply_modifier", new ApplyModifierRecipe.Serializer());

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, T recipeSerializer) {
        return RECIPE_SERIALIZERS.register(name, () -> recipeSerializer);
    }

}
