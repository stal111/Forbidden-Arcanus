package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
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
import net.valhelsia.valhelsia_core.common.util.ValhelsiaNBTIngredient;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.data.recipes.RecipePart;
import net.valhelsia.valhelsia_core.data.recipes.ValhelsiaRecipeProvider;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 2021-01-28
 */
public final class ModRecipeProvider extends ValhelsiaRecipeProvider {

    public ModRecipeProvider(DataProviderInfo info) {
        super(info);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        //Shaped Recipes
        ShapedRecipeBuilder.shaped(ModItems.SANITY_METER.get()).pattern("AXA").pattern("X#X").pattern("AXA").define('X', Tags.Items.INGOTS_GOLD).define('#', Tags.Items.ENDER_PEARLS).define('A', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(Tags.Items.INGOTS_GOLD)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.LENS_OF_VERITATIS.get()).pattern(" # ").pattern("#X#").pattern("S# ").define('#', ModItems.SPAWNER_SCRAP.get()).define('X', ModItems.ARCANE_CRYSTAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.OBSIDIAN_SKULL.get()).pattern("#O#").pattern("OXO").pattern("#O#").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.SKELETON_SKULL).define('O', Tags.Items.OBSIDIAN).unlockedBy("has_item", has(Tags.Items.OBSIDIAN)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.UTREM_JAR.get()).pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_COLORLESS).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_item", has(Tags.Items.GLASS_COLORLESS)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_CRYSTAL_DUST.get()).pattern("###").pattern("###").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SOUL_EXTRACTOR.get()).pattern("U  ").pattern("##X").pattern("Q  ").define('U', ModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy("has_item", has(ModBlocks.UTREM_JAR.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.ARCANE_BONE_MEAL.get(), 4).pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.AUREAL_BOTTLE.get()).pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST.get()).define('X', new ValhelsiaNBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL_DUST.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BLACKSMITH_GAVEL_HEAD.get()).pattern("###").pattern("# #").pattern(" # ").define('#', Items.CLAY_BALL).unlockedBy("has_item", has(Items.CLAY_BALL)).save(consumer);
        this.addBlacksmithGavelRecipe(ModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS, consumer);
        this.addBlacksmithGavelRecipe(ModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS, consumer);
        this.addBlacksmithGavelRecipe(ModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD, consumer);
        this.addBlacksmithGavelRecipe(ModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON, consumer);
        this.addBlacksmithGavelRecipe(ModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND, consumer);
        this.addBlacksmithGavelRecipe(ModItems.DEORUM_BLACKSMITH_GAVEL.get(), ModTags.Items.DEORUM_INGOTS, consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.DARKSTONE_PEDESTAL.get()).pattern("###").pattern(" * ").pattern("XXX").define('#', ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get()).define('*', ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get()).define('X', ModBlocks.POLISHED_DARKSTONE.get()).unlockedBy("has_item", has(ModBlocks.POLISHED_DARKSTONE.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.DARK_NETHER_STAR.get()).pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.OBSIDIAN_INGOT.get()).define('X', Items.NETHER_STAR).unlockedBy("has_obsidian_ingot", has(ModItems.OBSIDIAN_INGOT.get())).unlockedBy("has_nether_star", has(Items.NETHER_STAR)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.DEORUM_INGOT.get()).pattern("#*#").pattern("MXM").pattern("#*#").define('#', Items.CHARCOAL).define('X', Items.GOLD_INGOT).define('M', ModItems.MUNDABITUR_DUST.get()).define('*', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT)).unlockedBy("has_arcane_crystal_dust", has(ModItems.ARCANE_CRYSTAL_DUST.get())).unlockedBy("has_mundabitur_dust", has(ModItems.MUNDABITUR_DUST.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DEORUM_CHAIN.get()).pattern("#").pattern("X").pattern("#").define('#', ModTags.Items.DEORUM_NUGGETS).define('X', ModTags.Items.DEORUM_INGOTS).unlockedBy("has_ingot", has(ModTags.Items.DEORUM_INGOTS)).unlockedBy("has_nugget", has(ModTags.Items.DEORUM_NUGGETS)).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CARVED_CHERRY_PLANKS.get(), 4).pattern(" # ").pattern("# #").pattern(" # ").define('#', ModBlocks.CHERRY_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.CHERRY_PLANKS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.THIN_CHERRY_LOG.get(), 4).pattern("#").pattern("#").define('#', ModBlocks.CHERRY_LOG.get()).unlockedBy("has_log", has(ModBlocks.CHERRY_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CHERRY_LOG.get()).pattern("##").define('#', ModBlocks.THIN_CHERRY_LOG.get()).unlockedBy("has_log", has(ModBlocks.THIN_CHERRY_LOG.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.EDELWOOD_LADDER.get(), 3).pattern("# #").pattern("#X#").pattern("# #").define('#', Tags.Items.RODS_WOODEN).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN)).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.EDELWOOD_BUCKET.get()).pattern("# #").pattern("# #").pattern(" # ").define('#', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())).save(consumer);

        //Shapeless Recipes
        ShapelessRecipeBuilder.shapeless(ModItems.PURIFYING_SOAP.get()).requires(ModItems.ARCANE_CRYSTAL_DUST.get()).requires(ModItems.WAX.get()).requires(Items.SLIME_BALL).requires(Items.PRISMARINE_CRYSTALS).requires(ItemTags.SMALL_FLOWERS).unlockedBy("has_item", has(ModItems.WAX.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.TEST_TUBE.get()).requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE.get()).unlockedBy("has_item", has(ModItems.RUNE.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.DARK_RUNE.get(), 2).requires(ModItems.RUNE.get()).requires(ModItems.RUNE.get()).requires(ModItems.CORRUPTI_DUST.get()).unlockedBy("has_rune", has(ModItems.RUNE.get())).unlockedBy("has_dust", has(ModItems.CORRUPTI_DUST.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.GOLDEN_ORCHID_SEEDS.get()).requires(ModBlocks.YELLOW_ORCHID.get()).requires(ModItems.DEORUM_INGOT.get()).unlockedBy("has_yellow_orchid", has(ModBlocks.YELLOW_ORCHID.get())).unlockedBy("has_deorum_ingot", has(ModItems.DEORUM_INGOT.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.CHERRY_PLANKS.get(), 2).requires(ModBlocks.THIN_CHERRY_LOG.get()).unlockedBy("has_item", has(ModBlocks.THIN_CHERRY_LOG.get())).save(consumer, "forbidden_arcanus:cherrywood_planks_from_thin_cherrywood_log");

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
    }

    @Override
    public void registerRecipes() {
        this.surroundingItem(ModBlocks.ARCANE_CHISELED_DARKSTONE.get(), RecipePart.of(ModTags.Items.DEORUM_INGOTS), RecipePart.of(ModBlocks.DARKSTONE.get()), 8);

        this.storageRecipe(ModItems.DARK_NETHER_STAR.get(), ModBlocks.DARK_NETHER_STAR_BLOCK.get());
        this.storageRecipe(ModItems.OBSIDIAN_INGOT.get(), ModBlocks.PROCESSED_OBSIDIAN_BLOCK.get(), "obsidian_ingot", null);
        this.storageRecipe(ModItems.DEORUM_INGOT.get(), ModBlocks.DEORUM_BLOCK.get(), "arcane_gold_ingot", null);
        this.storageRecipe(ModItems.DEORUM_NUGGET.get(), ModItems.DEORUM_INGOT.get(), null, "arcane_gold_ingot");
        this.storageRecipe(ModItems.STELLARITE_PIECE.get(), ModBlocks.STELLARITE_BLOCK.get());
        this.storageRecipe(ModItems.ARCANE_CRYSTAL.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
        this.storageRecipe(ModItems.RUNE.get(), ModBlocks.RUNE_BLOCK.get());
        this.storageRecipe(ModItems.DARK_RUNE.get(), ModBlocks.DARK_RUNE_BLOCK.get());

        this.surroundingItem(ModBlocks.DEORUM_GLASS.get(), RecipePart.of(ModItems.DEORUM_INGOT.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.DEORUM_GLASS_PANE.get(), ModBlocks.DEORUM_GLASS.get());
        this.surroundingItem(ModBlocks.RUNIC_GLASS.get(), RecipePart.of(ModItems.RUNE.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.RUNIC_GLASS_PANE.get(), ModBlocks.RUNIC_GLASS.get());
        this.surroundingItem(ModBlocks.DARK_RUNIC_GLASS.get(), RecipePart.of(ModItems.DARK_RUNE.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.DARK_RUNIC_GLASS_PANE.get(), ModBlocks.DARK_RUNIC_GLASS.get());

        this.wood(ModBlocks.FUNGYSS_HYPHAE.get(), ModBlocks.FUNGYSS_STEM.get());
        this.wood(ModBlocks.CHERRY_WOOD.get(), ModBlocks.CHERRY_LOG.get());
        this.wood(ModBlocks.AURUM_WOOD.get(), ModBlocks.AURUM_LOG.get());

        this.wood(ModBlocks.STRIPPED_CHERRY_WOOD.get(), ModBlocks.STRIPPED_CHERRY_LOG.get());
        this.wood(ModBlocks.STRIPPED_AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_LOG.get());

        this.planks(ModBlocks.FUNGYSS_PLANKS.get(), ModTags.Items.FUNGYSS_STEMS, 4);
        this.planks(ModBlocks.CHERRY_PLANKS.get(), ModTags.Items.CHERRYWOOD_LOGS, 4);
        this.planks(ModBlocks.AURUM_PLANKS.get(), ModTags.Items.MYSTERYWOOD_LOGS, 4);
        this.planks(ModBlocks.EDELWOOD_PLANKS.get(), ModTags.Items.EDELWOOD_LOGS, 2);

        this.surroundingItem(ModBlocks.ARCANE_EDELWOOD_PLANKS.get(), RecipePart.of(ModItems.DEORUM_INGOT.get()), RecipePart.of(ModBlocks.EDELWOOD_PLANKS.get()), 8);

        this.woodenSlab(ModBlocks.FUNGYSS_SLAB.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.woodenSlab(ModBlocks.CHERRY_SLAB.get(), ModBlocks.CHERRY_PLANKS.get());
        this.woodenSlab(ModBlocks.AURUM_SLAB.get(), ModBlocks.AURUM_PLANKS.get());
        this.woodenSlab(ModBlocks.EDELWOOD_SLAB.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.woodenStairs(ModBlocks.FUNGYSS_STAIRS.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.woodenStairs(ModBlocks.CHERRY_STAIRS.get(), ModBlocks.CHERRY_PLANKS.get());
        this.woodenStairs(ModBlocks.AURUM_STAIRS.get(), ModBlocks.AURUM_PLANKS.get());
        this.woodenStairs(ModBlocks.EDELWOOD_STAIRS.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.door(ModBlocks.DEORUM_DOOR.get(), ModItems.DEORUM_INGOT.get());
        this.door(ModBlocks.FUNGYSS_DOOR.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.door(ModBlocks.CHERRY_DOOR.get(), ModBlocks.CHERRY_PLANKS.get());
        this.door(ModBlocks.AURUM_DOOR.get(), ModBlocks.AURUM_PLANKS.get());
        this.door(ModBlocks.EDELWOOD_DOOR.get(), ModBlocks.EDELWOOD_PLANKS.get());
        this.door(ModBlocks.ARCANE_EDELWOOD_DOOR.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get());

        this.trapdoor(ModBlocks.DEORUM_TRAPDOOR.get(), ModItems.DEORUM_INGOT.get());
        this.trapdoor(ModBlocks.FUNGYSS_TRAPDOOR.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.trapdoor(ModBlocks.CHERRY_TRAPDOOR.get(), ModBlocks.CHERRY_PLANKS.get());
        this.trapdoor(ModBlocks.AURUM_TRAPDOOR.get(), ModBlocks.AURUM_PLANKS.get());
        this.trapdoor(ModBlocks.EDELWOOD_TRAPDOOR.get(), ModBlocks.EDELWOOD_PLANKS.get());
        this.trapdoor(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get(), ModBlocks.ARCANE_EDELWOOD_PLANKS.get());

        this.fence(ModBlocks.FUNGYSS_FENCE.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.fence(ModBlocks.CHERRY_FENCE.get(), ModBlocks.CHERRY_PLANKS.get());
        this.fence(ModBlocks.AURUM_FENCE.get(), ModBlocks.AURUM_PLANKS.get());
        this.fence(ModBlocks.EDELWOOD_FENCE.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.fenceGate(ModBlocks.FUNGYSS_FENCE_GATE.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.fenceGate(ModBlocks.CHERRY_FENCE_GATE.get(), ModBlocks.CHERRY_PLANKS.get());
        this.fenceGate(ModBlocks.AURUM_FENCE_GATE.get(), ModBlocks.AURUM_PLANKS.get());
        this.fenceGate(ModBlocks.EDELWOOD_FENCE_GATE.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.sign(ModBlocks.FUNGYSS_SIGN.getFirst().get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.sign(ModBlocks.CHERRY_SIGN.getFirst().get(), ModBlocks.CHERRY_PLANKS.get());
        this.sign(ModBlocks.AURUM_SIGN.getFirst().get(), ModBlocks.AURUM_PLANKS.get());
        this.sign(ModBlocks.EDELWOOD_SIGN.getFirst().get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.pressurePlate(ModBlocks.DEORUM_PRESSURE_PLATE.get(), ModItems.DEORUM_INGOT.get());
        this.pressurePlate(ModBlocks.FUNGYSS_PRESSURE_PLATE.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.pressurePlate(ModBlocks.CHERRY_PRESSURE_PLATE.get(), ModBlocks.CHERRY_PLANKS.get());
        this.pressurePlate(ModBlocks.AURUM_PRESSURE_PLATE.get(), ModBlocks.AURUM_PLANKS.get());
        this.pressurePlate(ModBlocks.EDELWOOD_PRESSURE_PLATE.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.button(ModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.button(ModBlocks.CHERRY_BUTTON.get(), ModBlocks.CHERRY_PLANKS.get());
        this.button(ModBlocks.AURUM_BUTTON.get(), ModBlocks.AURUM_PLANKS.get());
        this.button(ModBlocks.EDELWOOD_BUTTON.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.sword(ModItems.DEORUM_SWORD.get(), ModItems.DEORUM_INGOT.get());
        this.shovel(ModItems.DEORUM_SHOVEL.get(), ModItems.DEORUM_INGOT.get());
        this.pickaxe(ModItems.DEORUM_PICKAXE.get(), ModItems.DEORUM_INGOT.get());
        this.axe(ModItems.DEORUM_AXE.get(), ModItems.DEORUM_INGOT.get());
        this.hoe(ModItems.DEORUM_HOE.get(), ModItems.DEORUM_INGOT.get());
        this.helmet(ModItems.DEORUM_HELMET.get(), ModItems.DEORUM_INGOT.get());
        this.chestplate(ModItems.DEORUM_CHESTPLATE.get(), ModItems.DEORUM_INGOT.get());
        this.leggings(ModItems.DEORUM_LEGGINGS.get(), ModItems.DEORUM_INGOT.get());
        this.boots(ModItems.DEORUM_BOOTS.get(), ModItems.DEORUM_INGOT.get());

        this.boat(ModItems.CHERRY_BOAT.get(), ModBlocks.CHERRY_PLANKS.get());
        this.boat(ModItems.AURUM_BOAT.get(), ModBlocks.AURUM_PLANKS.get());
        this.boat(ModItems.EDELWOOD_BOAT.get(), ModBlocks.EDELWOOD_PLANKS.get());

        this.chestBoat(ModItems.CHERRY_CHEST_BOAT.get(), ModItems.CHERRY_BOAT.get());
        this.chestBoat(ModItems.AURUM_CHEST_BOAT.get(), ModItems.AURUM_BOAT.get());
        this.chestBoat(ModItems.EDELWOOD_CHEST_BOAT.get(), ModItems.EDELWOOD_BOAT.get());

        this.surroundingItem(ModBlocks.CLIBANO_CORE.get(), RecipePart.of(Blocks.BLAST_FURNACE), RecipePart.of(ModBlocks.DARKSTONE.get()), 1);
        this.carpet(ModBlocks.CHERRY_LEAF_CARPET.get(), ModBlocks.CHERRY_LEAVES.get());

        this.lantern(ModBlocks.DEORUM_LANTERN.get(), Blocks.TORCH, RecipePart.of(ModTags.Items.DEORUM_NUGGETS));
        this.lantern(ModBlocks.DEORUM_SOUL_LANTERN.get(), Blocks.SOUL_TORCH, RecipePart.of(ModTags.Items.DEORUM_NUGGETS));
    }

    private void lantern(ItemLike result, ItemLike torch, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("XXX").pattern("X#X").pattern("XXX").define('X', material).define('#', torch).unlockedBy(this, material).unlockedBy(this, torch));
    }

    private void addBlacksmithGavelRecipe(Item gavel, TagKey<Item> material, @Nonnull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(gavel).pattern("#X#").pattern("#S#").pattern(" S ").define('#', material).define('X', ModItems.BLACKSMITH_GAVEL_HEAD.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(material)).save(consumer);
    }

    private void addStonecutterRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material) {
        this.addStonecutterRecipe(consumer, result, material, 1);
    }

    private void addStonecutterRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike material, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), result, count).unlockedBy("has_" + this.getName(material), has(material)).save(consumer, new ResourceLocation(ForbiddenArcanus.MOD_ID,  this.getName(result) + "_from_" + this.getName(material) + "_stonecutting"));
    }
}
