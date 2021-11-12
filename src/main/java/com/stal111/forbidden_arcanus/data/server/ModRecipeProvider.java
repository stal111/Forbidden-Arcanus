package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.valhelsia.valhelsia_core.common.util.ValhelsiaNBTIngredient;

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
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        //Shaped Recipes
        ShapedRecipeBuilder.shaped(NewModItems.SANITY_METER.get()).pattern("AXA").pattern("X#X").pattern("AXA").define('X', Tags.Items.INGOTS_GOLD).define('#', Tags.Items.ENDER_PEARLS).define('A', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(Tags.Items.INGOTS_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.LENS_OF_VERITATIS.get()).pattern(" # ").pattern("#X#").pattern("S# ").define('#', ModItems.SPAWNER_SCRAP.get()).define('X', ModItems.ARCANE_CRYSTAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.OBSIDIAN_SKULL.get()).pattern("#O#").pattern("OXO").pattern("#O#").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.SKELETON_SKULL).define('O', Tags.Items.OBSIDIAN).unlockedBy("has_item", has(Tags.Items.OBSIDIAN)).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.UTREM_JAR.get()).pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_COLORLESS).define('X', ModBlocks.EDELWOOD_PLANKS.getBlock()).unlockedBy("has_item", has(Tags.Items.GLASS_COLORLESS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_CRYSTAL_DUST.get()).pattern("###").pattern("###").pattern("###").define('#', NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).unlockedBy("has_item", has(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SOUL_EXTRACTOR.get()).pattern("U  ").pattern("##X").pattern("Q  ").define('U', NewModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy("has_item", has(NewModBlocks.UTREM_JAR.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_HYPHAE.get(), 3).pattern("##").pattern("##").define('#', NewModBlocks.FUNGYSS_STEM.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_STEM.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_SLAB.get(), 6).pattern("###").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_STAIRS.get(), 4).pattern("#  ").pattern("## ").pattern("###").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get()).pattern("##").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_TRAPDOOR.get(), 2).pattern("###").pattern("###").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_DOOR.get(), 3).pattern("##").pattern("##").pattern("##").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_FENCE.get(), 3).pattern("#X#").pattern("#X#").define('#', NewModBlocks.FUNGYSS_PLANKS.get()).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModBlocks.FUNGYSS_FENCE_GATE.get()).pattern("#X#").pattern("#X#").define('#', Tags.Items.RODS_WOODEN).define('X', NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.ARCANE_BONE_MEAL.get(), 4).pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.AUREAL_BOTTLE.get()).pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST.get()).define('X', new ValhelsiaNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.BLACKSMITH_GAVEL_HEAD.get()).pattern("###").pattern("# #").pattern(" # ").define('#', Items.CLAY_BALL).unlockedBy("has_item", has(Items.CLAY_BALL)).save(consumer);
        addBlacksmithGavelRecipe(NewModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS, consumer);
        addBlacksmithGavelRecipe(NewModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS, consumer);
        addBlacksmithGavelRecipe(NewModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD, consumer);
        addBlacksmithGavelRecipe(NewModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON, consumer);
        addBlacksmithGavelRecipe(NewModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND, consumer);
        addBlacksmithGavelRecipe(NewModItems.ARCANE_GOLDEN_BLACKSMITH_GAVEL.get(), ModTags.Items.ARCANE_GOLD_INGOTS, consumer);
        addBlacksmithGavelRecipe(NewModItems.OBSIDIAN_BLACKSMITH_GAVEL.get(), ModTags.Items.OBSIDIAN_INGOTS, consumer);

        //Shapeless Recipes
        ShapelessRecipeBuilder.shapeless(NewModItems.PURIFYING_SOAP.get()).requires(ModItems.ARCANE_CRYSTAL_DUST.get()).requires(ModItems.WAX.get()).requires(Items.SLIME_BALL).requires(Items.PRISMARINE_CRYSTALS).requires(ItemTags.SMALL_FLOWERS).unlockedBy("has_item", has(ModItems.WAX.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(NewModBlocks.FUNGYSS_PLANKS.get(), 4).requires(ModTags.Items.FUNGYSS_STEMS).unlockedBy("has_item", has(ModTags.Items.FUNGYSS_STEMS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(NewModBlocks.FUNGYSS_BUTTON.get()).requires(NewModBlocks.FUNGYSS_PLANKS.get()).unlockedBy("has_item", has(NewModBlocks.FUNGYSS_PLANKS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(NewModItems.TEST_TUBE.get()).requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE.get()).unlockedBy("has_item", has(ModItems.RUNE.get())).save(consumer);

        //Smelting Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer, "forbidden_arcanus:smelting/arcane_crystal_dust_from_smelting");

        //Blasting Recipes
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer,  "forbidden_arcanus:blasting/arcane_crystal_dust_from_blasting");

        //Smithing Recipes
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.SHIELD), Ingredient.of(NewModItems.OBSIDIAN_SKULL.get()), NewModItems.OBSIDIAN_SKULL_SHIELD.get()).unlocks("has_item", has(NewModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_skull_shield"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(NewModItems.OBSIDIAN_SKULL.get()), Ingredient.of(ModItems.ETERNAL_STELLA.get()), NewModItems.ETERNAL_OBSIDIAN_SKULL.get()).unlocks("has_item", has(NewModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(NewModItems.NETHERITE_BLACKSMITH_GAVEL.get()), Ingredient.of(Items.NETHERITE_INGOT), NewModItems.DIAMOND_BLACKSMITH_GAVEL.get()).unlocks("has_item", has(Items.NETHERITE_INGOT)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_blacksmith_gavel"));
    }

    private void addBlacksmithGavelRecipe(Item gavel, Tag.Named<Item> material, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(gavel).pattern("#X#").pattern("#S#").pattern(" S ").define('#', material).define('X', NewModItems.BLACKSMITH_GAVEL_HEAD.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(material)).save(consumer);
    }
}
