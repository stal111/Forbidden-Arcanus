package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class ForbiddenArcanusJEIPlugin implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ApplyIndestructibleEnchantmentMaker.getRecipes(), VanillaRecipeCategoryUid.SMITHING);
    }
}