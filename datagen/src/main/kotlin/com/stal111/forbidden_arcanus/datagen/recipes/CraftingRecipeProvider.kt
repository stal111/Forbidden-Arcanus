package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.data.FABlockFamilies
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.common.crafting.DataComponentIngredient
import net.valhelsia.dataforge.recipe.*

class CraftingRecipeProvider(
    lookupProvider: HolderLookup.Provider,
    recipeOutput: RecipeOutput
) : RecipeSubProvider(lookupProvider, recipeOutput) {
    override fun buildRecipes() {
        FABlockFamilies.getAllFamilies()
            .filter { it.shouldGenerateCraftingRecipe() }
            .forEach { generateRecipes(it, FeatureFlags.VANILLA_SET) }

        //Shaped Recipes
        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.UTREM_JAR.get()) {
            it.pattern("#X#", "# #", "###")
                .define('#' to Tags.Items.GLASS_BLOCKS_COLORLESS, 'X' to ModBlocks.EDELWOOD_PLANKS.get())
                .unlockedBy(Tags.Items.GLASS_BLOCKS_COLORLESS)
        }
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST) {
            it.pattern("###", "###", "###")
                .define('#' to ModItems.ARCANE_CRYSTAL_DUST_SPECK)
                .unlockedBy(ModItems.ARCANE_CRYSTAL_DUST_SPECK)
        }
        this.shaped(RecipeCategory.TOOLS, ModItems.ECTO_BLASTER) {
            it.pattern("U  ", "##X", "Q  ")
                .define(
                    'U' to ModBlocks.UTREM_JAR.get(),
                    '#' to Blocks.NETHER_BRICKS,
                    'X' to Blocks.QUARTZ_BLOCK,
                    'Q' to Items.QUARTZ
                )
                .unlockedBy(ModBlocks.UTREM_JAR.get())
        }
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_BONE_MEAL, 4) {
            it.pattern(" # ", "#X#", " # ")
                .define('#' to Items.BONE_MEAL, 'X' to ModItems.ARCANE_CRYSTAL_DUST)
                .unlockedBy(ModItems.ARCANE_CRYSTAL_DUST)
        }
        this.shaped(RecipeCategory.MISC, ModItems.AUREAL_BOTTLE) {
            it.pattern("###", "#X#", "###").define(
                '#' to ModItems.ARCANE_CRYSTAL_DUST,
                'X' to DataComponentIngredient.of(
                    true,
                    DataComponents.POTION_CONTENTS,
                    PotionContents(Potions.STRONG_REGENERATION),
                    Items.POTION
                )
            ).unlockedBy(ModItems.ARCANE_CRYSTAL_DUST)
        }
        this.shaped(RecipeCategory.TOOLS, ModItems.OAK_WAND) {
            it.pattern("  E", " D ", "A  ")
                .define('E' to ModItems.EDELWOOD_STICK, 'D' to ModItems.DEORUM_INGOT, 'A' to ModItems.ARCANE_CRYSTAL)
                .unlockedBy(ModItems.EDELWOOD_STICK, ModItems.DEORUM_INGOT, ModItems.ARCANE_CRYSTAL)
        }

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARKSTONE_PEDESTAL.get()) {
            it.pattern("###", " * ", "XXX")
                .define(
                    '#' to ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get(),
                    '*' to ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get(),
                    'X' to ModBlocks.POLISHED_DARKSTONE.get()
                )
                .unlockedBy(ModBlocks.POLISHED_DARKSTONE.get())
        }

        this.shaped(RecipeCategory.MISC, ModItems.DARK_NETHER_STAR) {
            it.pattern(" # ", "#X#", " # ")
                .define('#' to ModItems.OBSIDIANSTEEL_INGOT, 'X' to Items.NETHER_STAR)
                .unlockedBy(ModItems.OBSIDIANSTEEL_INGOT, Items.NETHER_STAR)
        }
        this.shaped(RecipeCategory.MISC, ModItems.OBSIDIANSTEEL_INGOT) {
            it.pattern(" # ", "#X#", " # ")
                .define('#' to Items.OBSIDIAN, 'X' to Items.IRON_INGOT)
                .unlockedBy(Items.OBSIDIAN, Items.IRON_INGOT)
        }
        this.shaped(RecipeCategory.MISC, ModItems.DEORUM_INGOT) {
            it.pattern("#*#", "MXM", "#*#")
                .define(
                    '#' to Items.CHARCOAL,
                    'X' to Items.GOLD_INGOT,
                    'M' to ModItems.MUNDABITUR_DUST,
                    '*' to ModItems.ARCANE_CRYSTAL_DUST
                )
                .unlockedBy(Items.GOLD_INGOT, ModItems.ARCANE_CRYSTAL_DUST, ModItems.MUNDABITUR_DUST)
        }

        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.DEORUM_CHAIN.get()) {
            it.pattern("#", "X", "#")
                .define('#' to ModTags.Items.DEORUM_NUGGETS, 'X' to ModTags.Items.DEORUM_INGOTS)
                .unlockedBy(ModTags.Items.DEORUM_INGOTS, ModTags.Items.DEORUM_NUGGETS)
        }

        for (color in DyeColor.entries) {
            this.shaped(RecipeCategory.TOOLS, ModItems.DYED_QUANTUM_CATCHERS.get(color)!!) {
                it.pattern(" # ", "#X#", " # ")
                    .define('X' to ModItems.QUANTUM_CATCHER, '#' to color.tag)
                    .unlockedBy(ModItems.QUANTUM_CATCHER, color.tag)
            }
        }

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EDELWOOD_LADDER.get(), 3) {
            it.pattern("# #", "#X#", "# #")
                .define('#' to Tags.Items.RODS_WOODEN, 'X' to ModBlocks.EDELWOOD_PLANKS.get())
                .unlockedBy(Tags.Items.RODS_WOODEN, ModBlocks.EDELWOOD_PLANKS.get())
        }
        this.shaped(RecipeCategory.MISC, ModItems.EDELWOOD_BUCKET) {
            it.pattern("# #", "# #", " # ")
                .define('#' to ModBlocks.EDELWOOD_PLANKS.get())
                .unlockedBy(ModBlocks.EDELWOOD_PLANKS.get())
        }
        this.shaped(RecipeCategory.MISC, ModItems.BOOM_ARROW, 4) {
            it.pattern(" # ", "#X#", " # ")
                .define('X' to Blocks.TNT, '#' to Items.ARROW)
                .unlockedBy(Blocks.TNT, Items.ARROW)
        }
        this.surroundingItem(
            RecipeCategory.MISC,
            ModBlocks.ARCANE_POLISHED_DARKSTONE.get(),
            ModItems.DEORUM_INGOT,
            ModBlocks.POLISHED_DARKSTONE.get(),
            8
        )
        this.surroundingItem(
            RecipeCategory.MISC,
            ModBlocks.OBSIDIAN_SKULL.getSkull(),
            Items.SKELETON_SKULL,
            ModItems.OBSIDIANSTEEL_INGOT,
            1
        )
        this.surroundingItem(
            RecipeCategory.MISC,
            ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE,
            Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
            ModBlocks.DARKSTONE.get(),
            1
        )
        this.shaped(RecipeCategory.MISC, ModBlocks.ARCANE_DRAGON_EGG.get()) {
            it.pattern(" # ", "#X#", "###")
                .define('#' to ModItems.GOLDEN_DRAGON_SCALE, 'X' to Blocks.DRAGON_EGG)
                .unlockedBy(Blocks.DRAGON_EGG, ModItems.GOLDEN_DRAGON_SCALE)
        }
        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get()) {
            it.pattern("#", "#")
                .define('#' to ModBlocks.ARCANE_POLISHED_DARKSTONE.get())
                .unlockedBy(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())
        }
        this.shaped(RecipeCategory.MISC, ModItems.DRACO_ARCANUS_ARROW) {
            it.pattern(" # ", "#X#", " # ")
                .define('#' to Items.ARROW, 'X' to Items.DRAGON_BREATH)
                .unlockedBy(Items.ARROW, Items.DRAGON_BREATH)
        }
        this.shaped(RecipeCategory.MISC, ModItems.SILVER_DRAGON_SCALE) {
            it.pattern("#X#", "XDX", "#X#")
                .define('#' to ModItems.ARCANE_CRYSTAL_DUST, 'X' to Items.IRON_INGOT, 'D' to ModItems.DRAGON_SCALE)
                .unlockedBy(ModItems.ARCANE_CRYSTAL_DUST, ModItems.DRAGON_SCALE)
        }
        this.shaped(RecipeCategory.MISC, ModItems.GOLDEN_DRAGON_SCALE) {
            it.pattern(" # ", "#X#", " # ")
                .define('#' to ModItems.DEORUM_INGOT, 'X' to ModItems.DRAGON_SCALE)
                .unlockedBy(ModItems.DEORUM_INGOT, ModItems.DRAGON_SCALE)
        }
        this.shaped(RecipeCategory.MISC, ModItems.SPECTRAL_EYE_AMULET) {
            it.pattern(" # ", "#X#", "DED")
                .define(
                    '#' to Items.STRING,
                    'X' to Items.NETHER_STAR,
                    'D' to ModItems.DEORUM_INGOT,
                    'E' to Items.ENDER_EYE
                )
                .unlockedBy(ModItems.DEORUM_INGOT, Items.NETHER_STAR)
        }
        this.shaped(RecipeCategory.MISC, Items.ENDER_PEARL) {
            it.pattern(" # ", "# #", " # ")
                .define('#' to ModItems.ENDER_PEARL_FRAGMENT)
                .unlockedBy(ModItems.ENDER_PEARL_FRAGMENT)
        }
        this.shaped(RecipeCategory.MISC, ModItems.CONTAINMENT_CAPSULE) {
            it.pattern("Q#Q", "#X#", "IEI")
                .define(
                    'Q' to Items.QUARTZ,
                    '#' to Items.IRON_NUGGET,
                    'X' to ModItems.UTREM_JAR,
                    'I' to Items.IRON_INGOT,
                    'E' to ModItems.ENDER_PEARL_FRAGMENT
                )
                .unlockedBy(Items.QUARTZ, Items.IRON_INGOT, ModItems.ENDER_PEARL_FRAGMENT)
        }

        //Shapeless Recipes
        this.shapeless(RecipeCategory.MISC, ModItems.TEST_TUBE) {
            it.requires(Items.GLASS_BOTTLE, ModItems.RUNE).unlockedBy(ModItems.RUNE)
        }
        this.shapeless(RecipeCategory.MISC, ModItems.MUNDABITUR_DUST, 4) {
            it.requires(
                ModItems.ARCANE_CRYSTAL_DUST,
                Items.REDSTONE,
                Items.BLAZE_POWDER,
                Items.BONE_MEAL,
                Items.PHANTOM_MEMBRANE,
                Items.GUNPOWDER
            ).unlockedBy(
                ModItems.ARCANE_CRYSTAL_DUST,
                Items.REDSTONE,
                Items.BLAZE_POWDER,
                Items.BONE_MEAL,
                Items.PHANTOM_MEMBRANE,
                Items.GUNPOWDER
            )
        }
        this.shapeless(RecipeCategory.MISC, ModItems.WAX, 2) {
            it.requires(Items.HONEY_BOTTLE, Items.SLIME_BALL).unlockedBy(Items.HONEY_BOTTLE, Items.SLIME_BALL)
        }
        this.shapeless(RecipeCategory.MISC, ModItems.CORRUPTI_DUST, 4) {
            it.requires(
                ModItems.OBSIDIANSTEEL_INGOT,
                Items.BLAZE_POWDER,
                Items.NETHER_WART,
                ModItems.ARCANE_CRYSTAL_DUST,
                ModItems.ENDER_PEARL_FRAGMENT
            ).unlockedBy(
                ModItems.OBSIDIANSTEEL_INGOT,
                Items.BLAZE_POWDER,
                Items.NETHER_WART,
                ModItems.ARCANE_CRYSTAL_DUST,
                ModItems.ENDER_PEARL_FRAGMENT
            )
        }
        this.shapeless(
            RecipeCategory.MISC,
            ModBlocks.OBSIDIAN_SKULL.getSkull(),
            1,
            "obsidian_skull_from_cracked_obsidian_skull"
        ) {
            it.requires(
                ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(),
                ModItems.OBSIDIANSTEEL_INGOT,
                ModItems.OBSIDIANSTEEL_INGOT
            ).unlockedBy(ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), ModItems.OBSIDIANSTEEL_INGOT)
        }
        this.shapeless(RecipeCategory.MISC, ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull()) {
            it.requires(
                ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(),
                ModItems.OBSIDIANSTEEL_INGOT,
                ModItems.OBSIDIANSTEEL_INGOT
            ).unlockedBy(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), ModItems.OBSIDIANSTEEL_INGOT)
        }
        this.shapeless(RecipeCategory.MISC, ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull()) {
            it.requires(
                ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(),
                ModItems.OBSIDIANSTEEL_INGOT,
                ModItems.OBSIDIANSTEEL_INGOT
            ).unlockedBy(ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(), ModItems.OBSIDIANSTEEL_INGOT)
        }
        this.shapeless(RecipeCategory.MISC, ModBlocks.QUANTUM_CORE.get()) {
            it.requires(ModItems.RUNE, Items.FLINT, ModItems.MUNDABITUR_DUST)
                .unlockedBy(ModItems.RUNE, Items.FLINT, ModItems.MUNDABITUR_DUST)
        }
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get()) {
            it.requires(ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModItems.DEORUM_NUGGET)
                .unlockedBy(ModBlocks.CHISELED_POLISHED_DARKSTONE.get(), ModItems.DEORUM_NUGGET)
        }
        this.shapeless(RecipeCategory.MISC, ModItems.AQUATIC_DRAGON_SCALE.get()) {
            it.requires(
                ModItems.DRAGON_SCALE,
                Items.PRISMARINE_SHARD,
                Items.PRISMARINE_CRYSTALS,
                ModItems.ARCANE_CRYSTAL_DUST
            ).unlockedBy(ModItems.DRAGON_SCALE)
        }
        this.shapeless(RecipeCategory.FOOD, ModItems.BAT_SOUP) {
            it.requires(Tags.Items.MUSHROOMS, Items.NETHER_WART, Items.BOWL, ModItems.BAT_WING)
                .unlockedBy(ModItems.BAT_WING)
        }
        this.shapeless(RecipeCategory.MISC, Items.BLACK_DYE, 2, "black_dye") {
            it.requires(ModItems.EDELWOOD_OIL).unlockedBy(ModItems.EDELWOOD_OIL)
        }
        this.shapeless(RecipeCategory.MISC, ModItems.ENDER_PEARL_FRAGMENT, 4) {
            it.requires(Items.ENDER_PEARL).unlockedBy(Items.ENDER_PEARL)
        }

        //Smelting Recipes
        this.add(
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.ARCANE_CRYSTAL.get()),
                RecipeCategory.MISC,
                CookingBookCategory.MISC,
                ModItems.ARCANE_CRYSTAL_DUST.get(),
                0.4f,
                150
            ).unlockedBy(ModItems.ARCANE_CRYSTAL), "smelting/arcane_crystal_dust_from_smelting"
        )
        this.add(
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(items.getOrThrow(ModTags.Items.RUNIC_STONES)),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.RUNE.get(),
                1.0f,
                200
            ).unlockedBy(ModTags.Items.RUNIC_STONES), "smelting/rune_from_smelting"
        )
        this.add(
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(items.getOrThrow(ModTags.Items.ARCANE_CRYSTAL_ORES)),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.ARCANE_CRYSTAL.get(),
                1.0f,
                200
            ).unlockedBy(ModTags.Items.ARCANE_CRYSTAL_ORES), "smelting/arcane_crystal_from_smelting"
        )
        this.add(
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.TENTACLE.get()),
                RecipeCategory.FOOD,
                CookingBookCategory.FOOD,
                ModItems.COOKED_TENTACLE.get(),
                0.35f,
                200
            ).unlockedBy(ModItems.TENTACLE.get()), "smelting/cooked_tentacle"
        )
        this.add(
            SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModBlocks.EDELWOOD_LOG.get()),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.DARK_MATTER.get(),
                0.4f,
                400
            ).unlockedBy(ModBlocks.EDELWOOD_LOG.get()), "smelting/dark_matter"
        )

        //Blasting Recipes
        this.add(
            SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(ModItems.ARCANE_CRYSTAL.get()),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.ARCANE_CRYSTAL_DUST.get(),
                0.4f,
                75
            ).unlockedBy(ModItems.ARCANE_CRYSTAL.get()), "blasting/arcane_crystal_dust_from_blasting"
        )
        this.add(
            SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(items.getOrThrow(ModTags.Items.RUNIC_STONES)),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.RUNE.get(),
                1.0f,
                100
            ).unlockedBy(ModTags.Items.RUNIC_STONES), "blasting/rune_from_blasting"
        )
        this.add(
            SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(items.getOrThrow(ModTags.Items.ARCANE_CRYSTAL_ORES)),
                RecipeCategory.MISC,
                CookingBookCategory.BLOCKS,
                ModItems.ARCANE_CRYSTAL.get(),
                1.0f,
                100
            ).unlockedBy(ModTags.Items.ARCANE_CRYSTAL_ORES), "blasting/arcane_crystal_from_blasting"
        )

        netheriteSmithing(
            ModItems.DIAMOND_BLACKSMITH_GAVEL.get(),
            RecipeCategory.TOOLS,
            ModItems.NETHERITE_BLACKSMITH_GAVEL.get()
        )

        // Stonecutting Recipes
        this.addStonecutterRecipe(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.DARKSTONE.get())
        this.addStonecutterRecipe(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(), ModBlocks.POLISHED_DARKSTONE.get())
        this.addStonecutterRecipe(
            ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get(),
            ModBlocks.POLISHED_DARKSTONE_BRICKS.get()
        )

        this.storageRecipe(
            ModItems.OBSIDIANSTEEL_INGOT.get(),
            ModBlocks.OBSIDIANSTEEL_BLOCK.get(),
            "obsidian_ingot",
            null
        )
        this.storageRecipe(ModItems.DEORUM_INGOT.get(), ModBlocks.DEORUM_BLOCK.get(), "deorum_ingot", null)
        this.storageRecipe(ModItems.DEORUM_NUGGET.get(), ModItems.DEORUM_INGOT.get(), null, "deorum_ingot")
        this.storageRecipe(ModItems.STELLARITE_PIECE.get(), ModBlocks.STELLARITE_BLOCK.get())
        this.storageRecipe(ModItems.ARCANE_CRYSTAL.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get())
        this.storageRecipe(ModItems.CORRUPTED_ARCANE_CRYSTAL.get(), ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get())
        this.storageRecipe(ModItems.RUNE.get(), ModBlocks.RUNE_BLOCK.get())

        this.surroundingItem(
            RecipeCategory.BUILDING_BLOCKS,
            ModBlocks.DEORUM_GLASS.get(),
            ModTags.Items.DEORUM_INGOTS,
            Blocks.GLASS,
            8
        )
        this.glassPane(ModBlocks.DEORUM_GLASS_PANE.get(), ModBlocks.DEORUM_GLASS.get())
        this.surroundingItem(
            RecipeCategory.BUILDING_BLOCKS,
            ModBlocks.RUNIC_GLASS.get(),
            ModItems.RUNE.get(),
            Blocks.GLASS,
            8
        )
        this.glassPane(ModBlocks.RUNIC_GLASS_PANE.get(), ModBlocks.RUNIC_GLASS.get())

        this.combine2x2(ModBlocks.SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SAND.get())
        this.slab(ModBlocks.SOULLESS_SANDSTONE_SLAB.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.stairs(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.wall(ModBlocks.SOULLESS_SANDSTONE_WALL.get(), ModBlocks.SOULLESS_SANDSTONE.get())
        this.combine2x2(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get(), ModBlocks.SOULLESS_SANDSTONE.get(), 4)

        this.wood(ModBlocks.FUNGYSS_HYPHAE.get(), ModBlocks.FUNGYSS_STEM.get())
        this.wood(ModBlocks.AURUM_WOOD.get(), ModBlocks.AURUM_LOG.get())

        this.wood(ModBlocks.STRIPPED_AURUM_WOOD.get(), ModBlocks.STRIPPED_AURUM_LOG.get())

        this.planks(ModBlocks.FUNGYSS_PLANKS.get(), ModTags.Items.FUNGYSS_STEMS, 4)
        this.planks(ModBlocks.AURUM_PLANKS.get(), ModTags.Items.MYSTERYWOOD_LOGS, 4)
        this.planks(ModBlocks.EDELWOOD_PLANKS.get(), ModTags.Items.EDELWOOD_LOGS, 2)

        this.surroundingItem(
            RecipeCategory.BUILDING_BLOCKS,
            ModBlocks.ARCANE_EDELWOOD_PLANKS.get(),
            ModTags.Items.DEORUM_INGOTS,
            ModBlocks.EDELWOOD_PLANKS.get(),
            8
        )

        this.door(ModBlocks.DEORUM_DOOR.get(), ModTags.Items.DEORUM_INGOTS)

        this.trapdoor(ModBlocks.DEORUM_TRAPDOOR.get(), ModTags.Items.DEORUM_INGOTS)

//        this.boat(ModItems.AURUM_BOAT.get(), ModBlocks.AURUM_PLANKS.get())
//        this.boat(ModItems.EDELWOOD_BOAT.get(), ModBlocks.EDELWOOD_PLANKS.get())
//
//        this.chestBoat(ModItems.AURUM_CHEST_BOAT.get(), ModItems.AURUM_BOAT.get())
//        this.chestBoat(ModItems.EDELWOOD_CHEST_BOAT.get(), ModItems.EDELWOOD_BOAT.get())

        shaped(RecipeCategory.DECORATIONS, ModBlocks.CLIBANO_CORE.get()) {
            it.pattern("X#X", "#B#", "X#X")
                .define('X' to ModItems.ASTERITE_CHUNK, '#' to ModBlocks.DARKSTONE.get(), 'B' to Blocks.BLAST_FURNACE)
                .unlockedBy(ModItems.ASTERITE_CHUNK.get(), ModBlocks.DARKSTONE.get())
        }

        this.lantern(ModBlocks.DEORUM_LANTERN.get(), Blocks.TORCH, ModTags.Items.DEORUM_NUGGETS)
        this.lantern(ModBlocks.DEORUM_SOUL_LANTERN.get(), Blocks.SOUL_TORCH, ModTags.Items.DEORUM_NUGGETS)

        this.shaped(RecipeCategory.MISC, ModItems.BLACKSMITH_GAVEL_HEAD.get()) {
            it.pattern("###", "# #", " # ")
                .define('#', Items.CLAY_BALL)
                .unlockedBy(Items.CLAY_BALL)
        }
        this.blacksmithGavel(ModItems.WOODEN_BLACKSMITH_GAVEL.get(), ItemTags.PLANKS)
        this.blacksmithGavel(ModItems.STONE_BLACKSMITH_GAVEL.get(), ItemTags.STONE_TOOL_MATERIALS)
        this.blacksmithGavel(ModItems.GOLDEN_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_GOLD)
        this.blacksmithGavel(ModItems.IRON_BLACKSMITH_GAVEL.get(), Tags.Items.INGOTS_IRON)
        this.blacksmithGavel(ModItems.DIAMOND_BLACKSMITH_GAVEL.get(), Tags.Items.GEMS_DIAMOND)
    }

//    override fun slab(result: ItemLike?, material: RecipePart<*>) {
//        this.shaped(
//            RecipeCategory.BUILDING_BLOCKS,
//            result,
//            6,
//            UnaryOperator { builder: ValhelsiaShapedRecipeBuilder? ->
//                builder!!.pattern("###").define('#', material).unlockedBy(this, material)
//            })
//    }

//    override fun stairs(result: ItemLike?, material: RecipePart<*>) {
//        this.shaped(
//            RecipeCategory.BUILDING_BLOCKS,
//            result,
//            4,
//            UnaryOperator { builder: ValhelsiaShapedRecipeBuilder? ->
//                builder!!.pattern("#  ").pattern("## ").pattern("###").define('#', material).unlockedBy(this, material)
//            })
//    }

    fun wall(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) {
            it.pattern("###", "###").define('#' to material).unlockedBy(material)
        }
    }

    fun combine2x2(result: ItemLike, material: DataForgeRecipePart, count: Int = 1) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, count) {
            it.pattern("##", "##").define('#' to material).unlockedBy(material)
        }
    }

    private fun lantern(result: ItemLike, torch: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.DECORATIONS, result) {
            it.pattern("XXX", "X#X", "XXX")
                .define('X' to material, '#' to torch)
                .unlockedBy(material, torch)
        }
    }

    private fun blacksmithGavel(gavel: ItemLike, material: TagKey<Item>) {
        this.shaped(RecipeCategory.TOOLS, gavel) {
            it.pattern("#X#", "#S#", " S ")
                .define('#' to material, 'X' to ModItems.BLACKSMITH_GAVEL_HEAD.get(), 'S' to Tags.Items.RODS_WOODEN)
                .unlockedBy(material)
        }
    }

    private fun addStonecutterRecipe(result: ItemLike, material: ItemLike, count: Int = 1) {
        this.add(
            SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(material),
                RecipeCategory.BUILDING_BLOCKS,
                result,
                count
            ).unlockedBy(material),
            result.getName() + "_from_" + material.getName() + "_stonecutting"
        )
    }
}
