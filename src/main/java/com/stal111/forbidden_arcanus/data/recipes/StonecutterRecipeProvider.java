package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.datagen.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.datagen.recipes.ValhelsiaRecipeProvider;

/**
 * @author stal111
 * @since 17.03.2024
 */
public class StonecutterRecipeProvider extends RecipeSubProvider {

    public StonecutterRecipeProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
    }

    @Override
    protected void registerRecipes(HolderLookup.Provider provider) {
        this.add(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2);
        this.add(ModBlocks.SOULLESS_SANDSTONE_WALL.get(), ModBlocks.SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2);
        this.add(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.CUT_SOULLESS_SANDSTONE.get(), 2);
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 2);
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get());
        this.add(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), 2);
    }

    private void add(ItemLike result, ItemLike material) {
        this.add(result, material, 1);
    }

    private void add(ItemLike result, ItemLike material, int count) {
        this.add(SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count).unlockedBy("has_" + getName(material), has(material)), getName(result) + "_from_" + getName(material) + "_stonecutting");
    }
}
