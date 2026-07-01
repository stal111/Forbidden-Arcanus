package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import com.stal111.forbidden_arcanus.client.gui.screen.research.ResearchScreen;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.TransmuteInputResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.HephaestusForgeGuiContainerHandler;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.SmithingCategory;
import com.stal111.forbidden_arcanus.common.integration.hephaestus_forge.UpgradeTierCategory;
import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.cache.ClibanoRecipeCache;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.IExtendableSmithingRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ForbiddenArcanusJEIPlugin implements IModPlugin {

    public IRecipeCategory<?> hephaestusSmithing;
    public IRecipeCategory<?> hephaestusForgeUpgrading;
    public IRecipeCategory<?> clibanoMelting;
    public IRecipeCategory<?> clibanoAlloying;

    public static final IRecipeType<Ritual> HEPHAESTUS_SMITHING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_smithing", Ritual.class);
    public static final IRecipeType<Ritual> HEPHAESTUS_FORGE_UPGRADING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "hephaestus_forge_upgrading", Ritual.class);

    public static final IRecipeType<ClibanoMeltingRecipe> CLIBANO_MELTING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "clibano_melting", ClibanoMeltingRecipe.class);
    public static final IRecipeType<ClibanoAlloyingRecipe> CLIBANO_ALLOYING = IRecipeType.create(ForbiddenArcanus.MOD_ID, "clibano_alloying", ClibanoAlloyingRecipe.class);

    @NotNull
    @Override
    public Identifier getPluginUid() {
        return ForbiddenArcanus.identifier("main");
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addGuiScreenHandler(ResearchScreen.class, guiScreen -> null);
        registration.addGuiContainerHandler(HephaestusForgeScreen.class, new HephaestusForgeGuiContainerHandler());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;

        Registry<Ritual> registry = level.registryAccess().lookupOrThrow(FARegistries.RITUAL);

        registration.addRecipes(HEPHAESTUS_SMITHING, registry.stream().filter(ritual -> ritual.result() instanceof CreateItemResult || ritual.result() instanceof TransmuteInputResult).toList());
        registration.addRecipes(HEPHAESTUS_FORGE_UPGRADING, registry.stream().filter(ritual -> ritual.result() instanceof UpgradeTierResult).toList());

        registration.addRecipes(CLIBANO_MELTING, ClibanoRecipeCache.meltingRecipes.stream().map(RecipeHolder::value).toList());
        registration.addRecipes(CLIBANO_ALLOYING, ClibanoRecipeCache.alloyingRecipes.stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(HEPHAESTUS_SMITHING, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        registration.addCraftingStation(HEPHAESTUS_FORGE_UPGRADING, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        registration.addCraftingStation(CLIBANO_MELTING, new ItemStack(ModBlocks.CLIBANO_CORE.get()));
        registration.addCraftingStation(CLIBANO_ALLOYING, new ItemStack(ModBlocks.CLIBANO_CORE.get()));
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                this.hephaestusSmithing = new SmithingCategory(guiHelper),
                this.hephaestusForgeUpgrading = new UpgradeTierCategory(guiHelper),
                this.clibanoMelting = new ClibanoMeltingCategory(guiHelper),
                this.clibanoAlloying = new ClibanoAlloyingCategory(guiHelper)
        );
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IExtendableSmithingRecipeCategory smithingCategory = registration.getSmithingCategory();

        smithingCategory.addExtension(ApplyModifierRecipe.class, new ApplyModifierCategoryExtension());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerFromDataComponentTypes(ModItems.HEPHAESTUS_FORGE.get(), DataComponents.BLOCK_STATE);
        registration.registerFromDataComponentTypes(ModItems.ESSENCE_UTREM_JAR.get(), ModDataComponents.ESSENCE_STORAGE.get());
    }
}
