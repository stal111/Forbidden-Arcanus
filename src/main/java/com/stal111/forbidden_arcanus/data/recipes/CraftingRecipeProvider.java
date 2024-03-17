package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.data.FABlockFamilies;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.NBTIngredient;
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
        this.shaped(RecipeCategory.TOOLS, ModItems.SANITY_METER.get(), builder -> builder.pattern("AXA").pattern("X#X").pattern("AXA").define('X', Tags.Items.INGOTS_GOLD).define('#', Tags.Items.ENDER_PEARLS).define('A', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy(this, RecipePart.of(Tags.Items.INGOTS_GOLD)));
        this.shaped(RecipeCategory.TOOLS, ModItems.LENS_OF_VERITATIS.get(), builder -> builder.pattern(" # ").pattern("#X#").pattern("S# ").define('#', ModItems.SPAWNER_SCRAP.get()).define('X', ModItems.ARCANE_CRYSTAL.get()).define('S', Tags.Items.RODS_WOODEN).unlockedBy(this, ModItems.ARCANE_CRYSTAL.get()));
        this.shaped(RecipeCategory.TOOLS, ModBlocks.OBSIDIAN_SKULL.getSkull(), builder -> builder.pattern("#O#").pattern("OXO").pattern("#O#").define('#', ModItems.OBSIDIANSTEEL_INGOT.get()).define('X', Items.SKELETON_SKULL).define('O', Tags.Items.OBSIDIAN).unlockedBy(this, RecipePart.of(Tags.Items.OBSIDIAN)));
        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.UTREM_JAR.get(), builder -> builder.pattern("#X#").pattern("# #").pattern("###").define('#', Tags.Items.GLASS_COLORLESS).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy(this, RecipePart.of(Tags.Items.GLASS_COLORLESS)));
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST.get(), builder -> builder.pattern("###").pattern("###").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST_SPECK.get()).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST_SPECK.get()));
        this.shaped(RecipeCategory.TOOLS, ModItems.SOUL_EXTRACTOR.get(), builder -> builder.pattern("U  ").pattern("##X").pattern("Q  ").define('U', ModBlocks.UTREM_JAR.get()).define('#', Blocks.NETHER_BRICKS).define('X', Blocks.QUARTZ_BLOCK).define('Q', Items.QUARTZ).unlockedBy(this, ModBlocks.UTREM_JAR.get()));
        this.shaped(RecipeCategory.MISC, ModItems.ARCANE_BONE_MEAL.get(), 4, builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', Items.BONE_MEAL).define('X', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST.get()));
        this.shaped(RecipeCategory.MISC, ModItems.AUREAL_BOTTLE.get(), builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', ModItems.ARCANE_CRYSTAL_DUST.get()).define('X', NBTIngredient.of(true, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION))).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST.get()));

        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARKSTONE_PEDESTAL.get(), builder -> builder.pattern("###").pattern(" * ").pattern("XXX").define('#', ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB.get()).define('*', ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get()).define('X', ModBlocks.POLISHED_DARKSTONE.get()).unlockedBy(this, ModBlocks.POLISHED_DARKSTONE.get()));

        this.shaped(RecipeCategory.MISC, ModItems.DARK_NETHER_STAR.get(), builder -> builder.pattern(" # ").pattern("#X#").pattern(" # ").define('#', ModItems.OBSIDIANSTEEL_INGOT.get()).define('X', Items.NETHER_STAR).unlockedBy(this, ModItems.OBSIDIANSTEEL_INGOT.get()).unlockedBy(this, Items.NETHER_STAR));
        this.shaped(RecipeCategory.MISC, ModItems.DEORUM_INGOT.get(), builder -> builder.pattern("#*#").pattern("MXM").pattern("#*#").define('#', Items.CHARCOAL).define('X', Items.GOLD_INGOT).define('M', ModItems.MUNDABITUR_DUST.get()).define('*', ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy(this, Items.GOLD_INGOT).unlockedBy(this, ModItems.ARCANE_CRYSTAL_DUST.get()).unlockedBy(this, ModItems.MUNDABITUR_DUST.get()));

        this.shaped(RecipeCategory.DECORATIONS, ModBlocks.DEORUM_CHAIN.get(), builder -> builder.pattern("#").pattern("X").pattern("#").define('#', ModTags.Items.DEORUM_NUGGETS).define('X', ModTags.Items.DEORUM_INGOTS).unlockedBy(this, RecipePart.of(ModTags.Items.DEORUM_INGOTS)).unlockedBy(this, RecipePart.of(ModTags.Items.DEORUM_NUGGETS)));


        this.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EDELWOOD_LADDER.get(), 3, builder -> builder.pattern("# #").pattern("#X#").pattern("# #").define('#', Tags.Items.RODS_WOODEN).define('X', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy(this, RecipePart.of(Tags.Items.RODS_WOODEN)).unlockedBy(this, ModBlocks.EDELWOOD_PLANKS.get()));
        this.shaped(RecipeCategory.MISC, ModItems.EDELWOOD_BUCKET.get(), builder -> builder.pattern("# #").pattern("# #").pattern(" # ").define('#', ModBlocks.EDELWOOD_PLANKS.get()).unlockedBy("has_planks", has(ModBlocks.EDELWOOD_PLANKS.get())));

        //Shapeless Recipes
        this.shapeless(RecipeCategory.MISC, ModItems.PURIFYING_SOAP.get(), builder -> builder.requires(ModItems.ARCANE_CRYSTAL_DUST.get()).requires(ModItems.WAX.get()).requires(Items.SLIME_BALL).requires(Items.PRISMARINE_CRYSTALS).requires(ItemTags.SMALL_FLOWERS).unlockedBy("has_item", has(ModItems.WAX.get())));
        this.shapeless(RecipeCategory.MISC, ModItems.TEST_TUBE.get(), builder -> builder.requires(Items.GLASS_BOTTLE).requires(ModItems.RUNE.get()).unlockedBy("has_item", has(ModItems.RUNE.get())));
        this.shapeless(RecipeCategory.MISC, ModItems.DARK_RUNE.get(), 2, builder -> builder.requires(ModItems.RUNE.get()).requires(ModItems.RUNE.get()).requires(ModItems.CORRUPTI_DUST.get()).unlockedBy("has_rune", has(ModItems.RUNE.get())).unlockedBy("has_dust", has(ModItems.CORRUPTI_DUST.get())));

        //Smelting Recipes
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL_DUST.get(), 0.4F, 150).unlockedBy("has_item", has(ModItems.ARCANE_CRYSTAL.get())), "smelting/arcane_crystal_dust_from_smelting");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.RUNIC_STONES), RecipeCategory.MISC, ModItems.RUNE.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.RUNIC_STONES)), "smelting/rune_from_smelting");
        this.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.ARCANE_CRYSTAL_ORES), RecipeCategory.MISC, ModItems.ARCANE_CRYSTAL.get(), 1.0F, 200).unlockedBy("has_item", has(ModTags.Items.ARCANE_CRYSTAL_ORES)), "smelting/arcane_crystal_from_smelting");

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

        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ARCANE_CHISELED_DARKSTONE.get(), RecipePart.of(ModTags.Items.DEORUM_INGOTS), RecipePart.of(ModBlocks.DARKSTONE.get()), 8);

        this.storageRecipe(ModItems.DARK_NETHER_STAR.get(), ModBlocks.DARK_NETHER_STAR_BLOCK.get());
        this.storageRecipe(ModItems.OBSIDIANSTEEL_INGOT.get(), ModBlocks.OBSIDIANSTEEL_BLOCK.get(), "obsidian_ingot", null);
        this.storageRecipe(ModItems.DEORUM_INGOT.get(), ModBlocks.DEORUM_BLOCK.get(), "deorum_ingot", null);
        this.storageRecipe(ModItems.DEORUM_NUGGET.get(), ModItems.DEORUM_INGOT.get(), null, "deorum_ingot");
        this.storageRecipe(ModItems.STELLARITE_PIECE.get(), ModBlocks.STELLARITE_BLOCK.get());
        this.storageRecipe(ModItems.ARCANE_CRYSTAL.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get());
        this.storageRecipe(ModItems.CORRUPTED_ARCANE_CRYSTAL.get(), ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get());
        this.storageRecipe(ModItems.RUNE.get(), ModBlocks.RUNE_BLOCK.get());
        this.storageRecipe(ModItems.DARK_RUNE.get(), ModBlocks.DARK_RUNE_BLOCK.get());

        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEORUM_GLASS.get(), this.tag(ModTags.Items.DEORUM_INGOTS), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.DEORUM_GLASS_PANE.get(), this.item(ModBlocks.DEORUM_GLASS.get()));
        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUNIC_GLASS.get(), RecipePart.of(ModItems.RUNE.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.RUNIC_GLASS_PANE.get(), this.item(ModBlocks.RUNIC_GLASS.get()));
        this.surroundingItem(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_RUNIC_GLASS.get(), RecipePart.of(ModItems.DARK_RUNE.get()), RecipePart.of(Blocks.GLASS), 8);
        this.glassPane(ModBlocks.DARK_RUNIC_GLASS_PANE.get(), this.item(ModBlocks.DARK_RUNIC_GLASS.get()));

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

        this.woodenSlab(ModBlocks.FUNGYSS_SLAB.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.woodenSlab(ModBlocks.AURUM_SLAB.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.woodenSlab(ModBlocks.EDELWOOD_SLAB.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.woodenStairs(ModBlocks.FUNGYSS_STAIRS.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.woodenStairs(ModBlocks.AURUM_STAIRS.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.woodenStairs(ModBlocks.EDELWOOD_STAIRS.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.door(ModBlocks.DEORUM_DOOR.get(), this.tag(ModTags.Items.DEORUM_INGOTS));
        this.door(ModBlocks.FUNGYSS_DOOR.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.door(ModBlocks.AURUM_DOOR.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.door(ModBlocks.EDELWOOD_DOOR.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));
        this.door(ModBlocks.ARCANE_EDELWOOD_DOOR.get(), this.item(ModBlocks.ARCANE_EDELWOOD_PLANKS.get()));

        this.trapdoor(ModBlocks.DEORUM_TRAPDOOR.get(), this.tag(ModTags.Items.DEORUM_INGOTS));
        this.trapdoor(ModBlocks.FUNGYSS_TRAPDOOR.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.trapdoor(ModBlocks.AURUM_TRAPDOOR.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.trapdoor(ModBlocks.EDELWOOD_TRAPDOOR.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));
        this.trapdoor(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR.get(), this.item(ModBlocks.ARCANE_EDELWOOD_PLANKS.get()));

        this.fence(ModBlocks.FUNGYSS_FENCE.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.fence(ModBlocks.AURUM_FENCE.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.fence(ModBlocks.EDELWOOD_FENCE.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.fenceGate(ModBlocks.FUNGYSS_FENCE_GATE.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.fenceGate(ModBlocks.AURUM_FENCE_GATE.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.fenceGate(ModBlocks.EDELWOOD_FENCE_GATE.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        //TODO
//        this.sign(ModBlocks.FUNGYSS_SIGN.getFirst().get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
//        this.sign(ModBlocks.CHERRY_SIGN.getFirst().get(), this.item(ModBlocks.CHERRY_PLANKS.get()));
//        this.sign(ModBlocks.AURUM_SIGN.getFirst().get(), this.item(ModBlocks.AURUM_PLANKS.get()));
//        this.sign(ModBlocks.EDELWOOD_SIGN.getFirst().get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.pressurePlate(ModBlocks.DEORUM_PRESSURE_PLATE.get(), this.tag(ModTags.Items.DEORUM_INGOTS));
        this.pressurePlate(ModBlocks.FUNGYSS_PRESSURE_PLATE.get(), this.item(ModBlocks.FUNGYSS_PLANKS.get()));
        this.pressurePlate(ModBlocks.AURUM_PRESSURE_PLATE.get(), this.item(ModBlocks.AURUM_PLANKS.get()));
        this.pressurePlate(ModBlocks.EDELWOOD_PRESSURE_PLATE.get(), this.item(ModBlocks.EDELWOOD_PLANKS.get()));

        this.button(ModBlocks.FUNGYSS_BUTTON.get(), ModBlocks.FUNGYSS_PLANKS.get());
        this.button(ModBlocks.AURUM_BUTTON.get(), ModBlocks.AURUM_PLANKS.get());
        this.button(ModBlocks.EDELWOOD_BUTTON.get(), ModBlocks.EDELWOOD_PLANKS.get());

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
