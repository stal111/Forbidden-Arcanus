package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

/**
 * Mod Recipe Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.ModRecipeProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-28
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(NewModItems.SANITY_METER.get())
                .patternLine("AXA")
                .patternLine("X#X")
                .patternLine("AXA")
                .key('X', Tags.Items.INGOTS_GOLD)
                .key('#', Tags.Items.ENDER_PEARLS)
                .key('A', ModItems.ARCANE_CRYSTAL_DUST.get())
                .addCriterion("has_item", hasItem(Tags.Items.INGOTS_GOLD))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(NewModItems.LENS_OF_VERITATIS.get())
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine("S# ")
                .key('#', ModItems.SPAWNER_SCRAP.get())
                .key('X', ModItems.ARCANE_CRYSTAL.get())
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(NewModItems.PURIFYING_SOAP.get())
                .addIngredient(ModItems.ARCANE_CRYSTAL_DUST.get())
                .addIngredient(ModItems.WAX.get())
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(Items.PRISMARINE_CRYSTALS)
                .addIngredient(ItemTags.SMALL_FLOWERS)
                .addCriterion("has_item", hasItem(ModItems.WAX.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(NewModItems.OBSIDIAN_SKULL.get())
                .patternLine("#O#")
                .patternLine("OXO")
                .patternLine("#O#")
                .key('#', ModItems.OBSIDIAN_INGOT.get())
                .key('X', Items.SKELETON_SKULL)
                .key('O', Tags.Items.OBSIDIAN)
                .addCriterion("has_item", hasItem(Tags.Items.OBSIDIAN))
                .build(consumer);

        SmithingRecipeBuilder.smithingRecipe(
                Ingredient.fromItems(Items.SHIELD),
                Ingredient.fromItems(NewModItems.OBSIDIAN_SKULL.get()),
                NewModItems.OBSIDIAN_SKULL_SHIELD.get())
                .addCriterion("has_item", hasItem(NewModItems.OBSIDIAN_SKULL.get()))
                .build(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_skull_shield"));

        SmithingRecipeBuilder.smithingRecipe(
                Ingredient.fromItems(NewModItems.OBSIDIAN_SKULL.get()),
                Ingredient.fromItems(ModItems.ETERNAL_STELLA.get()),
                NewModItems.ETERNAL_OBSIDIAN_SKULL.get())
                .addCriterion("has_item", hasItem(NewModItems.OBSIDIAN_SKULL.get()))
                .build(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));

        ShapedRecipeBuilder.shapedRecipe(NewerModBlocks.UTREM_JAR.get()).patternLine("#X#").patternLine("# #").patternLine("###").key('#', Tags.Items.GLASS_COLORLESS).key('X', ModBlocks.EDELWOOD_PLANKS.getBlock()).addCriterion("has_item", hasItem(Tags.Items.GLASS_COLORLESS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ARCANE_CRYSTAL_DUST.get()).patternLine("###").patternLine("###").patternLine("###").key('#', NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).addCriterion("has_item", hasItem(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SOUL_EXTRACTOR.get()).patternLine("U  ").patternLine("##X").patternLine("Q  ").key('U', NewerModBlocks.UTREM_JAR.get()).key('#', Blocks.NETHER_BRICKS).key('X', Blocks.QUARTZ_BLOCK).key('Q', Items.QUARTZ).addCriterion("has_item", hasItem(NewerModBlocks.UTREM_JAR.get())).build(consumer);

        //Smelting Recipes
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get())).build(consumer, "forbidden_arcanus:smelting/arcane_crystal_dust_from_smelting");

        //Blasting Recipes
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get())).build(consumer,  "forbidden_arcanus:blasting/arcane_crystal_dust_from_blasting");
    }
}
