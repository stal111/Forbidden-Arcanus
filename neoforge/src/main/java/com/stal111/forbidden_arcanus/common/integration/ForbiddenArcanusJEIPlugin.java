package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.research.ResearchScreen;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.TransmuteInputResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.SmithingCategory;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.UpgradeTierCategory;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ForbiddenArcanusJEIPlugin implements IModPlugin {

    public IRecipeCategory<?> hephaestusSmithing;
    public IRecipeCategory<?> hephaestusForgeUpgrading;
    public IRecipeCategory<?> clibanoCombustion;

    public static final IRecipeType<Ritual> HEPHAESTUS_SMITHING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_smithing", Ritual.class);
    public static final IRecipeType<Ritual> HEPHAESTUS_FORGE_UPGRADING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_forge_upgrading", Ritual.class);

    public static final IRecipeType<ClibanoRecipe> CLIBANO_COMBUSTION = IRecipeType.create(ForbiddenArcanus.MOD_ID, "clibano_combustion", ClibanoRecipe.class);

    @NotNull
    @Override
    public Identifier getPluginUid() {
        return ForbiddenArcanus.identifier("main");
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addGuiScreenHandler(ResearchScreen.class, guiScreen -> null);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;

//        registration.addRecipes(RecipeTypes.SMITHING, ApplyModifierRecipeMaker.getRecipes());

        Registry<Ritual> registry = level.registryAccess().lookupOrThrow(FARegistries.RITUAL);

        registration.addRecipes(HEPHAESTUS_SMITHING, registry.stream().filter(ritual -> ritual.result() instanceof CreateItemResult || ritual.result() instanceof TransmuteInputResult).toList());
        registration.addRecipes(HEPHAESTUS_FORGE_UPGRADING, registry.stream().filter(ritual -> ritual.result() instanceof UpgradeTierResult).toList());

//        registration.addRecipes(CLIBANO_COMBUSTION, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CLIBANO_COMBUSTION.get()).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(HEPHAESTUS_SMITHING, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        registration.addCraftingStation(HEPHAESTUS_FORGE_UPGRADING, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        registration.addCraftingStation(CLIBANO_COMBUSTION, new ItemStack(ModBlocks.CLIBANO_CORE.get()));
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                this.hephaestusSmithing = new SmithingCategory(guiHelper),
                this.hephaestusForgeUpgrading = new UpgradeTierCategory(guiHelper),
                this.clibanoCombustion = new ClibanoCombustionCategory(guiHelper)
        );
    }
}