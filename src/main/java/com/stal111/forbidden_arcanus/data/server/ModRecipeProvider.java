package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
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
 * @version 1.18.2 - 2.0.0
 * @since 2021-01-28
 */
public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        //Shaped Recipes
        ShapedRecipeBuilder.shaped(ModItems.SANITY_METER.get()).pattern("AXA").pattern("X#X").pattern("AXA").define('X', Tags.Items.INGOTS_GOLD).define('#', Tags.Items.ENDER_PEARLS).define('A', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(Tags.Items.INGOTS_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.LENS_OF_VERITATIS.get()).pattern(" # ").pattern("#X#").pattern("S# ").define('#', ModItems.SPAWNER_SCRAP.get()).define('X', ModItems.ARCANE_CRYSTAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.OBSIDIAN_SKULL.get()).pattern("#O#").pattern("OXO").pattern("#O#").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.SKELETON_SKULL).define('O', Tags.Items.OBSIDIAN).unlockedBy("has_item", has(Tags.Items.OBSIDIAN)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.UTREM_JAR.get()).pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_COLORLESS).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_item", has(Tags.Items.GLASS_COLORLESS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_CRYSTAL_DUST.get()).pattern("###").pattern("###").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SOUL_EXTRACTOR.get()).pattern("U  ").pattern("##X").pattern("Q  ").define('U', ModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy("has_item", has(ModBlocks.UTREM_JAR.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_BONE_MEAL.get(), 4).pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.AUREAL_BOTTLE.get()).pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST.get()).define('X', new ValhelsiaNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        this.addSurroundingItemRecipe(ModBlocks.ARCANE_CHISELED_DARKSTONE.get(), ModTags.Items.ARCANE_GOLD_INGOTS, ModBlocks.DARKSTONE.get(), 8, consumer);

        ShapedRecipeBuilder.shaped(ModItems.BLACKSMITH_GAVEL_HEAD.get()).pattern("###").pattern("# #").pattern(" # ").define('#', Items.CLAY_BALL).unlockedBy("has_item", has(Items.CLAY_BALL)).save(consumer);
        this.addBlacksmithGavelRecipe(ModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS, consumer);
        this.addBlacksmithGavelRecipe(ModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS, consumer);
        this.addBlacksmithGavelRecipe(ModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD, consumer);
        this.addBlacksmithGavelRecipe(ModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON, consumer);
        this.addBlacksmithGavelRecipe(ModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND, consumer);
        this.addBlacksmithGavelRecipe(ModItems.DEORUM_BLACKSMITH_GAVEL.get(), ModTags.Items.ARCANE_GOLD_INGOTS, consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.DARKSTONE_PEDESTAL.get()).pattern("###").pattern(" * ").pattern("XXX").define('#', ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get()).define('*', ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get()).define('X', ModBlocks.POLISHED_DARKSTONE.get()).unlockedBy("has_item", has(ModBlocks.POLISHED_DARKSTONE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.DARK_NETHER_STAR.get()).pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.NETHER_STAR).unlockedBy("has_obsidian_ingot", has(ModItems.OBSIDIAN_INGOT.get())).unlockedBy("has_nether_star", has(Items.NETHER_STAR)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.DEORUM_INGOT.get()).pattern("#*#").pattern("MXM").pattern("#*#").define('#', Items.CHARCOAL).define('X', Items.GOLD_INGOT).define('M', ModItems.MUNDABITUR_DUST.get()).define('*', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).unlockedBy("has_arcane_crystal_dust", has(ModItems.ARCANE_CRYSTAL_DUST.get())).unlockedBy("has_mundabitur_dust", has(ModItems.MUNDABITUR_DUST.get())).save(consumer);

        this.addStorageRecipe(ModItems.DARK_NETHER_STAR.get(), ModBlocks.DARK_NETHER_STAR_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.OBSIDIAN_INGOT.get(), ModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), "obsidian_ingot", null, consumer);
        this.addStorageRecipe(ModItems.DEORUM_INGOT.get(), ModBlocks.DEORUM_BLOCK.get(), "arcane_gold_ingot", null, consumer);
        this.addStorageRecipe(ModItems.DEORUM_NUGGET.get(), ModItems.DEORUM_INGOT.get(), null, "arcane_gold_ingot", consumer);
        this.addStorageRecipe(ModItems.STELLARITE_PIECE.get(), ModBlocks.STELLARITE_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.ARCANE_CRYSTAL.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.RUNE.get(), ModBlocks.RUNE_BLOCK.get(), consumer);
        this.addStorageRecipe(ModItems.DARK_RUNE.get(), ModBlocks.DARK_RUNE_BLOCK.get(), consumer);

        this.addSurroundingItemRecipe(ModBlocks.DEORUM_GLASS.get(), ModItems.DEORUM_INGOT.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(ModBlocks.DEORUM_GLASS.get(), ModBlocks.DEORUM_GLASS_PANE.get(), consumer);
        this.addSurroundingItemRecipe(ModBlocks.RUNIC_GLASS.get(), ModItems.RUNE.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(ModBlocks.RUNIC_GLASS.get(), ModBlocks.RUNIC_GLASS_PANE.get(), consumer);
        this.addSurroundingItemRecipe(ModBlocks.DARK_RUNIC_GLASS.get(), ModItems.DARK_RUNE.get(), Blocks.GLASS, 8, consumer);
        this.addGlassPaneRecipe(ModBlocks.DARK_RUNIC_GLASS.get(), ModBlocks.DARK_RUNIC_GLASS_PANE.get(), consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DEORUM_CHAIN.get()).pattern("#").pattern("X").pattern("#").define('#', ModTags.Items.ARCANE_GOLD_NUGGETS).define('X', ModTags.Items.ARCANE_GOLD_INGOTS).unlockedBy("has_ingot", has(ModTags.Items.ARCANE_GOLD_INGOTS)).unlockedBy("has_nugget", has(ModTags.Items.ARCANE_GOLD_NUGGETS)).save(consumer);

        this.addWoodRecipe(ModBlocks.FUNGYSS_HYPHAE.get(), ModBlocks.FUNGYSS_STEM.get(), consumer);
        this.addWoodRecipe(ModBlocks.CHERRY_WOOD.get(), ModBlocks.CHERRY_LOG.get(), consumer);
        this.addWoodRecipe(ModBlocks.AURUM_WOOD.get(), ModBlocks.AURUM_LOG.get(), consumer);

        this.addWoodRecipe(ModBlocks.STRIPPED_CHERRY_WOOD.get(), ModBlocks.STRIPPED_CHERRY_LOG.get(), consumer);
        this.addWoodRecipe(ModBlocks.STRIPPED_AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_LOG.get(), consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CARVED_CHERRY_PLANKS.get(), 4).pattern(" # ").pattern("# #").pattern(" # ").define('#', ModBlocks.CHERRY_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.CHERRY_PLANKS.get())).save(consumer);
        this.addSurroundingItemRecipe(ModBlocks.ARCANE_EDELWOOD_PLANKS.get(), ModItems.DEORUM_INGOT.get(), ModBlocks.EDELWOOD_PLANKS.get(), 8, consumer);

        this.addWoodenSlabRecipe(ModBlocks.FUNGYSS_SLAB.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addWoodenSlabRecipe(ModBlocks.CHERRY_SLAB.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addWoodenSlabRecipe(ModBlocks.AURUM_SLAB.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addWoodenSlabRecipe(ModBlocks.EDELWOOD_SLAB.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addWoodenStairsRecipe(ModBlocks.FUNGYSS_STAIRS.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addWoodenStairsRecipe(ModBlocks.CHERRY_STAIRS.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addWoodenStairsRecipe(ModBlocks.AURUM_STAIRS.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addWoodenStairsRecipe(ModBlocks.EDELWOOD_STAIRS.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addDoorRecipe(ModBlocks.DEORUM_DOOR.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.addDoorRecipe(ModBlocks.FUNGYSS_DOOR.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addDoorRecipe(ModBlocks.CHERRY_DOOR.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addDoorRecipe(ModBlocks.AURUM_DOOR.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addDoorRecipe(ModBlocks.EDELWOOD_DOOR.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);
        this.addDoorRecipe(ModBlocks.ARCANE_EDELWOOD_DOOR.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get(), consumer);

        this.addTrapdoorRecipe(ModBlocks.DEORUM_TRAPDOOR.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.addTrapdoorRecipe(ModBlocks.FUNGYSS_TRAPDOOR.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(ModBlocks.CHERRY_TRAPDOOR.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(ModBlocks.AURUM_TRAPDOOR.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(ModBlocks.EDELWOOD_TRAPDOOR.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);
        this.addTrapdoorRecipe(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get(), consumer);

        this.addFenceRecipe(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addFenceRecipe(ModBlocks.CHERRY_FENCE.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addFenceRecipe(ModBlocks.AURUM_FENCE.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addFenceRecipe(ModBlocks.EDELWOOD_FENCE.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addFenceGateRecipe(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addFenceGateRecipe(ModBlocks.CHERRY_FENCE_GATE.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addFenceGateRecipe(ModBlocks.AURUM_FENCE_GATE.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addFenceGateRecipe(ModBlocks.EDELWOOD_FENCE_GATE.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addSignRecipe(ModBlocks.FUNGYSS_SIGN.getFirst().get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addSignRecipe(ModBlocks.CHERRY_SIGN.getFirst().get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addSignRecipe(ModBlocks.AURUM_SIGN.getFirst().get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addSignRecipe(ModBlocks.EDELWOOD_SIGN.getFirst().get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addPressurePlateRecipe(ModBlocks.DEORUM_PRESSURE_PLATE.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.addPressurePlateRecipe(ModBlocks.FUNGYSS_PRESSURE_PLATE.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(ModBlocks.CHERRY_PRESSURE_PLATE.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(ModBlocks.AURUM_PRESSURE_PLATE.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addPressurePlateRecipe(ModBlocks.EDELWOOD_PRESSURE_PLATE.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.THIN_CHERRY_LOG.get(), 4).pattern("#").pattern("#").define('#', ModBlocks.CHERRY_LOG.get()).unlockedBy("has_log", has(ModBlocks.CHERRY_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CHERRY_LOG.get()).pattern("##").define('#', ModBlocks.THIN_CHERRY_LOG.get()).unlockedBy("has_log", has(ModBlocks.THIN_CHERRY_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.EDELWOOD_LADDER.get(), 3).pattern("# #").pattern("#X#").pattern("# #").define('#', Tags.Items.RODS_WOODEN).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN)).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.EDELWOOD_BUCKET.get()).pattern("# #").pattern("# #").pattern(" # ").define('#', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())).save(consumer);

        this.addSurroundingItemRecipe(ModBlocks.CLIBANO_CORE.get(), Blocks.BLAST_FURNACE, ModBlocks.DARKSTONE.get(), 1, consumer);

        this.sword(ModItems.DEORUM_SWORD.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.shovel(ModItems.DEORUM_SHOVEL.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.pickaxe(ModItems.DEORUM_PICKAXE.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.axe(ModItems.DEORUM_AXE.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.hoe(ModItems.DEORUM_HOE.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.helmet(ModItems.DEORUM_HELMET.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.chestplate(ModItems.DEORUM_CHESTPLATE.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.leggings(ModItems.DEORUM_LEGGINGS.get(), ModItems.DEORUM_INGOT.get(), consumer);
        this.boots(ModItems.DEORUM_BOOTS.get(), ModItems.DEORUM_INGOT.get(), consumer);

        //Shapeless Recipes
        ShapelessRecipeBuilder.shapeless(ModItems.PURIFYING_SOAP.get()).requires(ModItems.ARCANE_CRYSTAL_DUST.get()).requires(ModItems.WAX.get()).requires(Items.SLIME_BALL).requires(Items.PRISMARINE_CRYSTALS).requires(ItemTags.SMALL_FLOWERS).unlockedBy("has_item", has(ModItems.WAX.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.TEST_TUBE.get()).requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE.get()).unlockedBy("has_item", has(ModItems.RUNE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.DARK_RUNE.get(), 2).requires(ModItems.RUNE.get()).requires(ModItems.RUNE.get()).requires(ModItems.CORRUPTI_DUST.get()).unlockedBy("has_rune", has(ModItems.RUNE.get())).unlockedBy("has_dust", has(ModItems.CORRUPTI_DUST.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.GOLDEN_ORCHID_SEEDS.get()).requires(ModBlocks.YELLOW_ORCHID.get()).requires(ModItems.DEORUM_INGOT.get()).unlockedBy("has_yellow_orchid", has(ModBlocks.YELLOW_ORCHID.get())).unlockedBy("has_deorum_ingot", has(ModItems.DEORUM_INGOT.get())).save(consumer);

        this.addPlanksRecipe(ModBlocks.FUNGYSS_PLANKS.get(), ModTags.Items.FUNGYSS_STEMS, 4, consumer);
        this.addPlanksRecipe(ModBlocks.CHERRY_PLANKS.get(), ModTags.Items.CHERRYWOOD_LOGS, 4, consumer);
        this.addPlanksRecipe(ModBlocks.AURUM_PLANKS.get(), ModTags.Items.MYSTERYWOOD_LOGS, 4, consumer);
        this.addPlanksRecipe(ModBlocks.EDELWOOD_PLANKS.get(), ModTags.Items.EDELWOOD_LOGS, 2, consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.CHERRY_PLANKS.get(), 2).requires(ModBlocks.THIN_CHERRY_LOG.get()).unlockedBy("has_item", has(ModBlocks.THIN_CHERRY_LOG.get())).save(consumer, "forbidden_arcanus:cherrywood_planks_from_thin_cherrywood_log");

        this.addButtonRecipe(ModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.FUNGYSS_PLANKS.get(), consumer);
        this.addButtonRecipe(ModBlocks.CHERRY_BUTTON.get(), ModBlocks.CHERRY_PLANKS.get(), consumer);
        this.addButtonRecipe(ModBlocks.AURUM_BUTTON.get(), ModBlocks.AURUM_PLANKS.get(), consumer);
        this.addButtonRecipe(ModBlocks.EDELWOOD_BUTTON.get(), ModBlocks.EDELWOOD_PLANKS.get(), consumer);

        this.addCarpetRecipe(ModBlocks.CHERRY_LEAF_CARPET.get(), ModBlocks.CHERRY_LEAVES.get(), consumer);

        //Smelting Recipes
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer, "forbidden_arcanus:smelting/arcane_crystal_dust_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.RUNIC_STONES), ModItems.RUNE.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)).save(consumer, "forbidden_arcanus:smelting/rune_from_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), ModItems.ARCANE_CRYSTAL.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)).save(consumer, "forbidden_arcanus:smelting/arcane_crystal_from_smelting");

        //Blasting Recipes
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer,  "forbidden_arcanus:blasting/arcane_crystal_dust_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.RUNIC_STONES), ModItems.RUNE.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)).save(consumer,  "forbidden_arcanus:blasting/rune_from_blasting");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), ModItems.ARCANE_CRYSTAL.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)).save(consumer,  "forbidden_arcanus:blasting/arcane_crystal_from_blasting");

        //Smithing Recipes
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.SHIELD), Ingredient.of(ModItems.OBSIDIAN_SKULL.get()), ModItems.OBSIDIAN_SKULL_SHIELD.get()).unlocks("has_item", has(ModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_skull_shield"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.OBSIDIAN_SKULL.get()), Ingredient.of(ModItems.ETERNAL_STELLA.get()), ModItems.ETERNAL_OBSIDIAN_SKULL.get()).unlocks("has_item", has(ModItems.OBSIDIAN_SKULL.get())).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.DIAMOND_BLACKSMITH_GAVEL.get()), Ingredient.of(Items.NETHERITE_INGOT), ModItems.NETHERITE_BLACKSMITH_GAVEL.get()).unlocks("has_item", has(Items.NETHERITE_INGOT)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_blacksmith_gavel"));

        // Stonecutting Recipes
        this.addStonecutterRecipe(consumer, ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.DARKSTONE.get());
        this.addStonecutterRecipe(consumer, ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE.get());
        this.addStonecutterRecipe(consumer, ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get());

        //TODO add recipe provider for apply modifier recipes
    }

    private void addBlacksmithGavelRecipe(Item gavel, TagKey<Item> material, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(gavel).pattern("#X#").pattern("#S#").pattern(" S ").define('#', material).define('X', ModItems.BLACKSMITH_GAVEL_HEAD.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(material)).save(consumer);
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

    private void addSurroundingItemRecipe(ItemLike result, TagKey<Item> middle, ItemLike outside, int amount, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, amount).pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy("has_" + middle, has(middle)).unlockedBy(getHasName(outside), has(outside)).save(consumer);
    }

    private void addWoodRecipe(ItemLike result, ItemLike log, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).group("bark").pattern("##").pattern("##").define('#', log).unlockedBy("has_log", has(log)).save(consumer);
    }

    private void addPlanksRecipe(ItemLike result, TagKey<Item> log, int count, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(result, count).requires(log).unlockedBy("has_log", has(log)).save(consumer);
    }

    private void addSlabRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 6).pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addWoodenSlabRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 6).group("wooden_slab").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addStairsRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 4).pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
    }

    private void addWoodenStairsRecipe(ItemLike result, ItemLike planks, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 4).group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)).save(consumer);
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

    private void addCarpetRecipe(ItemLike result, ItemLike material, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result, 3).pattern("##").define('#', material).unlockedBy("has_material", has(material)).save(consumer);
    }

    private void addStonecutterRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material) {
        this.addStonecutterRecipe(consumer, result, material, 1);
    }

    private void addStonecutterRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), result, count).unlockedBy("has_" + this.getName(material), has(material)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID,  this.getName(result) + "_from_" + this.getName(material) + "_stonecutting"));
    }

    public void sword(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("#").pattern("#").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void shovel(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("#").pattern("X").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void pickaxe(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("###").pattern(" X ").pattern(" X ").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void axe(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("##").pattern("#X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void hoe(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("##").pattern(" X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void helmet(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("###").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void chestplate(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void leggings(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("###").pattern("# #").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    public void boots(ItemLike result, ItemLike material, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(result).pattern("# #").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)).save(consumer);
    }

    private String getName(ItemLike item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.asItem())).getPath();
    }
}
