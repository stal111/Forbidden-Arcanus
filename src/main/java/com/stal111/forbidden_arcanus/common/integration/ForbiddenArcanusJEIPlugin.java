package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.NamedRitual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

@JeiPlugin
public class ForbiddenArcanusJEIPlugin implements IModPlugin {

    public IRecipeCategory<?> hephaestusSmithing;
    public IRecipeCategory<?> clibanoCombustion;

    public static final RecipeType<NamedRitual> HEPHAESTUS_SMITHING = RecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_smithing", NamedRitual.class);
    public static final RecipeType<ClibanoRecipe> CLIBANO_COMBUSTION = RecipeType.create(ForbiddenArcanus.MOD_ID, "clibano_combustion", ClibanoRecipe.class);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.SMITHING, ApplyModifierRecipeMaker.getRecipes());
        registration.addRecipes(HEPHAESTUS_SMITHING, ForbiddenArcanus.INSTANCE.getRitualLoader().rituals.values().stream().filter(namedRitual -> namedRitual.get().result() instanceof CreateItemResult).toList());
        registration.addRecipes(CLIBANO_COMBUSTION, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.CLIBANO_COMBUSTION.get()));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), HEPHAESTUS_SMITHING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CLIBANO_CORE.get()), CLIBANO_COMBUSTION);
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                this.hephaestusSmithing = new HephaestusSmithingCategory(guiHelper),
                this.clibanoCombustion = new ClibanoCombustionCategory(guiHelper)
        );
    }
}