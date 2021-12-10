package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.valhelsia.valhelsia_core.common.util.ValhelsiaNBTIngredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Mod Recipe Provider <br>
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
        ShapedRecipeBuilder.shaped(NewModBlocks.UTREM_JAR.get()).pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_COLORLESS).define('X', NewModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_item", has(Tags.Items.GLASS_COLORLESS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_CRYSTAL_DUST.get()).pattern("###").pattern("###").pattern("###").define('#', NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).unlockedBy("has_item", has(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SOUL_EXTRACTOR.get()).pattern("U  ").pattern("##X").pattern("Q  ").define('U', NewModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy("has_item", has(NewModBlocks.UTREM_JAR.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.ARCANE_BONE_MEAL.get(), 4).pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        ShapedRecipeBuilder.shaped(NewModItems.AUREAL_BOTTLE.get()).pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST.get()).define('X', new ValhelsiaNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        this.addSurroundingItemRecipe(NewModBlocks.ARCANE_CHISELED_DARKSTONE.get(), ModTags.Items.ARCANE_GOLD_INGOTS, NewModBlocks.DARKSTONE.get(), 8, consumer);

        ShapedRecipeBuilder.shaped(NewModItems.BLACKSMITH_GAVEL_HEAD.get()).pattern("###").pattern("# #").pattern(" # ").define('#', Items.CLAY_BALL).unlockedBy("has_item", has(Items.CLAY_BALL)).save(consumer);
        this.addBlacksmithGavelRecipe(NewModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS, consumer);
        this.addBlacksmithGavelRecipe(NewModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS, consumer);
        this.addBlacksmithGavelRecipe(NewModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD, consumer);
        this.addBlacksmithGavelRecipe(NewModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON, consumer);
        this.addBlacksmithGavelRecipe(NewModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND, consumer);
        this.addBlacksmithGavelRecipe(NewModItems.ARCANE_GOLDEN_BLACKSMITH_GAVEL.get(), ModTags.Items.ARCANE_GOLD_INGOTS, consumer);
        ShapedRecipeBuilder.shaped(NewModItems.DARK_NETHER_STAR.get()).pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.NETHER_STAR).unlockedBy("has_obsidian_ingot", has(ModItems.OBSIDIAN_INGOT.get())).unlockedBy("has_nether_star", has(Items.NETHER_STAR)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_GOLD_INGOT.get()).pattern("#*#").pattern("MXM").pattern("#*#").define('#', Items.CHARCOAL).define('X', Items.GOLD_INGOT).define('M', ModItems.MUNDABITUR_DUST.get()).define('*', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).unlockedBy("has_arcane_crystal_dust", has(ModItems.ARCANE_CRYSTAL_DUST.get())).unlockedBy("has_mundabitur_dust", has(ModItems.MUNDABITUR_DUST.get())).save(consumer);

        this.addStorageRecipe(NewModItems.DARK_NETHER_STAR.get(), NewModBlocks.DARK_NETHER_STAR_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.OBSIDIAN_INGOT.get(), NewModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), "obsidian_ingot", null, consumer);
        this.addStorageRecipe(ModItems.ARCANE_GOLD_INGOT.get(), NewModBlocks.ARCANE_GOLD_BLOCK.get(), "arcane_gold_ingot", null, consumer);
        this.addStorageRecipe(ModItems.ARCANE_GOLD_NUGGET.get(), ModItems.ARCANE_GOLD_INGOT.get(), null, "arcane_gold_ingot", consumer);
        this.addStorageRecipe(NewModItems.STELLARITE_PIECE.get(), NewModBlocks.STELLARITE_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.ARCANE_CRYSTAL.get(), NewModBlocks.ARCANE_CRYSTAL_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.RUNE.get(), NewModBlocks.RUNE_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.DARK_RUNE.get(), NewModBlocks.DARK_RUNE_BLOCK.get(), consumer);

        this.addSurroundingItemRecipe(NewModBlocks.ARCANE_GOLDEN_GLASS.get(), ModItems.ARCANE_GOLD_INGOT.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(NewModBlocks.ARCANE_GOLDEN_GLASS.get(), NewModBlocks.ARCANE_GOLDEN_GLASS_PANE.get(), consumer);
        this.addSurroundingItemRecipe(NewModBlocks.RUNIC_GLASS.get(), ModItems.RUNE.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(NewModBlocks.RUNIC_GLASS.get(), NewModBlocks.RUNIC_GLASS_PANE.get(), consumer);
        this.addSurroundingItemRecipe(NewModBlocks.DARK_RUNIC_GLASS.get(), ModItems.DARK_RUNE.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(NewModBlocks.DARK_RUNIC_GLASS.get(), NewModBlocks.DARK_RUNIC_GLASS_PANE.get(), consumer);

        ShapedRecipeBuilder.shaped(NewModBlocks.ARCANE_GOLDEN_CHAIN.get()).pattern("#").pattern("X").pattern("#").define('#', ModTags.Items.ARCANE_GOLD_NUGGETS).define('X', ModTags.Items.ARCANE_GOLD_INGOTS).unlockedBy("has_ingot", has(ModTags.Items.ARCANE_GOLD_INGOTS)).unlockedBy("has_nugget", has(ModTags.Items.ARCANE_GOLD_NUGGETS)).save(consumer);

        this.addWoodRecipe(NewModBlocks.FUNGYSS_HYPHAE.get(), NewModBlocks.FUNGYSS_STEM.get(), consumer);
        this.addWoodRecipe(NewModBlocks.CHERRYWOOD.get(), NewModBlocks.CHERRYWOOD_LOG.get(), consumer);
        this.addWoodRecipe(NewModBlocks.MYSTERYWOOD.get(), NewModBlocks.MYSTERYWOOD_LOG.get(), consumer);

        ShapedRecipeBuilder.shaped(NewModBlocks.CARVED_CHERRYWOOD_PLANKS.get(), 4).pattern(" # ").pattern("# #").pattern(" # ").define('#', NewModBlocks.CHERRYWOOD_PLANKS.get()).unlockedBy("has_planks", has(NewModBlocks.CHERRYWOOD_PLANKS.get())).save(consumer);
        this.addSurroundingItemRecipe(NewModBlocks.ARCANE_EDELWOOD_PLANKS.get(), ModItems.ARCANE_GOLD_INGOT.get(), NewModBlocks.EDELWOOD_PLANKS.get(), 8, consumer);

        this.addSlabRecipe(NewModBlocks.FUNGYSS_SLAB.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.CHERRYWOOD_SLAB.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.MYSTERYWOOD_SLAB.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.EDELWOOD_SLAB.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addSlabRecipe(NewModBlocks.FUNGYSS_STAIRS.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.CHERRYWOOD_STAIRS.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.MYSTERYWOOD_STAIRS.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addSlabRecipe(NewModBlocks.EDELWOOD_STAIRS.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addDoorRecipe(NewModBlocks.ARCANE_GOLD_DOOR.get(), ModItems.ARCANE_GOLD_INGOT.get(), consumer);
        this.addDoorRecipe(NewModBlocks.FUNGYSS_DOOR.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addDoorRecipe(NewModBlocks.CHERRYWOOD_DOOR.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addDoorRecipe(NewModBlocks.MYSTERYWOOD_DOOR.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addDoorRecipe(NewModBlocks.EDELWOOD_DOOR.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addTrapdoorRecipe(NewModBlocks.ARCANE_GOLD_TRAPDOOR.get(), ModItems.ARCANE_GOLD_INGOT.get(), consumer);
        this.addTrapdoorRecipe(NewModBlocks.FUNGYSS_TRAPDOOR.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(NewModBlocks.CHERRYWOOD_TRAPDOOR.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(NewModBlocks.MYSTERYWOOD_TRAPDOOR.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(NewModBlocks.EDELWOOD_TRAPDOOR.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addFenceRecipe(NewModBlocks.FUNGYSS_FENCE.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addFenceRecipe(NewModBlocks.CHERRYWOOD_FENCE.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addFenceRecipe(NewModBlocks.MYSTERYWOOD_FENCE.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addFenceRecipe(NewModBlocks.EDELWOOD_FENCE.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addFenceGateRecipe(NewModBlocks.FUNGYSS_FENCE_GATE.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addFenceGateRecipe(NewModBlocks.CHERRYWOOD_FENCE_GATE.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addFenceGateRecipe(NewModBlocks.MYSTERYWOOD_FENCE_GATE.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addFenceGateRecipe(NewModBlocks.EDELWOOD_FENCE_GATE.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addSignRecipe(NewModBlocks.FUNGYSS_SIGN.getFirst().get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addSignRecipe(NewModBlocks.CHERRYWOOD_SIGN.getFirst().get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addSignRecipe(NewModBlocks.MYSTERYWOOD_SIGN.getFirst().get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addSignRecipe(NewModBlocks.EDELWOOD_SIGN.getFirst().get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addPressurePlateRecipe(NewModBlocks.ARCANE_GOLD_PRESSURE_PLATE.get(), ModItems.ARCANE_GOLD_INGOT.get(), consumer);
        this.addPressurePlateRecipe(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(NewModBlocks.CHERRYWOOD_PRESSURE_PLATE.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(NewModBlocks.MYSTERYWOOD_PRESSURE_PLATE.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(NewModBlocks.EDELWOOD_PRESSURE_PLATE.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        //Shapeless Recipes
        ShapelessRecipeBuilder.shapeless(NewModItems.PURIFYING_SOAP.get()).requires(ModItems.ARCANE_CRYSTAL_DUST.get()).requires(ModItems.WAX.get()).requires(Items.SLIME_BALL).requires(Items.PRISMARINE_CRYSTALS).requires(ItemTags.SMALL_FLOWERS).unlockedBy("has_item", has(ModItems.WAX.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(NewModItems.TEST_TUBE.get()).requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE.get()).unlockedBy("has_item", has(ModItems.RUNE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.DARK_RUNE.get(), 2).requires(ModItems.RUNE.get()).requires(ModItems.RUNE.get()).requires(ModItems.CORRUPTI_DUST.get()).unlockedBy("has_rune", has(ModItems.RUNE.get())).unlockedBy("has_dust", has(ModItems.CORRUPTI_DUST.get())).save(consumer);

        this.addPlanksRecipe(NewModBlocks.FUNGYSS_PLANKS.get(), ModTags.Items.FUNGYSS_STEMS, 4, consumer);
        this.addPlanksRecipe(NewModBlocks.CHERRYWOOD_PLANKS.get(), ModTags.Items.CHERRYWOOD_LOGS, 4, consumer);
        this.addPlanksRecipe(NewModBlocks.MYSTERYWOOD_PLANKS.get(), ModTags.Items.MYSTERYWOOD_LOGS, 4, consumer);
        this.addPlanksRecipe(NewModBlocks.EDELWOOD_PLANKS.get(), ModTags.Items.EDELWOOD_LOGS, 2, consumer);

        this.addButtonRecipe(NewModBlocks.FUNGYSS_BUTTON.get(), NewModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addButtonRecipe(NewModBlocks.CHERRYWOOD_BUTTON.get(), NewModBlocks.CHERRYWOOD_PLANKS.get(), consumer);
        this.addButtonRecipe(NewModBlocks.MYSTERYWOOD_BUTTON.get(), NewModBlocks.MYSTERYWOOD_PLANKS.get(), consumer);
        this.addButtonRecipe(NewModBlocks.EDELWOOD_BUTTON.get(), NewModBlocks.EDELWOOD_PLANKS.get(), consumer);

        //Smelting Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer, "forbidden_arcanus:smelting/arcane_crystal_dust_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.RUNIC_STONES), ModItems.RUNE.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)).save(consumer, "forbidden_arcanus:smelting/rune_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), ModItems.ARCANE_CRYSTAL.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)).save(consumer, "forbidden_arcanus:smelting/arcane_crystal_from_smelting");

        //Blasting Recipes
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer,  "forbidden_arcanus:blasting/arcane_crystal_dust_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.RUNIC_STONES), ModItems.RUNE.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)).save(consumer,  "forbidden_arcanus:blasting/rune_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), ModItems.ARCANE_CRYSTAL.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)).save(consumer,  "forbidden_arcanus:blasting/arcane_crystal_from_blasting");

        //Smithing Recipes
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.SHIELD), Ingredient.of(NewModItems.OBSIDIAN_SKULL.get()), NewModItems.OBSIDIAN_SKULL_SHIELD.get()).unlocks("has_item", has(NewModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_skull_shield"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(NewModItems.OBSIDIAN_SKULL.get()), Ingredient.of(ModItems.ETERNAL_STELLA.get()), NewModItems.ETERNAL_OBSIDIAN_SKULL.get()).unlocks("has_item", has(NewModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(NewModItems.NETHERITE_BLACKSMITH_GAVEL.get()), Ingredient.of(Items.NETHERITE_INGOT), NewModItems.DIAMOND_BLACKSMITH_GAVEL.get()).unlocks("has_item", has(Items.NETHERITE_INGOT)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_blacksmith_gavel"));

        //TODO add recipe provider for apply modifier recipes
    }

    private void addBlacksmithGavelRecipe(Item gavel, Tag.Named<Item> material, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(gavel).pattern("#X#").pattern("#S#").pattern(" S ").define('#', material).define('X', NewModItems.BLACKSMITH_GAVEL_HEAD.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(material)).save(consumer);
    }

    private void addStorageRecipe(ItemLike item, ItemLike block, @Nonnull Consumer<FinishedRecipe> consumer) {
        addStorageRecipe(item, block, null, null, consumer);
    }

    private void addStorageRecipe(ItemLike item, ItemLike block, @Nullable String groupItem, @Nullable String groupBlock, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(block).group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy("has_item", has(item)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, getName(block) + "_from_" + getName(item)));
        ShapelessRecipeBuilder.shapeless(item, 9).group(groupItem).requires(block).unlockedBy("has_item", has(block)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, getName(item) + "_from_" + getName(block)));
    }

    private void addGlassPaneRecipe(ItemLike glass, ItemLike glassPane, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(glassPane, 16).pattern("###").pattern("###").define('#', glass).unlockedBy("has_item", has(glass)).save(consumer);
    }

    private void addSurroundingItemRecipe(ItemLike result, ItemLike middle, ItemLike outside, int amount, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, amount).pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy(getHasName(middle), has(middle)).unlockedBy(getHasName(outside), has(outside)).save(consumer);
    }

    private void addSurroundingItemRecipe(ItemLike result, Tag<Item> middle, ItemLike outside, int amount, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, amount).pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy("has_" + middle, has(middle)).unlockedBy(getHasName(outside), has(outside)).save(consumer);
    }

    private void addWoodRecipe(ItemLike result, ItemLike log, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).pattern("##").pattern("##").define('#', log).unlockedBy("has_log", has(log)).save(consumer);
    }

    private void addPlanksRecipe(ItemLike result, Tag<Item> log, int count, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(result, count).requires(log).unlockedBy("has_log", has(log)).save(consumer);
    }

    private void addSlabRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 6).pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addStairsRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 4).pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);

    }

    private void addSignRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addDoorRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).pattern("##").pattern("##").pattern("##").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addTrapdoorRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 2).pattern("###").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addFenceRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).pattern("#X#").pattern("#X#").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addFenceGateRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("#X#").pattern("#X#").define('X', planks).define('#', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addButtonRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(result).requires(planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addPressurePlateRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("##").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private String getName(ItemLike item) {
        return Objects.requireNonNull(item.asItem().getRegistryName()).getPath();
    }

    private String getHasName(ItemLike item) {
        return "has_" + getName(item);
    }
}
