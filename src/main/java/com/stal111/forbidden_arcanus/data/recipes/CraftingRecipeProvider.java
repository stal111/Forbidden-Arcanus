package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.data.FABlockFamilies;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.valhelsia.valhelsia_core.datagen.recipes.RecipePart;
import net.valhelsia.valhelsia_core.datagen.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.datagen.recipes.ValhelsiaRecipeProvider;

/**
 * @author Valhelsia Team
 * @since 2023-01-02
 */
public class CraftingRecipeProvider extends RecipeSubProvider {

    private final ValhelsiaRecipeProvider provider;

    public CraftingRecipeProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    protected void registerRecipes(HolderLookup.Provider lookupProvider) {
        FABlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> RecipeProvider.generateRecipes(this.provider.getRecipeOutput(), family, FeatureFlagSet.of(FeatureFlags.VANILLA)));

        //Shaped Recipes
        this.shaped(RecipeCategory.TOOLS, ModItems.SANITY_METER, builder -> builder.pattern("AXA").pattern("X#X").pattern("AXA").define('X', Tags.Items.INGOTS_GOLD).define('#', Tags.Items.ENDER_PEARLS).define('A', ModItems.ARCANE_CRYSTAL_DUST).unlockedBy(this, RecipePart.of(Tags.Items.INGOTS_GOLD)));
        this.shaped(RecipeCategory.TOOLS, ModItems.LENS_OF_VERITATIS, builder -> builder.pattern(" # ").pattern("#X#").pattern("S# ").define('#', ModItems.SPAWNER_SCRAP).define('X', ModItems.ARCANE_CRYSTAL).define('S', Tags.Items.RODS_WOODEN).unlockedBy(this, ModItems.ARCANE_CRYSTAL));
        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.UTREM_JAR.get(), builder -> builder.pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_BLOCKS_COLORLESS).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy(this, RecipePart.of(Tags.Items.GLASS_BLOCKS_COLORLESS)));
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST, builder -> builder.pattern("###").pattern("###").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST_SPECK).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST_SPECK));
        this.shaped(RecipeCategory.TOOLS, ModItems.SOUL_EXTRACTOR, builder -> builder.pattern("U  ").pattern("##X").pattern("Q  ").define('U', ModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy(this, ModBlocks.UTREM_JAR.get()));
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_BONE_MEAL, 4, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST));
        this.shaped(RecipeCategory.MISC, ModItems.AUREAL_BOTTLE, builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST).define('X', DataComponentIngredient.of(true, DataComponentPredicate.builder().expect(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_REGENERATION)).build(), Items.POTION)).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST));

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARKSTONE_PEDESTAL.get(), builder -> builder.pattern("###").pattern(" * ").pattern("XXX").define('#', ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get()).define('*', ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get()).define('X', ModBlocks.POLISHED_DARKSTONE.get()).unlockedBy(this, ModBlocks.POLISHED_DARKSTONE.get()));

        this.shaped(RecipeCategory.MISC, ModItems.DARK_NETHER_STAR, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.OBSIDIANSTEEL_INGOT).define('X', Items.NETHER_STAR).unlockedBy(this, ModItems.OBSIDIANSTEEL_INGOT).unlockedBy(this, Items.NETHER_STAR));
        this.shaped(RecipeCategory.MISC, ModItems.DEORUM_INGOT, builder -> builder.pattern("#*#").pattern("MXM").pattern("#*#").define('#', Items.CHARCOAL).define('X', Items.GOLD_INGOT).define('M', ModItems.MUNDABITUR_DUST).define('*', ModItems.ARCANE_CRYSTAL_DUST).unlockedBy(this, Items.GOLD_INGOT).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST).unlockedBy(this, ModItems.MUNDABITUR_DUST));

        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.DEORUM_CHAIN.get(), builder -> builder.pattern("#").pattern("X").pattern("#").define('#', ModTags.Items.DEORUM_NUGGETS).define('X', ModTags.Items.DEORUM_INGOTS).unlockedBy(this, RecipePart.of(ModTags.Items.DEORUM_INGOTS)).unlockedBy(this, RecipePart.of(ModTags.Items.DEORUM_NUGGETS)));

        for (DyeColor color : DyeColor.values()) {
            this.shaped(RecipeCategory.TOOLS, ModItems.DYED_QUANTUM_CATCHERS.get(color), builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('X', ModItems.QUANTUM_CATCHER).define('#', color.getTag()).unlockedBy(this, ModItems.QUANTUM_CATCHER).unlockedBy(this, RecipePart.of(color.getTag())));
        }

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EDELWOOD_LADDER.get(), 3, builder -> builder.pattern("# #").pattern("#X#").pattern("# #").define('#', Tags.Items.RODS_WOODEN).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy(this, RecipePart.of(Tags.Items.RODS_WOODEN)).unlockedBy(this, ModBlocks.EDELWOOD_PLANKS.get()));
        this.shaped(RecipeCategory.MISC, ModItems.EDELWOOD_BUCKET, builder -> builder.pattern("# #").pattern("# #").pattern(" # ").define('#', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())));
        this.shaped(RecipeCategory.MISC, ModItems.BOOM_ARROW, 4, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('X', Blocks.TNT).define('#', Items.ARROW).unlockedBy(this, Blocks.TNT).unlockedBy(this, Items.ARROW));
        this.surroundingItem(RecipeCategory.MISC, ModBlocks.ARCANE_POLISHED_DARKSTONE.get(), this.item(ModItems.DEORUM_INGOT), this.item(ModBlocks.POLISHED_DARKSTONE.get()), 8);
        this.surroundingItem(RecipeCategory.MISC, ModBlocks.OBSIDIAN_SKULL.getSkull(), this.item(Items.SKELETON_SKULL), this.item(ModItems.OBSIDIANSTEEL_INGOT), 1);
        this.surroundingItem(RecipeCategory.MISC, ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE, this.item(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), this.item(ModBlocks.DARKSTONE.get()), 1);
        this.shaped(RecipeCategory.MISC, ModBlocks.ARCANE_DRAGON_EGG.get(), builder -> builder.pattern(" # ").pattern("#X#").pattern("###").define('#', ModItems.GOLDEN_DRAGON_SCALE).define('X', Blocks.DRAGON_EGG).unlockedBy(this, Blocks.DRAGON_EGG).unlockedBy(this, ModItems.GOLDEN_DRAGON_SCALE));
        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(), builder -> builder.pattern("#").pattern("#").define('#', ModBlocks.ARCANE_POLISHED_DARKSTONE.get()).unlockedBy(this, ModBlocks.ARCANE_POLISHED_DARKSTONE.get()));
        this.shaped(RecipeCategory.MISC, ModItems.DRACO_ARCANUS_ARROW, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.ARROW).define('X', Items.DRAGON_BREATH).unlockedBy(this, Items.ARROW).unlockedBy(this, Items.DRAGON_BREATH));
        this.shaped(RecipeCategory.MISC, ModItems.SILVER_DRAGON_SCALE, builder -> builder.pattern("#X#").pattern("XDX").pattern("#X#").define('#', ModItems.ARCANE_CRYSTAL_DUST).define('X', Items.IRON_INGOT).define('D', ModItems.DRAGON_SCALE).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST).unlockedBy(this, ModItems.DRAGON_SCALE));
        this.shaped(RecipeCategory.MISC, ModItems.GOLDEN_DRAGON_SCALE, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.DEORUM_INGOT).define('X', ModItems.DRAGON_SCALE).unlockedBy(this, ModItems.DEORUM_INGOT).unlockedBy(this, ModItems.DRAGON_SCALE));
        this.shaped(RecipeCategory.MISC, ModItems.SPECTRAL_EYE_AMULET, builder -> builder.pattern(" # ").pattern("#X#").pattern("DED").define('#', Items.STRING).define('X', Items.NETHER_STAR).define('D', ModItems.DEORUM_INGOT).define('E', Items.ENDER_EYE).unlockedBy(this, ModItems.DEORUM_INGOT).unlockedBy(this, Items.NETHER_STAR));
        this.shaped(RecipeCategory.MISC, Items.ENDER_PEARL, builder -> builder.pattern(" # ").pattern("# #").pattern(" # ").define('#', ModItems.ENDER_PEARL_FRAGMENT).unlockedBy(this, ModItems.ENDER_PEARL_FRAGMENT));

        //Shapeless Recipes
        this.shapeless(RecipeCategory.MISC, ModItems.TEST_TUBE, builder -> builder.requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE).unlockedBy("has_item", has(ModItems.RUNE)));
        this.shapeless(RecipeCategory.MISC, ModItems.MUNDABITUR_DUST, 4, builder -> builder.requires(ModItems.ARCANE_CRYSTAL_DUST).requires(Items.REDSTONE).requires(Items.BLAZE_POWDER).requires(Items.BONE_MEAL).requires(Items.PHANTOM_MEMBRANE).requires(Items.GUNPOWDER).unlockedBy("has_arcane_crystal_dust", has(ModItems.ARCANE_CRYSTAL_DUST)).unlockedBy("has_redstone", has(Items.REDSTONE)).unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).unlockedBy("has_bone_meal", has(Items.BONE_MEAL)).unlockedBy("has_phantom_membrane", has(Items.PHANTOM_MEMBRANE)).unlockedBy("has_gunpowder", has(Items.GUNPOWDER)));
        this.shapeless(RecipeCategory.MISC, ModItems.WAX, 2, builder -> builder.requires(Items.HONEY_BOTTLE).requires(Items.SLIME_BALL).unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE)).unlockedBy("has_slime_ball", has(Items.SLIME_BALL)));
        this.shapeless(RecipeCategory.MISC, ModItems.CORRUPTI_DUST, 4, builder -> builder.requires(ModItems.OBSIDIANSTEEL_INGOT).requires(Items.BLAZE_POWDER).requires(Items.NETHER_WART).requires(ModItems.ARCANE_CRYSTAL_DUST).requires(ModItems.ENDER_PEARL_FRAGMENT).unlockedBy("has_obsidiansteel_ingot", has(ModItems.OBSIDIANSTEEL_INGOT)).unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).unlockedBy("has_nether_wart", has(Items.NETHER_WART)).unlockedBy("has_arcane_crystal_dust", has(ModItems.ARCANE_CRYSTAL_DUST)).unlockedBy("has_ender_pearl_fragment", has(ModItems.ENDER_PEARL_FRAGMENT)));
        this.shapeless(RecipeCategory.MISC, ModBlocks.OBSIDIAN_SKULL.getSkull(), builder -> builder.requires(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull()).requires(ModItems.OBSIDIANSTEEL_INGOT, 2).unlockedBy("has_cracked_obsidian_skull", has(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull())).unlockedBy("has_obsidiansteel_ingot", has(ModItems.OBSIDIANSTEEL_INGOT)), "obsidian_skull_from_cracked_obsidian_skull");
        this.shapeless(RecipeCategory.MISC, ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), builder -> builder.requires(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull()).requires(ModItems.OBSIDIANSTEEL_INGOT, 2).unlockedBy("has_fragmented_obsidian_skull", has(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull())).unlockedBy("has_obsidiansteel_ingot", has(ModItems.OBSIDIANSTEEL_INGOT)));
        this.shapeless(RecipeCategory.MISC, ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), builder -> builder.requires(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull()).requires(ModItems.OBSIDIANSTEEL_INGOT, 2).unlockedBy("has_fading_obsidian_skull", has(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull())).unlockedBy("has_obsidiansteel_ingot", has(ModItems.OBSIDIANSTEEL_INGOT)));
        this.shapeless(RecipeCategory.MISC, ModBlocks.QUANTUM_CORE.get(), builder -> builder.requires(ModItems.RUNE).requires(Items.FLINT).requires(ModItems.MUNDABITUR_DUST).unlockedBy("has_rune", has(ModItems.RUNE)).unlockedBy("has_flint", has(Items.FLINT)).unlockedBy("has_mundabitur_dust", has(ModItems.MUNDABITUR_DUST)));
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get(), builder -> builder.requires(ModBlocks.CHISELED_POLISHED_DARKSTONE.get()).requires(ModItems.DEORUM_NUGGET).unlockedBy("has_chiseled_polished_darkstone", has(ModBlocks.CHISELED_POLISHED_DARKSTONE.get())).unlockedBy("has_deorum_nugget", has(ModItems.DEORUM_NUGGET)));
        this.shapeless(RecipeCategory.MISC, ModItems.AQUATIC_DRAGON_SCALE.get(), builder -> builder.requires(ModItems.DRAGON_SCALE).requires(Items.PRISMARINE_SHARD).requires(Items.PRISMARINE_CRYSTALS).requires(ModItems.ARCANE_CRYSTAL_DUST).unlockedBy("has_dragon_scale", has(ModItems.DRAGON_SCALE)));
        this.shapeless(RecipeCategory.FOOD, ModItems.BAT_SOUP, builder -> builder.requires(Tags.Items.MUSHROOMS).requires(Items.NETHER_WART).requires(Items.BOWL).requires(ModItems.BAT_WING).unlockedBy("has_bat_wing", has(ModItems.BAT_WING)));
        this.shapeless(RecipeCategory.MISC, Items.BLACK_DYE, 2, builder -> builder.requires(ModItems.EDELWOOD_OIL).requires(ModItems.EDELWOOD_OIL).unlockedBy("has_edelwood_oil", has(ModItems.EDELWOOD_OIL)));
        this.shapeless(RecipeCategory.MISC, ModItems.ENDER_PEARL_FRAGMENT, 4, builder -> builder.requires(Items.ENDER_PEARL).unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL)));

        //Smelting Recipes
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())), "smelting/arcane_crystal_dust_from_smelting");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.RUNIC_STONES), RecipeCategory.MISC, ModItems.RUNE.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)), "smelting/rune_from_smelting");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)), "smelting/arcane_crystal_from_smelting");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TENTACLE.get()), RecipeCategory.FOOD, ModItems.COOKED_TENTACLE.get(), 0.35F, 200).unlockedBy("has_item", has(ModItems.TENTACLE.get())), "smelting/cooked_tentacle");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DARK_MATTER.get()), RecipeCategory.MISC, ModBlocks.EDELWOOD_LOG.get(), 0.4F, 400).unlockedBy("has_item", has(ModBlocks.EDELWOOD_LOG.get())), "smelting/dark_matter");

        //Blasting Recipes
        this.add(SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 75).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())), "blasting/arcane_crystal_dust_from_blasting");
        this.add(SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.RUNIC_STONES), RecipeCategory.MISC, ModItems.RUNE.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)), "blasting/rune_from_blasting");
        this.add(SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL.get(), 1.0F, 100).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)), "blasting/arcane_crystal_from_blasting");

        //Smithing Recipes
        //TODO
//        this.add(UpgradeRecipeBuilder.smithing(Ingredient.of(Items.SHIELD), Ingredient.of(ModItems.OBSIDIAN_SKULL.get()), RecipeCategory.MISC, ModItems.OBSIDIAN_SKULL_SHIELD.get()).unlocks("has_item", has(ModItems.OBSIDIAN_SKULL.get())),"smithing/obsidian_skull_shield");
//        this.add(UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.OBSIDIAN_SKULL.get()), Ingredient.of(ModItems.ETERNAL_STELLA.get()), RecipeCategory.MISC, ModItems.ETERNAL_OBSIDIAN_SKULL.get()).unlocks("has_item", has(ModItems.OBSIDIAN_SKULL.get())), new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/eternal_obsidian_skull"));
//        this.add(UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.DIAMOND_BLACKSMITH_GAVEL.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.MISC, ModItems.NETHERITE_BLACKSMITH_GAVEL.get()).unlocks("has_item", has(Items.NETHERITE_INGOT)), new ResourceLocation(ForbiddenArcanus.MOD_ID, "smithing/obsidian_blacksmith_gavel"));

        // Stonecutting Recipes
        this.addStonecutterRecipe(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.DARKSTONE.get());
        this.addStonecutterRecipe(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE.get());
        this.addStonecutterRecipe(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE_BRICKS.get());

        this.storageRecipe(ModItems.DARK_NETHER_STAR.get(), ModBlocks.DARK_NETHER_STAR_BLOCK.get());
        this.storageRecipe(ModItems.OBSIDIANSTEEL_INGOT.get(), ModBlocks.OBSIDIANSTEEL_BLOCK.get(), "obsidian_ingot", null);
        this.storageRecipe(ModItems.DEORUM_INGOT.get(), ModBlocks.DEORUM_BLOCK.get(), "deorum_ingot", null);
        this.storageRecipe(ModItems.DEORUM_NUGGET.get(), ModItems.DEORUM_INGOT.get(), null, "deorum_ingot");
        this.storageRecipe(ModItems.STELLARITE_PIECE.get(), ModBlocks.STELLARITE_BLOCK.get());
        this.storageRecipe(ModItems.ARCANE_CRYSTAL.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
        this.storageRecipe(ModItems.CORRUPTED_ARCANE_CRYSTAL.get(), ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
        this.storageRecipe(ModItems.RUNE.get(), ModBlocks.RUNE_BLOCK.get());

        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEORUM_GLASS.get(), this.tag(ModTags.Items.DEORUM_INGOTS), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.DEORUM_GLASS_PANE.get(), this.item(ModBlocks.DEORUM_GLASS.get()));
        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUNIC_GLASS.get(), RecipePart.of(ModItems.RUNE.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.RUNIC_GLASS_PANE.get(), this.item(ModBlocks.RUNIC_GLASS.get()));

        this.combine2x2(ModBlocks.SOULLESS_SANDSTONE.get(), this.item(ModBlocks.SOULLESS_SAND.get()));
        this.slab(ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), this.item(ModBlocks.SOULLESS_SANDSTONE.get()));
        this.stairs(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), this.item(ModBlocks.SOULLESS_SANDSTONE.get()));
        this.wall(ModBlocks.SOULLESS_SANDSTONE_WALL.get(), this.item(ModBlocks.SOULLESS_SANDSTONE.get()));
        this.combine2x2(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), this.item(ModBlocks.SOULLESS_SANDSTONE.get()), 4);

        this.wood(ModBlocks.FUNGYSS_HYPHAE.get(), this.item(ModBlocks.FUNGYSS_STEM.get()));
        this.wood(ModBlocks.AURUM_WOOD.get(), this.item(ModBlocks.AURUM_LOG.get()));

        this.wood(ModBlocks.STRIPPED_AURUM_WOOD.get(), this.item(ModBlocks.STRIPPED_AURUM_LOG.get()));

        this.planks(ModBlocks.FUNGYSS_PLANKS.get(), ModTags.Items.FUNGYSS_STEMS, 4);
        this.planks(ModBlocks.AURUM_PLANKS.get(), ModTags.Items.MYSTERYWOOD_LOGS, 4);
        this.planks(ModBlocks.EDELWOOD_PLANKS.get(), ModTags.Items.EDELWOOD_LOGS, 2);

        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ARCANE_EDELWOOD_PLANKS.get(), this.tag(ModTags.Items.DEORUM_INGOTS), this.item(ModBlocks.EDELWOOD_PLANKS.get()), 8);

        this.door(ModBlocks.DEORUM_DOOR.get(), this.tag(ModTags.Items.DEORUM_INGOTS));

        this.trapdoor(ModBlocks.DEORUM_TRAPDOOR.get(), this.tag(ModTags.Items.DEORUM_INGOTS));

        this.boat(ModItems.AURUM_BOAT.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.boat(ModItems.EDELWOOD_BOAT.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.chestBoat(ModItems.AURUM_CHEST_BOAT.get(), ModItems.AURUM_BOAT.get());
        this.chestBoat(ModItems.EDELWOOD_CHEST_BOAT.get(), ModItems.EDELWOOD_BOAT.get());

        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLIBANO_CORE.get(), RecipePart.of(Blocks.BLAST_FURNACE), RecipePart.of(ModBlocks.DARKSTONE.get()), 1);

        this.lantern(ModBlocks.DEORUM_LANTERN.get(), Blocks.TORCH, RecipePart.of(ModTags.Items.DEORUM_NUGGETS));
        this.lantern(ModBlocks.DEORUM_SOUL_LANTERN.get(), Blocks.SOUL_TORCH, RecipePart.of(ModTags.Items.DEORUM_NUGGETS));

        this.shapeless(RecipeCategory.MISC, ModItems.GOLDEN_ORCHID_SEEDS.get(), builder -> builder.requires(ModBlocks.YELLOW_ORCHID.get()).requires(ModTags.Items.DEORUM_INGOTS).unlockedBy("has_yellow_orchid", has(ModBlocks.YELLOW_ORCHID.get())).unlockedBy("has_deorum_ingot", has(ModTags.Items.DEORUM_INGOTS)));

        this.shaped(RecipeCategory.MISC, ModItems.BLACKSMITH_GAVEL_HEAD.get(), builder -> builder.pattern("###").pattern("# #").pattern(" # ").define('#', Items.CLAY_BALL).unlockedBy(this, Items.CLAY_BALL));
        this.blacksmithGavel(ModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS);
        this.blacksmithGavel(ModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS);
        this.blacksmithGavel(ModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD);
        this.blacksmithGavel(ModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON);
        this.blacksmithGavel(ModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND);
    }

    public void slab(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6, (builder) -> {
            return builder.pattern("###").define('#', material).unlockedBy(this, material);
        });
    }

    public void stairs(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4, (builder) -> {
            return builder.pattern("#  ").pattern("## ").pattern("###").define('#', material).unlockedBy(this, material);
        });
    }

    public void wall(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6, (builder) -> {
            return builder.pattern("###").pattern("###").define('#', material).unlockedBy(this, material);
        });
    }

    public void combine2x2(ItemLike result, RecipePart<?> material) {
        this.combine2x2(result, material, 1);
    }

    public void combine2x2(ItemLike result, RecipePart<?> material, int count) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, count, (builder) -> {
            return builder.pattern("##").pattern("##").define('#', material).unlockedBy(this, material);
        });
    }

    private void lantern(ItemLike result, ItemLike torch, RecipePart<?> material) {
        this.shaped(RecipeCategory.DECORATIONS, result, builder -> builder.pattern("XXX").pattern("X#X").pattern("XXX").define('X', material).define('#', torch).unlockedBy(this, material).unlockedBy(this, torch));
    }

    private void blacksmithGavel(ItemLike gavel, TagKey<Item> material) {
        this.shaped(RecipeCategory.TOOLS, gavel, builder -> builder.pattern("#X#").pattern("#S#").pattern(" S ").define('#', material).define('X', ModItems.BLACKSMITH_GAVEL_HEAD.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy(this, RecipePart.of(material)));
    }

    private void addStonecutterRecipe(ItemLike result, ItemLike material) {
        this.addStonecutterRecipe(result, material, 1);
    }

    private void addStonecutterRecipe(ItemLike result, ItemLike material, int count) {
        this.add(SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), RecipeCategory.BUILDING_BLOCKS, result, count).unlockedBy("has_" + this.getName(material), has(material)), this.getName(result) + "_from_" + this.getName(material) + "_stonecutting");
    }
}
