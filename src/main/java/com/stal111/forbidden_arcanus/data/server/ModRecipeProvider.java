package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.valhelsia.valhelsia_core.util.ValhelsiaNBTIngredient;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * Mod Recipe Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.ModRecipeProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-01-28
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        //Shaped Recipes
        ShapedRecipeBuilder.shapedRecipe(NewModItems.SANITY_METER.get()).patternLine("AXA").patternLine("X#X").patternLine("AXA").key('X', Tags.Items.INGOTS_GOLD).key('#', Tags.Items.ENDER_PEARLS).key('A', ModItems.ARCANE_CRYSTAL_DUST.get()).addCriterion("has_item", hasItem(Tags.Items.INGOTS_GOLD)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModItems.LENS_OF_VERITATIS.get()).patternLine(" # ").patternLine("#X#").patternLine("S# ").key('#', ModItems.SPAWNER_SCRAP.get()).key('X', ModItems.ARCANE_CRYSTAL.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModItems.OBSIDIAN_SKULL.get()).patternLine("#O#").patternLine("OXO").patternLine("#O#").key('#', ModItems.OBSIDIAN_INGOT.get()).key('X', Items.SKELETON_SKULL).key('O', Tags.Items.OBSIDIAN).addCriterion("has_item", hasItem(Tags.Items.OBSIDIAN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.UTREM_JAR.get()).patternLine("#X#").patternLine("# #").patternLine("###").key('#', Tags.Items.GLASS_COLORLESS).key('X', ModBlocks.EDELWOOD_PLANKS.getBlock()).addCriterion("has_item", hasItem(Tags.Items.GLASS_COLORLESS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.ARCANE_CRYSTAL_DUST.get()).patternLine("###").patternLine("###").patternLine("###").key('#', NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).addCriterion("has_item", hasItem(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SOUL_EXTRACTOR.get()).patternLine("U  ").patternLine("##X").patternLine("Q  ").key('U', NewModBlocks.UTREM_JAR.get()).key('#', Blocks.NETHER_BRICKS).key('X', Blocks.QUARTZ_BLOCK).key('Q', Items.QUARTZ).addCriterion("has_item", hasItem(NewModBlocks.UTREM_JAR.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_HYPHAE.get(), 3).patternLine("##").patternLine("##").key('#', NewModBlocks.FUNGYSS_STEM.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_STEM.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_SLAB.get(), 6).patternLine("###").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_STAIRS.get(), 4).patternLine("#  ").patternLine("## ").patternLine("###").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get()).patternLine("##").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_TRAPDOOR.get(), 2).patternLine("###").patternLine("###").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_DOOR.get(), 3).patternLine("##").patternLine("##").patternLine("##").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_FENCE.get(), 3).patternLine("#X#").patternLine("#X#").key('#', NewModBlocks.FUNGYSS_PLANKS.get()).key('X', Tags.Items.RODS_WOODEN).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModBlocks.FUNGYSS_FENCE_GATE.get()).patternLine("#X#").patternLine("#X#").key('#', Tags.Items.RODS_WOODEN).key('X', NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModItems.ARCANE_BONE_MEAL.get(), 4).patternLine(" # ").patternLine("#X#").patternLine(" # ").key('#', Items.BONE_MEAL).key('X', ModItems.ARCANE_CRYSTAL_DUST.get()).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL_DUST.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModItems.AUREAL_BOTTLE.get()).patternLine("###").patternLine("#X#").patternLine("###").key('#', ModItems.ARCANE_CRYSTAL_DUST.get()).key('X', new ValhelsiaNBTIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL_DUST.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(NewModItems.BLACKSMITH_GAVEL_HEAD.get()).patternLine("###").patternLine("# #").patternLine(" # ").key('#', Items.CLAY_BALL).addCriterion("has_item", hasItem(Items.CLAY_BALL)).build(consumer);
        addBlacksmithGavelRecipe(NewModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS, consumer);
        addBlacksmithGavelRecipe(NewModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS, consumer);
        addBlacksmithGavelRecipe(NewModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD, consumer);
        addBlacksmithGavelRecipe(NewModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON, consumer);
        addBlacksmithGavelRecipe(NewModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND, consumer);
        addBlacksmithGavelRecipe(NewModItems.ARCANE_GOLDEN_BLACKSMITH_GAVEL.get(), ModTags.Items.ARCANE_GOLD_INGOTS, consumer);
        addBlacksmithGavelRecipe(NewModItems.OBSIDIAN_BLACKSMITH_GAVEL.get(), ModTags.Items.OBSIDIAN_INGOTS, consumer);

        //Shapeless Recipes
        ShapelessRecipeBuilder.shapelessRecipe(NewModItems.PURIFYING_SOAP.get()).addIngredient(ModItems.ARCANE_CRYSTAL_DUST.get()).addIngredient(ModItems.WAX.get()).addIngredient(Items.SLIME_BALL).addIngredient(Items.PRISMARINE_CRYSTALS).addIngredient(ItemTags.SMALL_FLOWERS).addCriterion("has_item", hasItem(ModItems.WAX.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(NewModBlocks.FUNGYSS_PLANKS.get(), 4).addIngredient(ModTags.Items.FUNGYSS_STEMS).addCriterion("has_item", hasItem(ModTags.Items.FUNGYSS_STEMS)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(NewModBlocks.FUNGYSS_BUTTON.get()).addIngredient(NewModBlocks.FUNGYSS_PLANKS.get()).addCriterion("has_item", hasItem(NewModBlocks.FUNGYSS_PLANKS.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(NewModItems.TEST_TUBE.get()).addIngredient(Items.GLASS_BOTTLE).addIngredient(ModItems.RUNE.get()).addCriterion("has_item", hasItem(ModItems.RUNE.get())).build(consumer);

        //Smelting Recipes
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get())).build(consumer, "forbidden_arcanus:smelting/arcane_crystal_dust_from_smelting");

        //Blasting Recipes
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).addCriterion("has_item", hasItem(ModItems.ARCANE_CRYSTAL.get())).build(consumer,  "forbidden_arcanus:blasting/arcane_crystal_dust_from_blasting");

        //Smithing Recipes
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(Items.SHIELD), Ingredient.fromItems(NewModItems.OBSIDIAN_SKULL.get()), NewModItems.OBSIDIAN_SKULL_SHIELD.get()).addCriterion("has_item", hasItem(NewModItems.OBSIDIAN_SKULL.get())).build(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_skull_shield"));
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(NewModItems.OBSIDIAN_SKULL.get()), Ingredient.fromItems(ModItems.ETERNAL_STELLA.get()), NewModItems.ETERNAL_OBSIDIAN_SKULL.get()).addCriterion("has_item", hasItem(NewModItems.OBSIDIAN_SKULL.get())).build(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(NewModItems.NETHERITE_BLACKSMITH_GAVEL.get()), Ingredient.fromItems(Items.NETHERITE_INGOT), NewModItems.DIAMOND_BLACKSMITH_GAVEL.get()).addCriterion("has_item", hasItem(Items.NETHERITE_INGOT)).build(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_blacksmith_gavel"));
    }

    private void addBlacksmithGavelRecipe(Item gavel, ITag.INamedTag<Item> material, @Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(gavel).patternLine("#X#").patternLine("#S#").patternLine(" S ").key('#', material).key('X', NewModItems.BLACKSMITH_GAVEL_HEAD.get()).key('S', Tags.Items.RODS_WOODEN).addCriterion("has_item", hasItem(material)).build(consumer);
    }
}
