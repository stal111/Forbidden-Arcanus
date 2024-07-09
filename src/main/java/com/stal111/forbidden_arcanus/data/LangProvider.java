package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 20.05.2024
 */
public class LangProvider extends LanguageProvider {

    public static final Map<DyeColor, String> COLOR_TO_STRING = Util.make(new EnumMap<>(DyeColor.class), map -> {
        map.put(DyeColor.WHITE, "White");
        map.put(DyeColor.ORANGE, "Orange");
        map.put(DyeColor.MAGENTA, "Magenta");
        map.put(DyeColor.LIGHT_BLUE, "Light Blue");
        map.put(DyeColor.YELLOW, "Yellow");
        map.put(DyeColor.LIME, "Lime");
        map.put(DyeColor.PINK, "Pink");
        map.put(DyeColor.GRAY, "Gray");
        map.put(DyeColor.LIGHT_GRAY, "Light Gray");
        map.put(DyeColor.CYAN, "Cyan");
        map.put(DyeColor.PURPLE, "Purple");
        map.put(DyeColor.BLUE, "Blue");
        map.put(DyeColor.BROWN, "Brown");
        map.put(DyeColor.GREEN, "Green");
        map.put(DyeColor.RED, "Red");
        map.put(DyeColor.BLACK, "Black");
    });

    public LangProvider(PackOutput output) {
        super(output, ForbiddenArcanus.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(Util.makeDescriptionId("itemGroup", ForbiddenArcanus.location("main")), "Forbidden & Arcanus");

        this.add(ModItemModifiers.ETERNAL, "Eternal");
        this.add(ModItemModifiers.FIERY, "Fiery");
        this.add(ModItemModifiers.MAGNETIZED, "Magnetized");
        this.add(ModItemModifiers.DEMOLISHING, "Demolishing");
        this.add(ModItemModifiers.AQUATIC, "Aquatic");
        this.add(ModItemModifiers.SOULBOUND, "Soulbound");

        this.add("essence", EssenceType.AUREAL, "Aureal");
        this.add("essence", EssenceType.SOULS, "Souls");
        this.add("essence", EssenceType.BLOOD, "Blood");
        this.add("essence", EssenceType.EXPERIENCE, "Experience");

        this.add(Util.makeDescriptionId("container", ForbiddenArcanus.location("hephaestus_forge")), "Hephaestus Forge");
        this.add(Util.makeDescriptionId("container", ForbiddenArcanus.location("clibano")), "Clibano");

        this.addEntityType(ModEntities.LOST_SOUL, "Lost Soul");
        this.addEntityType(ModEntities.DARK_TRADER, "Dark Trader");

       // this.add(ModEnchantments.AUREAL_RESERVOIR.get(), "Aureal Reservoir");

        // Blocks
        this.addBlock(ModBlocks.QUANTUM_CORE, "Quantum Core");
        this.addBlock(ModBlocks.QUANTUM_INJECTOR, "Quantum Injector");
        this.addBlock(ModBlocks.DARKSTONE, "Darkstone");
        this.addBlock(ModBlocks.DARKSTONE_SLAB, "Darkstone Slab");
        this.addBlock(ModBlocks.DARKSTONE_STAIRS, "Darkstone Stairs");
        this.addBlock(ModBlocks.DARKSTONE_WALL, "Darkstone Wall");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE, "Polished Darkstone");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_SLAB, "Polished Darkstone Slab");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_STAIRS, "Polished Darkstone Stairs");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_WALL, "Polished Darkstone Wall");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE, "Polished Darkstone Pressure Plate");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_BUTTON, "Polished Darkstone Button");
        this.addBlock(ModBlocks.CHISELED_POLISHED_DARKSTONE, "Chiseled Polished Darkstone");
        this.addBlock(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE, "Gilded Chiseled Polished Darkstone");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_BRICKS, "Polished Darkstone Bricks");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB, "Polished Darkstone Brick Slab");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS, "Polished Darkstone Brick Stairs");
        this.addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_WALL, "Polished Darkstone Brick Wall");
        this.addBlock(ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS, "Cracked Polished Darkstone Bricks");
        this.addBlock(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS, "Tiled Polished Darkstone Bricks");
        this.addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE, "Arcane Polished Darkstone");
        this.addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB, "Arcane Polished Darkstone Slab");
        this.addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS, "Arcane Polished Darkstone Stairs");
        this.addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL, "Arcane Polished Darkstone Wall");
        this.addBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE, "Chiseled Arcane Polished Darkstone");
        this.addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR, "Arcane Polished Darkstone Pillar");
        this.addBlock(ModBlocks.DARKSTONE_PEDESTAL, "Darkstone Pedestal");
        this.addBlock(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL, "Magnetized Darkstone Pedestal");
        this.addBlock(ModBlocks.CLIBANO_CORE, "Clibano Core");
        this.addBlock(ModBlocks.HEPHAESTUS_FORGE_TIER_1, "Hephaestus Forge");
        this.addBlock(ModBlocks.ARCANE_CRYSTAL_ORE, "Arcane Crystal Ore");
        this.addBlock(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE, "Deepslate Arcane Crystal Ore");
        this.addBlock(ModBlocks.RUNIC_STONE, "Runic Stone");
        this.addBlock(ModBlocks.RUNIC_DEEPSLATE, "Runic Deepslate");
        this.addBlock(ModBlocks.RUNIC_DARKSTONE, "Runic Darkstone");
        this.addBlock(ModBlocks.STELLA_ARCANUM, "Stella Arcanum");
        this.addBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK, "Arcane Crystal Block");
        this.addBlock(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK, "Corrupted Arcane Crystal Block");
        this.addBlock(ModBlocks.RUNE_BLOCK, "Rune Block");
        this.addBlock(ModBlocks.DARK_RUNE_BLOCK, "Dark Rune Block");
        this.addBlock(ModBlocks.STELLARITE_BLOCK, "Stellarite Block");
        this.addBlock(ModBlocks.DEORUM_BLOCK, "Deorum Block");
        this.addBlock(ModBlocks.OBSIDIANSTEEL_BLOCK, "Obsidiansteel Block");
        this.addBlock(ModBlocks.ARCANE_CRYSTAL_OBELISK, "Arcane Crystal Obelisk");
        this.addBlock(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK, "Corrupted Arcane Crystal Obelisk");
        this.addBlock(ModBlocks.DEORUM_GLASS, "Deorum Glass");
        this.addBlock(ModBlocks.RUNIC_GLASS, "Runic Glass");
        this.addBlock(ModBlocks.DARK_RUNIC_GLASS, "Dark Runic Glass");
        this.addBlock(ModBlocks.DEORUM_GLASS_PANE, "Deorum Glass Pane");
        this.addBlock(ModBlocks.RUNIC_GLASS_PANE, "Runic Glass Pane");
        this.addBlock(ModBlocks.DARK_RUNIC_GLASS_PANE, "Dark Runic Glass Pane");
        this.addBlock(ModBlocks.DEORUM_LANTERN, "Deorum Lantern");
        this.addBlock(ModBlocks.DEORUM_SOUL_LANTERN, "Deorum Soul Lantern");
        this.addBlock(ModBlocks.SOULLESS_SAND, "Soulless Sand");
        this.addBlock(ModBlocks.SOULLESS_SANDSTONE, "Soulless Sandstone");
        this.addBlock(ModBlocks.SOULLESS_SANDSTONE_SLAB, "Soulless Sandstone Slab");
        this.addBlock(ModBlocks.SOULLESS_SANDSTONE_STAIRS, "Soulless Sandstone Stairs");
        this.addBlock(ModBlocks.SOULLESS_SANDSTONE_WALL, "Soulless Sandstone Wall");
        this.addBlock(ModBlocks.CUT_SOULLESS_SANDSTONE, "Cut Soulless Sandstone");
        this.addBlock(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB, "Cut Soulless Sandstone Slab");
        this.addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE, "Polished Soulless Sandstone");
        this.addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB, "Polished Soulless Sandstone Slab");
        this.addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS, "Polished Soulless Sandstone Stairs");
        this.addBlock(ModBlocks.FUNGYSS, "Fungyss");
        this.addBlock(ModBlocks.AURUM_SAPLING, "Aurum Sapling");
        this.addBlock(ModBlocks.GROWING_EDELWOOD, "Growing Edelwood");
        this.addBlock(ModBlocks.FUNGYSS_BLOCK, "Fungyss Block");
        this.addBlock(ModBlocks.AURUM_LEAVES, "Aurum Leaves");
        this.addBlock(ModBlocks.NUGGETY_AURUM_LEAVES, "Nuggety Aurum Leaves");
        this.addBlock(ModBlocks.FUNGYSS_STEM, "Fungyss Stem");
        this.addBlock(ModBlocks.AURUM_LOG, "Aurum Log");
        this.addBlock(ModBlocks.EDELWOOD_LOG, "Edelwood Log");
        this.addBlock(ModBlocks.CARVED_EDELWOOD_LOG, "Carved Edelwood Log");
        this.addBlock(ModBlocks.EDELWOOD_BRANCH, "Edelwood Branch");
        this.addBlock(ModBlocks.STRIPPED_AURUM_LOG, "Stripped Aurum Log");
        this.addBlock(ModBlocks.FUNGYSS_HYPHAE, "Fungyss Hyphae");
        this.addBlock(ModBlocks.AURUM_WOOD, "Aurum Wood");
        this.addBlock(ModBlocks.STRIPPED_AURUM_WOOD, "Stripped Aurum Wood");
        this.addBlock(ModBlocks.FUNGYSS_PLANKS, "Fungyss Planks");
        this.addBlock(ModBlocks.AURUM_PLANKS, "Aurum Planks");
        this.addBlock(ModBlocks.EDELWOOD_PLANKS, "Edelwood Planks");
        this.addBlock(ModBlocks.ARCANE_EDELWOOD_PLANKS, "Arcane Edelwood Planks");
        this.addBlock(ModBlocks.FUNGYSS_SLAB, "Fungyss Slab");
        this.addBlock(ModBlocks.AURUM_SLAB, "Aurum Slab");
        this.addBlock(ModBlocks.EDELWOOD_SLAB, "Edelwood Slab");
        this.addBlock(ModBlocks.FUNGYSS_STAIRS, "Fungyss Stairs");
        this.addBlock(ModBlocks.AURUM_STAIRS, "Aurum Stairs");
        this.addBlock(ModBlocks.EDELWOOD_STAIRS, "Edelwood Stairs");
        this.addBlock(ModBlocks.FUNGYSS_FENCE, "Fungyss Fence");
        this.addBlock(ModBlocks.AURUM_FENCE, "Aurum Fence");
        this.addBlock(ModBlocks.EDELWOOD_FENCE, "Edelwood Fence");
        this.addBlock(ModBlocks.FUNGYSS_FENCE_GATE, "Fungyss Fence Gate");
        this.addBlock(ModBlocks.AURUM_FENCE_GATE, "Aurum Fence Gate");
        this.addBlock(ModBlocks.EDELWOOD_FENCE_GATE, "Edelwood Fence Gate");
        this.addBlock(ModBlocks.DEORUM_DOOR, "Deorum Door");
        this.addBlock(ModBlocks.FUNGYSS_DOOR, "Fungyss Door");
        this.addBlock(ModBlocks.AURUM_DOOR, "Aurum Door");
        this.addBlock(ModBlocks.EDELWOOD_DOOR, "Edelwood Door");
        this.addBlock(ModBlocks.ARCANE_EDELWOOD_DOOR, "Arcane Edelwood Door");
        this.addBlock(ModBlocks.DEORUM_TRAPDOOR, "Deorum Trapdoor");
        this.addBlock(ModBlocks.FUNGYSS_TRAPDOOR, "Fungyss Trapdoor");
        this.addBlock(ModBlocks.AURUM_TRAPDOOR, "Aurum Trapdoor");
        this.addBlock(ModBlocks.EDELWOOD_TRAPDOOR, "Edelwood Trapdoor");
        this.addBlock(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR, "Arcane Edelwood Trapdoor");
        this.addBlock(ModBlocks.FUNGYSS_BUTTON, "Fungyss Button");
        this.addBlock(ModBlocks.AURUM_BUTTON, "Aurum Button");
        this.addBlock(ModBlocks.EDELWOOD_BUTTON, "Edelwood Button");
        this.addBlock(ModBlocks.FUNGYSS_PRESSURE_PLATE, "Fungyss Pressure Plate");
        this.addBlock(ModBlocks.AURUM_PRESSURE_PLATE, "Aurum Pressure Plate");
        this.addBlock(ModBlocks.EDELWOOD_PRESSURE_PLATE, "Edelwood Pressure Plate");
        this.addBlock(ModBlocks.EDELWOOD_LADDER, "Edelwood Ladder");
        this.addBlock(ModBlocks.ARCANE_DRAGON_EGG, "Arcane Dragon Egg");
        this.addBlock(ModBlocks.NIPA, "Nipa");
        this.addBlock(ModBlocks.DEORUM_CHAIN, "Deorum Chain");
        this.addBlock(ModBlocks.YELLOW_ORCHID, "Yellow Orchid");
        this.addBlock(ModBlocks.MAGICAL_FARMLAND, "Magical Farmland");
        this.addBlock(ModBlocks.OBSIDIAN_SKULL.skull(), "Obsidian Skull");
        this.addBlock(ModBlocks.CRACKED_OBSIDIAN_SKULL.skull(), "Cracked Obsidian Skull");
        this.addBlock(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.skull(), "Fragmented Obsidian Skull");
        this.addBlock(ModBlocks.FADING_OBSIDIAN_SKULL.skull(), "Fading Obsidian Skull");
        this.addBlock(ModBlocks.AUREALIC_OBSIDIAN_SKULL.skull(), "Aurealic Obsidian Skull");
        this.addBlock(ModBlocks.ETERNAL_OBSIDIAN_SKULL.skull(), "Eternal Obsidian Skull");
        this.addBlock(ModBlocks.UTREM_JAR, "Utrem Jar");

        this.add("block", "hephaestus_forge.tier.match_exact", "Requires exactly tier %s");
        this.add("block", "hephaestus_forge.tier.at_least", "Requires at least tier %s");

        // Items
        this.addItem(ModItems.QUANTUM_CATCHER, "Quantum Catcher");
        this.addItem(ModItems.BOSS_CATCHER, "Boss Catcher");
        this.addItem(ModItems.ARCANE_CRYSTAL, "Arcane Crystal");
        this.addItem(ModItems.CORRUPTED_ARCANE_CRYSTAL, "Corrupted Arcane Crystal");
        this.addItem(ModItems.RUNE, "Rune");
        this.addItem(ModItems.DARK_RUNE, "Dark Rune");
        this.addItem(ModItems.STELLARITE_PIECE, "Stellarite Piece");
        this.addItem(ModItems.XPETRIFIED_ORB, "Xpetrified Orb");
        this.addItem(ModItems.DARK_NETHER_STAR, "Dark Nether Star");
        this.addItem(ModItems.DEORUM_INGOT, "Deorum Ingot");
        this.addItem(ModItems.OBSIDIANSTEEL_INGOT, "Obsidiansteel Ingot");
        this.addItem(ModItems.DEORUM_NUGGET, "Deorum Nugget");
        this.addItem(ModItems.ARCANE_CRYSTAL_DUST, "Arcane Crystal Dust");
        this.addItem(ModItems.MUNDABITUR_DUST, "Mundabitur Dust");
        this.addItem(ModItems.CORRUPTI_DUST, "Corrupti Dust");
        this.addItem(ModItems.ARCANE_CRYSTAL_DUST_SPECK, "Arcane Crystal Dust Speck");
        this.addItem(ModItems.ARCANE_BONE_MEAL, "Arcane Bone Meal");
        this.addItem(ModItems.SOUL, "Soul");
        this.addItem(ModItems.CORRUPT_SOUL, "Corrupt Soul");
        this.addItem(ModItems.ENCHANTED_SOUL, "Enchanted Soul");
        this.addItem(ModItems.AUREAL_BOTTLE, "Aureal Bottle");
        this.addItem(ModItems.SPLASH_AUREAL_BOTTLE, "Splash Aureal Bottle");
        this.addItem(ModItems.TEST_TUBE, "Test Tube");
        this.addItem(ModItems.ETERNAL_STELLA, "Eternal Stella");
        this.addItem(ModItems.TERRASTOMP_PRISM, "Terrastomp Prism");
        this.addItem(ModItems.SEA_PRISM, "Sea Prism");
        this.addItem(ModItems.WHIRLWIND_PRISM, "Whirlwind Prism");
        this.addItem(ModItems.SMELTER_PRISM, "Smelter Prism");
        this.addItem(ModItems.FERROGNETIC_MIXTURE, "Ferrognetic Mixture");
        this.addItem(ModItems.SOUL_BINDING_CRYSTAL, "Soul Binding Crystal");
        this.addItem(ModItems.DARK_MATTER, "Dark Matter");
        this.addItem(ModItems.ENDER_PEARL_FRAGMENT, "Ender Pearl Fragment");
        this.addItem(ModItems.DRAGON_SCALE, "Dragon Scale");
        this.addItem(ModItems.SILVER_DRAGON_SCALE, "Silver Dragon Scale");
        this.addItem(ModItems.GOLDEN_DRAGON_SCALE, "Golden Dragon Scale");
        this.addItem(ModItems.AQUATIC_DRAGON_SCALE, "Aquatic Dragon Scale");
        this.addItem(ModItems.SPECTRAL_EYE_AMULET, "Spectral Eye Amulet");
        this.addItem(ModItems.BAT_WING, "Bat Wing");
        this.addItem(ModItems.BAT_SOUP, "Bat Soup");
        this.addItem(ModItems.TENTACLE, "Tentacle");
        this.addItem(ModItems.COOKED_TENTACLE, "Cooked Tentacle");
        this.addItem(ModItems.EDELWOOD_STICK, "Edelwood Stick");
        this.addItem(ModItems.WAX, "Wax");
        this.addItem(ModItems.SPAWNER_SCRAP, "Spawner Scrap");
        this.addItem(ModItems.SANITY_METER, "Sanity Meter");
        this.addItem(ModItems.AUREAL_TANK, "Aureal Tank");
        this.addItem(ModItems.LENS_OF_VERITATIS, "Lens of Veritatis");
        this.addItem(ModItems.EDELWOOD_BUCKET, "Edelwood Bucket");
        this.addItem(ModItems.EDELWOOD_WATER_BUCKET, "Edelwood Water Bucket");
        this.addItem(ModItems.EDELWOOD_LAVA_BUCKET, "Edelwood Lava Bucket");
        this.addItem(ModItems.EDELWOOD_MILK_BUCKET, "Edelwood Milk Bucket");
        this.addItem(ModItems.BOOM_ARROW, "Boom Arrow");
        this.addItem(ModItems.DRACO_ARCANUS_ARROW, "Draco Arcanus Arrow");
        this.addItem(ModItems.EDELWOOD_OIL, "Edelwood Oil");
        this.addItem(ModItems.GOLDEN_ORCHID_SEEDS, "Golden Orchid Seeds");
        this.addItem(ModItems.AURUM_BOAT, "Aurum Boat");
        this.addItem(ModItems.AURUM_CHEST_BOAT, "Aurum Chest Boat");
        this.addItem(ModItems.EDELWOOD_BOAT, "Edelwood Boat");
        this.addItem(ModItems.EDELWOOD_CHEST_BOAT, "Edelwood Chest Boat");
        this.addItem(ModItems.DRACO_ARCANUS_STAFF, "Draco Arcanus Staff");
        this.addItem(ModItems.DRACO_ARCANUS_SWORD, "Draco Arcanus Sword");
        this.addItem(ModItems.DRACO_ARCANUS_PICKAXE, "Draco Arcanus Pickaxe");
        this.addItem(ModItems.DRACO_ARCANUS_AXE, "Draco Arcanus Axe");
        this.addItem(ModItems.DRACO_ARCANUS_SHOVEL, "Draco Arcanus Shovel");
        this.addItem(ModItems.DRACO_ARCANUS_HOE, "Draco Arcanus Hoe");
        this.addItem(ModItems.DRACO_ARCANUS_SCEPTER, "Draco Arcanus Scepter");
        this.addItem(ModItems.DRACO_ARCANUS_HELMET, "Draco Arcanus Helmet");
        this.addItem(ModItems.DRACO_ARCANUS_CHESTPLATE, "Draco Arcanus Chestplate");
        this.addItem(ModItems.DRACO_ARCANUS_LEGGINGS, "Draco Arcanus Leggings");
        this.addItem(ModItems.DRACO_ARCANUS_BOOTS, "Draco Arcanus Boots");
        this.addItem(ModItems.TYR_HELMET, "Tyr Helmet");
        this.addItem(ModItems.TYR_CHESTPLATE, "Tyr Chestplate");
        this.addItem(ModItems.TYR_LEGGINGS, "Tyr Leggings");
        this.addItem(ModItems.TYR_BOOTS, "Tyr Boots");
        this.addItem(ModItems.MORTEM_HELMET, "Mortem Helmet");
        this.addItem(ModItems.MORTEM_CHESTPLATE, "Mortem Chestplate");
        this.addItem(ModItems.MORTEM_LEGGINGS, "Mortem Leggings");
        this.addItem(ModItems.MORTEM_BOOTS, "Mortem Boots");
        this.addItem(ModItems.ARTISAN_RELIC, "Artisan Relic");
        this.addItem(ModItems.CRESCENT_MOON, "Crescent Moon");
        this.addItem(ModItems.CRIMSON_STONE, "Crimson Stone");
        this.addItem(ModItems.SOUL_CRIMSON_STONE, "Soul Crimson Stone");
        this.addItem(ModItems.ELEMENTARIUM, "Elementarium");
        this.addItem(ModItems.DIVINE_PACT, "Divine Pact");
        this.addItem(ModItems.MALEDICTUS_PACT, "Maledictus Pact");
        this.addItem(ModItems.SOUL_EXTRACTOR, "Soul Extractor");
        this.addItem(ModItems.BLACKSMITH_GAVEL_HEAD, "Blacksmith Gavel Head");
        this.addItem(ModItems.WOODEN_BLACKSMITH_GAVEL, "Wooden Blacksmith Gavel");
        this.addItem(ModItems.STONE_BLACKSMITH_GAVEL, "Stone Blacksmith Gavel");
        this.addItem(ModItems.IRON_BLACKSMITH_GAVEL, "Iron Blacksmith Gavel");
        this.addItem(ModItems.GOLDEN_BLACKSMITH_GAVEL, "Golden Blacksmith Gavel");
        this.addItem(ModItems.DIAMOND_BLACKSMITH_GAVEL, "Diamond Blacksmith Gavel");
        this.addItem(ModItems.NETHERITE_BLACKSMITH_GAVEL, "Netherite Blacksmith Gavel");
        this.addItem(ModItems.REINFORCED_DEORUM_BLACKSMITH_GAVEL, "Reinforced Deorum Blacksmith Gavel");
        this.addItem(ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE, "Smithing Template");

        for (DyeColor color : DyeColor.values()) {
            this.addItem(ModItems.DYED_QUANTUM_CATCHERS.get(color), COLOR_TO_STRING.get(color) + " Quantum Catcher");
        }

        this.add("item", "enhancer", "Enhancer Relic");
        this.add("item", "enhancer.hephaestus_forge_effect", "Hephaestus Forge effect:");
        this.add("item", "enhancer.clibano_effect", "Clibano effect:");
        this.add("item", "enhancer.artisan_relic.hephaestus_forge", "Saves you a lot of experience.");
        this.add("item", "enhancer.artisan_relic.clibano", "Allows you to create new alloys.");
        this.add("item", "enhancer.crescent_moon.hephaestus_forge", "Reduces the required Aureal depending on the time of the day.");
        this.add("item", "enhancer.crimson_stone.hephaestus_forge", "Heavily decreases the amount of required souls.");
        this.add("item", "enhancer.crimson_stone.clibano", "Used souls will last longer. (Soon)");
        this.add("item", "enhancer.soul_crimson_stone.hephaestus_forge", "Completely removes the essence requirement for one ritual.");
        this.add("item", "enhancer.elementarium.hephaestus_forge", "Allows the creation of elemental items.");
        this.add("item", "enhancer.divine_pact.hephaestus_forge", "Allows the creation of celestial items.");
        this.add("item", "enhancer.maledictus_pact.hephaestus_forge", "Allows the creation of cursed items.");

        this.add("item", "smithing_template.darkstone_upgrade.additions_slot_description", "Add a modifier item");
        this.add("item", "smithing_template.darkstone_upgrade.applies_to", "Equipment");
        this.add("item", "smithing_template.darkstone_upgrade.base_slot_description", "Add armor, weapon, or tool");
        this.add("item", "smithing_template.darkstone_upgrade.ingredients", "Modifier Item");

        this.add("upgrade", "darkstone_upgrade", "Apply Item Modifier");

        this.add("tooltip", "essence.storage_format", "%s / %s");
    }

    private void add(Supplier<? extends ItemModifier> modifier, String name) {
        this.add(modifier.get().getTranslationKey(), name);
    }

    private <T extends StringRepresentable> void add(String category, T value, String name) {
        this.add(Util.makeDescriptionId(category, ForbiddenArcanus.location(value.getSerializedName())), name);
    }

    public void add(String category, String path, String name) {
        this.add(Util.makeDescriptionId(category, ForbiddenArcanus.location(path)), name);
    }
}
