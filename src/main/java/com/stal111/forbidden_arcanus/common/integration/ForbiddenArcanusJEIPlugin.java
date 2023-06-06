package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.SmithingCategory;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.UpgradeTierCategory;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

@JeiPlugin
public class ForbiddenArcanusJEIPlugin implements IModPlugin {

    public IRecipeCategory<?> hephaestusSmithing;
    public IRecipeCategory<?> hephaestusForgeUpgrading;
    public IRecipeCategory<?> clibanoCombustion;

    public static final RecipeType<Ritual> HEPHAESTUS_SMITHING = RecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_smithing", Ritual.class);
    public static final RecipeType<Ritual> HEPHAESTUS_FORGE_UPGRADING = RecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_forge_upgrading", Ritual.class);

    public static final RecipeType<ClibanoRecipe> CLIBANO_COMBUSTION = RecipeType.create(ForbiddenArcanus.MOD_ID, "clibano_combustion", ClibanoRecipe.class);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "main");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.SMITHING, ApplyModifierRecipeMaker.getRecipes());

        Registry<Ritual> registry = Minecraft.getInstance().level.registryAccess().registryOrThrow(FARegistries.RITUAL);

        registration.addRecipes(HEPHAESTUS_SMITHING, registry.stream().filter(ritual -> ritual.result() instanceof CreateItemResult).toList());
        registration.addRecipes(HEPHAESTUS_FORGE_UPGRADING, registry.stream().filter(ritual -> ritual.result() instanceof UpgradeTierResult).toList());

        registration.addRecipes(CLIBANO_COMBUSTION, Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.CLIBANO_COMBUSTION.get()));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), HEPHAESTUS_SMITHING, HEPHAESTUS_FORGE_UPGRADING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CLIBANO_CORE.get()), CLIBANO_COMBUSTION);
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                this.hephaestusSmithing = new SmithingCategory(guiHelper),
                this.hephaestusForgeUpgrading = new UpgradeTierCategory(guiHelper),
                this.clibanoCombustion = new ClibanoCombustionCategory(guiHelper)
        );
    }
}