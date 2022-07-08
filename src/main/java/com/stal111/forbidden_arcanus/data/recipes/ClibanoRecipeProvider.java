package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2022-06-26
 */
public class ClibanoRecipeProvider extends RecipeProvider {

    public static final double CHANCE_33 = 0.33D;
    public static final double CHANCE_20 = 0.2D;
    public static final double CHANCE_10 = 0.1D;
    public static final double CHANCE_05 = 0.05D;

    public ClibanoRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        ClibanoRecipeBuilder.of(ModItems.ARCANE_CRYSTAL.get(), Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), 1.0F, 100, new ClibanoRecipe.ResidueInfo("arcane_crystal", CHANCE_33)).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(ModItems.ARCANE_CRYSTAL_DUST.get(), Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 0.4F, 80, new ClibanoRecipe.ResidueInfo("iron", CHANCE_10)).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer);
        ClibanoRecipeBuilder.of(ModItems.RUNE.get(), Ingredient.of(ModTags.Items.RUNIC_STONES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("rune", CHANCE_10)).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)).save(consumer);

        ClibanoRecipeBuilder.of(Items.IRON_INGOT, Ingredient.of(ItemTags.IRON_ORES), 0.35F, 100, new ClibanoRecipe.ResidueInfo("iron", CHANCE_33)).unlockedBy("has_item", has(ItemTags.IRON_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.GOLD_INGOT, Ingredient.of(ItemTags.GOLD_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("gold", CHANCE_20)).unlockedBy("has_item", has(ItemTags.GOLD_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.COPPER_INGOT, Ingredient.of(ItemTags.COPPER_ORES), 0.35F, 100, new ClibanoRecipe.ResidueInfo("copper", CHANCE_33)).unlockedBy("has_item", has(ItemTags.COPPER_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.LAPIS_LAZULI, Ingredient.of(ItemTags.LAPIS_ORES), 0.1F, 100, new ClibanoRecipe.ResidueInfo("lapis_lazuli", CHANCE_20)).unlockedBy("has_item", has(ItemTags.LAPIS_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.DIAMOND, Ingredient.of(ItemTags.DIAMOND_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("diamond", CHANCE_10)).unlockedBy("has_item", has(ItemTags.DIAMOND_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.EMERALD, Ingredient.of(ItemTags.EMERALD_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("emerald", CHANCE_10)).unlockedBy("has_item", has(ItemTags.EMERALD_ORES)).save(consumer);
        ClibanoRecipeBuilder.of(Items.NETHERITE_SCRAP, Ingredient.of(Blocks.ANCIENT_DEBRIS), 1.0F, 100, new ClibanoRecipe.ResidueInfo("netherite", CHANCE_05)).unlockedBy("has_item", has(Blocks.ANCIENT_DEBRIS)).save(consumer);

        CombineResiduesRecipeBuilder.of("iron", 9, Blocks.IRON_BLOCK).save(consumer);
        CombineResiduesRecipeBuilder.of("gold", 9, Blocks.GOLD_BLOCK).save(consumer);

    }
}
