package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.datagen.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.datagen.recipes.ValhelsiaRecipeProvider;

import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 2022-06-26
 */
public class ClibanoRecipeProvider extends RecipeSubProvider {

    public static final double CHANCE_65 = 0.65D;
    public static final double CHANCE_33 = 0.33D;
    public static final double CHANCE_20 = 0.2D;
    public static final double CHANCE_10 = 0.1D;
    public static final double CHANCE_05 = 0.05D;

    public ClibanoRecipeProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
    }

    @Override
    protected void registerRecipes(CompletableFuture<HolderLookup.Provider> lookupProvider) {
//        this.add(this.clibanoRecipe(ModItems.ARCANE_CRYSTAL.get(), Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), 1.0F, 100, new ResidueChance("arcane_crystal", CHANCE_33)).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)));
//        this.add(this.clibanoRecipe(ModItems.ARCANE_CRYSTAL_DUST.get(), Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 0.4F, 80, new ClibanoRecipe.ResidueInfo("arcane_crystal", CHANCE_10)).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())));
//        this.add(this.clibanoRecipe(ModItems.RUNE.get(), Ingredient.of(ModTags.Items.RUNIC_STONES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("rune", CHANCE_10)).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)));
//
//        this.add(this.clibanoRecipe(Items.COAL, Ingredient.of(ItemTags.COAL_ORES), 0.05F, 100, new ClibanoRecipe.ResidueInfo("coal", CHANCE_33)).unlockedBy("has_item", has(ItemTags.COAL_ORES)));
//        this.add(this.clibanoRecipe(Items.IRON_INGOT, Ingredient.of(ItemTags.IRON_ORES), 0.35F, 100, new ClibanoRecipe.ResidueInfo("iron", CHANCE_33)).unlockedBy("has_item", has(ItemTags.IRON_ORES)));
//        this.add(this.clibanoRecipe(Items.GOLD_INGOT, Ingredient.of(ItemTags.GOLD_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("gold", CHANCE_20)).unlockedBy("has_item", has(ItemTags.GOLD_ORES)));
//        this.add(this.clibanoRecipe(Items.COPPER_INGOT, Ingredient.of(ItemTags.COPPER_ORES), 0.35F, 100, new ClibanoRecipe.ResidueInfo("copper", CHANCE_33)).unlockedBy("has_item", has(ItemTags.COPPER_ORES)));
//        this.add(this.clibanoRecipe(Items.LAPIS_LAZULI, Ingredient.of(ItemTags.LAPIS_ORES), 0.1F, 100, new ClibanoRecipe.ResidueInfo("lapis_lazuli", CHANCE_20)).unlockedBy("has_item", has(ItemTags.LAPIS_ORES)));
//        this.add(this.clibanoRecipe(Items.DIAMOND, Ingredient.of(ItemTags.DIAMOND_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("diamond", CHANCE_10)).unlockedBy("has_item", has(ItemTags.DIAMOND_ORES)));
//        this.add(this.clibanoRecipe(Items.EMERALD, Ingredient.of(ItemTags.EMERALD_ORES), 0.5F, 100, new ClibanoRecipe.ResidueInfo("emerald", CHANCE_10)).unlockedBy("has_item", has(ItemTags.EMERALD_ORES)));
//        this.add(this.clibanoRecipe(Items.NETHERITE_SCRAP, Ingredient.of(Blocks.ANCIENT_DEBRIS), 1.0F, 100, new ClibanoRecipe.ResidueInfo("netherite", CHANCE_05)).unlockedBy("has_item", has(Blocks.ANCIENT_DEBRIS)));
//
//        this.add(this.clibanoRecipe(ModItems.DEORUM_NUGGET.get(), Ingredient.of(ModBlocks.GILDED_DARKSTONE.get()), 1.0F, 150, new ClibanoRecipe.ResidueInfo("deorum", CHANCE_65), ClibanoFireType.SOUL_FIRE).unlockedBy("has_item", has(ModBlocks.GILDED_DARKSTONE.get())));
//
//        this.add(this.clibanoRecipe(Items.IRON_INGOT, Ingredient.of(Tags.Items.RAW_MATERIALS_IRON), 0.35F, 100, new ClibanoRecipe.ResidueInfo("iron", CHANCE_33)).unlockedBy("has_item", has(Tags.Items.RAW_MATERIALS_IRON)), "clibano_combustion/iron_ingot_from_clibano_combusting_raw_iron");
//        this.add(this.clibanoRecipe(Items.GOLD_INGOT, Ingredient.of(Tags.Items.RAW_MATERIALS_GOLD), 0.5F, 100, new ClibanoRecipe.ResidueInfo("gold", CHANCE_20)).unlockedBy("has_item", has(Tags.Items.RAW_MATERIALS_GOLD)), "clibano_combustion/gold_ingot_from_clibano_combusting_raw_gold");
//        this.add(this.clibanoRecipe(Items.COPPER_INGOT, Ingredient.of(Tags.Items.RAW_MATERIALS_COPPER), 0.35F, 100, new ClibanoRecipe.ResidueInfo("copper", CHANCE_33)).unlockedBy("has_item", has(Tags.Items.RAW_MATERIALS_COPPER)), "clibano_combustion/copper_ingot_from_clibano_combusting_raw_copper");

//        this.combineResidue("arcane_crystal", 9, new ItemStack(ModBlocks.ARCANE_CRYSTAL_BLOCK.get()));
//        this.combineResidue("rune", 9, new ItemStack(ModBlocks.RUNE_BLOCK.get()));
//
//        this.combineResidue("iron", 9, new ItemStack(Blocks.IRON_BLOCK));
//        this.combineResidue("gold", 9, new ItemStack(Blocks.GOLD_BLOCK));
//        this.combineResidue("copper", 9, new ItemStack(Blocks.COPPER_BLOCK));
//        this.combineResidue("lapis_lazuli", 9, new ItemStack(Blocks.LAPIS_BLOCK));
//        this.combineResidue("diamond", 9, new ItemStack(Blocks.DIAMOND_BLOCK));
//        this.combineResidue("emerald", 9, new ItemStack(Blocks.EMERALD_BLOCK));
//        this.combineResidue("netherite", 9, new ItemStack(Blocks.NETHERITE_BLOCK));
//
//        this.combineResidue("deorum", 1, new ItemStack(ModItems.DEORUM_NUGGET.get(), 2));
    }

    private void combineResidue(String name, int amount, ItemStack result) {
      //  this.add(CombineResiduesRecipeBuilder.of(name, amount, result));
    }

    private RecipeBuilder clibanoRecipe(ItemLike result, Ingredient ingredient, float experience, int cookingTime, ResidueChance chance) {
        return this.clibanoRecipe(result, ingredient, experience, cookingTime, chance, ClibanoFireType.FIRE);
    }

    private RecipeBuilder clibanoRecipe(ItemLike result, Ingredient ingredient, float experience, int cookingTime, ResidueChance chance, ClibanoFireType fireType) {
        return SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, result, experience, cookingTime, ModRecipeSerializers.CLIBANO_SERIALIZER.get(), (group, category, i, r, xp, time) -> new ClibanoRecipe(group, category, i, r, xp, time, chance, fireType));
    }
}
