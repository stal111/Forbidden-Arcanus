package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.recipe.EdelwoodBucketIncreaseFullnessRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public enum ModRecipeSerializers {
    EDELWOOD_BUCKET_INCREASE_FULLNESS(new SpecialRecipeSerializer<>(EdelwoodBucketIncreaseFullnessRecipe::new));

    private final IRecipeSerializer<?> recipe;

    ModRecipeSerializers(IRecipeSerializer<?> recipe) {
        this.recipe = recipe;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public IRecipeSerializer<?> getRecipe() {
        if (recipe.getRegistryName() == null) {
            recipe.setRegistryName(new ResourceLocation(Main.MOD_ID, getName()));
        }
        return recipe;
    }
}
