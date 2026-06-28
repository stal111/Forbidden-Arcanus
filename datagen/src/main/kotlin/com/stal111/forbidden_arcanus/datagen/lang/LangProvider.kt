package com.stal111.forbidden_arcanus.datagen.lang

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.essence.EssenceType
import com.stal111.forbidden_arcanus.common.item.enchantment.BuiltInEnchantments
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModEntities
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.PackOutput
import net.minecraft.util.StringRepresentable
import net.minecraft.util.Util
import net.minecraft.world.item.DyeColor
import net.neoforged.neoforge.common.data.LanguageProvider
import java.util.*
import java.util.function.Consumer

class LangProvider(output: PackOutput) : LanguageProvider(output, ForbiddenArcanus.MOD_ID, "en_us") {
    override fun addTranslations() {
        add(Util.makeDescriptionId("itemGroup", ForbiddenArcanus.identifier("main")), "Forbidden & Arcanus")

        addModifier("eternal", "Eternal")
        addModifier("fiery", "Fiery")
        addModifier("magnetized", "Magnetized")
        addModifier("demolishing", "Demolishing")
        addModifier("aquatic", "Aquatic")
        addModifier("soulbound", "Soulbound")

        add("essence", EssenceType.AUREAL, "Aureal")
        add("essence", EssenceType.ECTOPLASM, "Ectoplasm")
        add("essence", EssenceType.BLOOD, "Blood")
        add("essence", EssenceType.EXPERIENCE, "Experience")

        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("hephaestus_forge")), "Hephaestus Forge")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("clibano")), "Clibano")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("clibano.slot.fuel")), "Place fuel here")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("clibano.slot.ectoplasm")), "Insert ectoplasm here")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("wand_desk")), "Wand Desk")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("wand_desk.slot.pommel")), "Change the pommel material")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("wand_desk.slot.transition")), "Change the transition material")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("wand_desk.slot.base")), "Change the base material")
        add(Util.makeDescriptionId("container", ForbiddenArcanus.identifier("wand_desk.create")), "Create Wand")

        addEntityType(ModEntities.LOST_SOUL, "Lost Soul")
        addEntityType(ModEntities.CORRUPT_LOST_SOUL, "Corrupt Lost Soul")
        addEntityType(ModEntities.ENCHANTED_LOST_SOUL, "Enchanted Lost Soul")
        addEntityType(ModEntities.DARK_TRADER, "Dark Trader")

        // add(ModEnchantments.AUREAL_RESERVOIR.get(), "Aureal Reservoir");
        add(Util.makeDescriptionId("enchantment", BuiltInEnchantments.SOUL_LOOTING.identifier()), "Soul Looting")
        add(Util.makeDescriptionId("enchantment", BuiltInEnchantments.ANCHORED.identifier()), "Anchored")

        // Blocks
        addBlock(ModBlocks.QUANTUM_CORE, "Quantum Core")
        addBlock(ModBlocks.QUANTUM_INJECTOR, "Quantum Injector")
        addBlock(ModBlocks.DARKSTONE, "Darkstone")
        addBlock(ModBlocks.DARKSTONE_SLAB, "Darkstone Slab")
        addBlock(ModBlocks.DARKSTONE_STAIRS, "Darkstone Stairs")
        addBlock(ModBlocks.DARKSTONE_WALL, "Darkstone Wall")
        addBlock(ModBlocks.POLISHED_DARKSTONE, "Polished Darkstone")
        addBlock(ModBlocks.POLISHED_DARKSTONE_SLAB, "Polished Darkstone Slab")
        addBlock(ModBlocks.POLISHED_DARKSTONE_STAIRS, "Polished Darkstone Stairs")
        addBlock(ModBlocks.POLISHED_DARKSTONE_WALL, "Polished Darkstone Wall")
        addBlock(ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE, "Polished Darkstone Pressure Plate")
        addBlock(ModBlocks.POLISHED_DARKSTONE_BUTTON, "Polished Darkstone Button")
        addBlock(ModBlocks.CHISELED_POLISHED_DARKSTONE, "Chiseled Polished Darkstone")
        addBlock(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE, "Gilded Chiseled Polished Darkstone")
        addBlock(ModBlocks.POLISHED_DARKSTONE_BRICKS, "Polished Darkstone Bricks")
        addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_SLAB, "Polished Darkstone Brick Slab")
        addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS, "Polished Darkstone Brick Stairs")
        addBlock(ModBlocks.POLISHED_DARKSTONE_BRICK_WALL, "Polished Darkstone Brick Wall")
        addBlock(ModBlocks.CRACKED_POLISHED_DARKSTONE_BRICKS, "Cracked Polished Darkstone Bricks")
        addBlock(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS, "Tiled Polished Darkstone Bricks")
        addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE, "Arcane Polished Darkstone")
        addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB, "Arcane Polished Darkstone Slab")
        addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS, "Arcane Polished Darkstone Stairs")
        addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_WALL, "Arcane Polished Darkstone Wall")
        addBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE, "Chiseled Arcane Polished Darkstone")
        addBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR, "Arcane Polished Darkstone Pillar")
        addBlock(ModBlocks.DARKSTONE_PEDESTAL, "Darkstone Pedestal")
        addBlock(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL, "Magnetized Darkstone Pedestal")
        addBlock(ModBlocks.MORTAR, "Mortar")
        addBlock(ModBlocks.CLIBANO_CORE, "Clibano Core")
        add("block", "hephaestus_forge", "Hephaestus Forge")
        addBlock(ModBlocks.ARCANE_CRYSTAL_ORE, "Arcane Crystal Ore")
        addBlock(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE, "Deepslate Arcane Crystal Ore")
        addBlock(ModBlocks.RUNIC_STONE, "Runic Stone")
        addBlock(ModBlocks.RUNIC_DEEPSLATE, "Runic Deepslate")
        addBlock(ModBlocks.RUNIC_DARKSTONE, "Runic Darkstone")
        addBlock(ModBlocks.ASTERITE_DEBRIS, "Asterite Debris")
        addBlock(ModBlocks.STELLA_ARCANUM, "Stella Arcanum")
        addBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK, "Arcane Crystal Block")
        addBlock(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK, "Corrupted Arcane Crystal Block")
        addBlock(ModBlocks.RUNE_BLOCK, "Rune Block")
        addBlock(ModBlocks.ASTERITE_BLOCK, "Block of Asterite")
        addBlock(ModBlocks.STELLARITE_BLOCK, "Stellarite Block")
        addBlock(ModBlocks.DEORUM_BLOCK, "Deorum Block")
        addBlock(ModBlocks.OBSIDIANSTEEL_BLOCK, "Obsidiansteel Block")
        addBlock(ModBlocks.ARCANE_CRYSTAL_OBELISK, "Arcane Crystal Obelisk")
        addBlock(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK, "Corrupted Arcane Crystal Obelisk")
        addBlock(ModBlocks.DEORUM_GLASS, "Deorum Glass")
        addBlock(ModBlocks.RUNIC_GLASS, "Runic Glass")
        addBlock(ModBlocks.DEORUM_GLASS_PANE, "Deorum Glass Pane")
        addBlock(ModBlocks.RUNIC_GLASS_PANE, "Runic Glass Pane")
        addBlock(ModBlocks.DEORUM_LANTERN, "Deorum Lantern")
        addBlock(ModBlocks.DEORUM_SOUL_LANTERN, "Deorum Soul Lantern")
        addBlock(ModBlocks.SOULLESS_SAND, "Soulless Sand")
        addBlock(ModBlocks.SOULLESS_SANDSTONE, "Soulless Sandstone")
        addBlock(ModBlocks.SOULLESS_SANDSTONE_SLAB, "Soulless Sandstone Slab")
        addBlock(ModBlocks.SOULLESS_SANDSTONE_STAIRS, "Soulless Sandstone Stairs")
        addBlock(ModBlocks.SOULLESS_SANDSTONE_WALL, "Soulless Sandstone Wall")
        addBlock(ModBlocks.CUT_SOULLESS_SANDSTONE, "Cut Soulless Sandstone")
        addBlock(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB, "Cut Soulless Sandstone Slab")
        addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE, "Polished Soulless Sandstone")
        addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB, "Polished Soulless Sandstone Slab")
        addBlock(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS, "Polished Soulless Sandstone Stairs")
        addBlock(ModBlocks.FUNGYSS, "Fungyss")
        addBlock(ModBlocks.AURUM_SAPLING, "Aurum Sapling")
        addBlock(ModBlocks.GROWING_EDELWOOD, "Growing Edelwood")
        addBlock(ModBlocks.FUNGYSS_BLOCK, "Fungyss Block")
        addBlock(ModBlocks.AURUM_LEAVES, "Aurum Leaves")
        addBlock(ModBlocks.NUGGETY_AURUM_LEAVES, "Nuggety Aurum Leaves")
        addBlock(ModBlocks.FUNGYSS_STEM, "Fungyss Stem")
        addBlock(ModBlocks.AURUM_LOG, "Aurum Log")
        addBlock(ModBlocks.EDELWOOD_LOG, "Edelwood Log")
        addBlock(ModBlocks.CARVED_EDELWOOD_LOG, "Carved Edelwood Log")
        addBlock(ModBlocks.EDELWOOD_BRANCH, "Edelwood Branch")
        addBlock(ModBlocks.STRIPPED_AURUM_LOG, "Stripped Aurum Log")
        addBlock(ModBlocks.FUNGYSS_HYPHAE, "Fungyss Hyphae")
        addBlock(ModBlocks.AURUM_WOOD, "Aurum Wood")
        addBlock(ModBlocks.STRIPPED_AURUM_WOOD, "Stripped Aurum Wood")
        addBlock(ModBlocks.FUNGYSS_PLANKS, "Fungyss Planks")
        addBlock(ModBlocks.AURUM_PLANKS, "Aurum Planks")
        addBlock(ModBlocks.EDELWOOD_PLANKS, "Edelwood Planks")
        addBlock(ModBlocks.ARCANE_EDELWOOD_PLANKS, "Arcane Edelwood Planks")
        addBlock(ModBlocks.FUNGYSS_SLAB, "Fungyss Slab")
        addBlock(ModBlocks.AURUM_SLAB, "Aurum Slab")
        addBlock(ModBlocks.EDELWOOD_SLAB, "Edelwood Slab")
        addBlock(ModBlocks.FUNGYSS_STAIRS, "Fungyss Stairs")
        addBlock(ModBlocks.AURUM_STAIRS, "Aurum Stairs")
        addBlock(ModBlocks.EDELWOOD_STAIRS, "Edelwood Stairs")
        addBlock(ModBlocks.FUNGYSS_FENCE, "Fungyss Fence")
        addBlock(ModBlocks.AURUM_FENCE, "Aurum Fence")
        addBlock(ModBlocks.EDELWOOD_FENCE, "Edelwood Fence")
        addBlock(ModBlocks.FUNGYSS_FENCE_GATE, "Fungyss Fence Gate")
        addBlock(ModBlocks.AURUM_FENCE_GATE, "Aurum Fence Gate")
        addBlock(ModBlocks.EDELWOOD_FENCE_GATE, "Edelwood Fence Gate")
        addBlock(ModBlocks.DEORUM_DOOR, "Deorum Door")
        addBlock(ModBlocks.FUNGYSS_DOOR, "Fungyss Door")
        addBlock(ModBlocks.AURUM_DOOR, "Aurum Door")
        addBlock(ModBlocks.EDELWOOD_DOOR, "Edelwood Door")
        addBlock(ModBlocks.ARCANE_EDELWOOD_DOOR, "Arcane Edelwood Door")
        addBlock(ModBlocks.DEORUM_TRAPDOOR, "Deorum Trapdoor")
        addBlock(ModBlocks.FUNGYSS_TRAPDOOR, "Fungyss Trapdoor")
        addBlock(ModBlocks.AURUM_TRAPDOOR, "Aurum Trapdoor")
        addBlock(ModBlocks.EDELWOOD_TRAPDOOR, "Edelwood Trapdoor")
        addBlock(ModBlocks.ARCANE_EDELWOOD_TRAPDOOR, "Arcane Edelwood Trapdoor")
        addBlock(ModBlocks.FUNGYSS_BUTTON, "Fungyss Button")
        addBlock(ModBlocks.AURUM_BUTTON, "Aurum Button")
        addBlock(ModBlocks.EDELWOOD_BUTTON, "Edelwood Button")
        addBlock(ModBlocks.FUNGYSS_PRESSURE_PLATE, "Fungyss Pressure Plate")
        addBlock(ModBlocks.AURUM_PRESSURE_PLATE, "Aurum Pressure Plate")
        addBlock(ModBlocks.EDELWOOD_PRESSURE_PLATE, "Edelwood Pressure Plate")
        addBlock(ModBlocks.EDELWOOD_LADDER, "Edelwood Ladder")
        addBlock(ModBlocks.ARCANE_DRAGON_EGG, "Arcane Dragon Egg")
        addBlock(ModBlocks.DEORUM_CHAIN, "Deorum Chain")
        addBlock(ModBlocks.YELLOW_ORCHID, "Yellow Orchid")
        addBlock(ModBlocks.MAGICAL_FARMLAND, "Magical Farmland")
        addBlock(ModBlocks.OBSIDIAN_SKULL.skull(), "Obsidian Skull")
        addBlock(ModBlocks.CRACKED_OBSIDIAN_SKULL.skull(), "Cracked Obsidian Skull")
        addBlock(ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.skull(), "Fragmented Obsidian Skull")
        addBlock(ModBlocks.FADING_OBSIDIAN_SKULL.skull(), "Fading Obsidian Skull")
        addBlock(ModBlocks.AUREALIC_OBSIDIAN_SKULL.skull(), "Aurealic Obsidian Skull")
        addBlock(ModBlocks.ETERNAL_OBSIDIAN_SKULL.skull(), "Eternal Obsidian Skull")
        addBlock(ModBlocks.UTREM_JAR, "Utrem Jar")

        add("block", "hephaestus_forge.tier", "Tier %s")
        add("block", "hephaestus_forge.tier.match_exact", "Requires exactly tier %s")
        add("block", "hephaestus_forge.tier.at_least", "Requires at least tier %s")
        add("block", "hephaestus_forge.slot_unlocked_at", "Unlocked at tier %s")

        // Items
        addItem(ModItems.QUANTUM_CATCHER, "Quantum Catcher")
        addItem(ModItems.BOSS_CATCHER, "Boss Catcher")
        addItem(ModItems.ARCANE_CRYSTAL, "Arcane Crystal")
        addItem(ModItems.CORRUPTED_ARCANE_CRYSTAL, "Corrupted Arcane Crystal")
        addItem(ModItems.RUNE, "Rune")
        addItem(ModItems.STELLARITE_PIECE, "Stellarite Piece")
        addItem(ModItems.CONDENSED_EXPERIENCE, "Condensed Experience")
        addItem(ModItems.DARK_NETHER_STAR, "Dark Nether Star")
        addItem(ModItems.DEORUM_INGOT, "Deorum Ingot")
        addItem(ModItems.ASTERITE_CHUNK, "Asterite Chunk")
        addItem(ModItems.ASTERITE_INGOT, "Asterite Ingot")
        addItem(ModItems.OBSIDIANSTEEL_INGOT, "Obsidiansteel Ingot")
        addItem(ModItems.DEORUM_NUGGET, "Deorum Nugget")
        addItem(ModItems.ARCANE_CRYSTAL_DUST, "Arcane Crystal Dust")
        addItem(ModItems.MUNDABITUR_DUST, "Mundabitur Dust")
        addItem(ModItems.CORRUPTI_DUST, "Corrupti Dust")
        addItem(ModItems.ARCANE_CRYSTAL_DUST_SPECK, "Arcane Crystal Dust Speck")
        addItem(ModItems.ARCANE_BONE_MEAL, "Arcane Bone Meal")
        addItem(ModItems.SOUL, "Soul")
        addItem(ModItems.CORRUPT_SOUL, "Corrupt Soul")
        addItem(ModItems.ENCHANTED_SOUL, "Enchanted Soul")
        addItem(ModItems.AUREAL_BOTTLE, "Aureal Bottle")
        addItem(ModItems.SPLASH_AUREAL_BOTTLE, "Splash Aureal Bottle")
        addItem(ModItems.ECTOPLASM_BOTTLE, "Ectoplasm Bottle")
        addItem(ModItems.TEST_TUBE, "Test Tube")
        addItem(ModItems.CONTAINMENT_CAPSULE, "Containment Capsule")
        addItem(ModItems.ENCAPSULATED_BLACK_HOLE, "Encapsulated Black Hole")
        addItem(ModItems.ETERNAL_STELLA, "Eternal Stella")
        addItem(ModItems.TERRASTOMP_PRISM, "Terrastomp Prism")
        addItem(ModItems.SEA_PRISM, "Sea Prism")
        addItem(ModItems.WHIRLWIND_PRISM, "Whirlwind Prism")
        addItem(ModItems.SMELTER_PRISM, "Smelter Prism")
        addItem(ModItems.FERROGNETIC_MIXTURE, "Ferrognetic Mixture")
        addItem(ModItems.SOUL_BINDING_CRYSTAL, "Soul Binding Crystal")
        addItem(ModItems.AUREAL_WARDSTONE, "Aureal Wardstone")
        addItem(ModItems.DARK_MATTER, "Dark Matter")
        addItem(ModItems.ENDER_PEARL_FRAGMENT, "Ender Pearl Fragment")
        addItem(ModItems.DRAGON_SCALE, "Dragon Scale")
        addItem(ModItems.SILVER_DRAGON_SCALE, "Silver Dragon Scale")
        addItem(ModItems.GOLDEN_DRAGON_SCALE, "Golden Dragon Scale")
        addItem(ModItems.AQUATIC_DRAGON_SCALE, "Aquatic Dragon Scale")
        addItem(ModItems.SPECTRAL_EYE_AMULET, "Spectral Eye Amulet")
        addItem(ModItems.BAT_WING, "Bat Wing")
        addItem(ModItems.BAT_SOUP, "Bat Soup")
        addItem(ModItems.TENTACLE, "Tentacle")
        addItem(ModItems.COOKED_TENTACLE, "Cooked Tentacle")
        addItem(ModItems.EDELWOOD_STICK, "Edelwood Stick")
        addItem(ModItems.WAX, "Wax")
        addItem(ModItems.SPAWNER_SCRAP, "Spawner Scrap")
        addItem(ModItems.AUREAL_TANK, "Aureal Tank")
        addItem(ModItems.EDELWOOD_BUCKET, "Edelwood Bucket")
        addItem(ModItems.EDELWOOD_WATER_BUCKET, "Edelwood Water Bucket")
        addItem(ModItems.EDELWOOD_LAVA_BUCKET, "Edelwood Lava Bucket")
        addItem(ModItems.EDELWOOD_MILK_BUCKET, "Edelwood Milk Bucket")
        addItem(ModItems.EDELWOOD_POWDER_SNOW_BUCKET, "Edelwood Powder Snow Bucket")
        addItem(ModItems.BOOM_ARROW, "Boom Arrow")
        addItem(ModItems.DRACO_ARCANUS_ARROW, "Draco Arcanus Arrow")
        addItem(ModItems.EDELWOOD_OIL, "Edelwood Oil")
        //        addItem(ModItems.AURUM_BOAT, "Aurum Boat");
//        addItem(ModItems.AURUM_CHEST_BOAT, "Aurum Chest Boat");
//        addItem(ModItems.EDELWOOD_BOAT, "Edelwood Boat");
//        addItem(ModItems.EDELWOOD_CHEST_BOAT, "Edelwood Chest Boat");
        addItem(ModItems.DRACO_ARCANUS_STAFF, "Draco Arcanus Staff")
        //        addItem(ModItems.DRACO_ARCANUS_SWORD, "Draco Arcanus Sword");
//        addItem(ModItems.DRACO_ARCANUS_PICKAXE, "Draco Arcanus Pickaxe");
//        addItem(ModItems.DRACO_ARCANUS_AXE, "Draco Arcanus Axe");
//        addItem(ModItems.DRACO_ARCANUS_SHOVEL, "Draco Arcanus Shovel");
//        addItem(ModItems.DRACO_ARCANUS_HOE, "Draco Arcanus Hoe");
        addItem(ModItems.DRACO_ARCANUS_SCEPTER, "Draco Arcanus Scepter")
        addItem(ModItems.OAK_WAND, "Oak Wand")
        addItem(ModItems.SPRUCE_WAND, "Spruce Wand")
        addItem(ModItems.BIRCH_WAND, "Birch Wand")
        addItem(ModItems.JUNGLE_WAND, "Jungle Wand")
        addItem(ModItems.ACACIA_WAND, "Acacia Wand")
        addItem(ModItems.DARK_OAK_WAND, "Dark Oak Wand")
        addItem(ModItems.MANGROVE_WAND, "Mangrove Wand")
        addItem(ModItems.CHERRY_WAND, "Cherry Wand")
        addItem(ModItems.PALE_OAK_WAND, "Pale Oak Wand")
        addItem(ModItems.BAMBOO_WAND, "Bamboo Wand")
        addItem(ModItems.CRIMSON_WAND, "Crimson Wand")
        addItem(ModItems.WARPED_WAND, "Warped Wand")
        addItem(ModItems.AURUM_WAND, "Aurum Wand")
        addItem(ModItems.EDELWOOD_WAND, "Edelwood Wand")
        addItem(ModItems.DRACO_ARCANUS_HELMET, "Draco Arcanus Helmet")
        addItem(ModItems.DRACO_ARCANUS_CHESTPLATE, "Draco Arcanus Chestplate")
        addItem(ModItems.DRACO_ARCANUS_LEGGINGS, "Draco Arcanus Leggings")
        addItem(ModItems.DRACO_ARCANUS_BOOTS, "Draco Arcanus Boots")
        addItem(ModItems.TYR_HELMET, "Tyr Helmet")
        addItem(ModItems.TYR_CHESTPLATE, "Tyr Chestplate")
        addItem(ModItems.TYR_LEGGINGS, "Tyr Leggings")
        addItem(ModItems.TYR_BOOTS, "Tyr Boots")
        //        addItem(ModItems.MORTEM_HELMET, "Mortem Helmet");
//        addItem(ModItems.MORTEM_CHESTPLATE, "Mortem Chestplate");
//        addItem(ModItems.MORTEM_LEGGINGS, "Mortem Leggings");
//        addItem(ModItems.MORTEM_BOOTS, "Mortem Boots");
        addItem(ModItems.ARTISAN_RELIC, "Artisan Relic")
        addItem(ModItems.CRESCENT_MOON, "Crescent Moon")
        addItem(ModItems.CRIMSON_STONE, "Crimson Stone")
        addItem(ModItems.SOUL_CRIMSON_STONE, "Soul Crimson Stone")
        addItem(ModItems.ELEMENTARIUM, "Elementarium")
        addItem(ModItems.DIVINE_PACT, "Divine Pact")
        addItem(ModItems.MALEDICTUS_PACT, "Maledictus Pact")
        addItem(ModItems.ECTO_BLASTER, "Soul Extractor")
        addItem(ModItems.BLACKSMITH_GAVEL_HEAD, "Blacksmith Gavel Head")
        addItem(ModItems.WOODEN_BLACKSMITH_GAVEL, "Wooden Blacksmith Gavel")
        addItem(ModItems.STONE_BLACKSMITH_GAVEL, "Stone Blacksmith Gavel")
        addItem(ModItems.IRON_BLACKSMITH_GAVEL, "Iron Blacksmith Gavel")
        addItem(ModItems.GOLDEN_BLACKSMITH_GAVEL, "Golden Blacksmith Gavel")
        addItem(ModItems.DIAMOND_BLACKSMITH_GAVEL, "Diamond Blacksmith Gavel")
        addItem(ModItems.NETHERITE_BLACKSMITH_GAVEL, "Netherite Blacksmith Gavel")
        //        addItem(ModItems.REINFORCED_DEORUM_BLACKSMITH_GAVEL, "Reinforced Deorum Blacksmith Gavel");
        addItem(ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE, "Smithing Template")

        for (color in DyeColor.entries) {
            addItem(ModItems.DYED_QUANTUM_CATCHERS.get(color)!!, COLOR_TO_STRING[color] + " Quantum Catcher")
        }

        add("item", "enhancer", "Enhancer Relic")
        add("item", "enhancer.hephaestus_forge_effect", "Hephaestus Forge effect:")
        add("item", "enhancer.clibano_effect", "Clibano effect:")
        add("item", "enhancer.artisan_relic.hephaestus_forge", "Saves you a lot of experience.")
        add("item", "enhancer.artisan_relic.clibano", "Allows you to create new alloys.")
        add(
            "item",
            "enhancer.crescent_moon.hephaestus_forge",
            "Reduces the required Aureal depending on the time of the day."
        )
        add("item", "enhancer.crimson_stone.hephaestus_forge", "Heavily decreases the amount of required ectoplasm.")
        add("item", "enhancer.crimson_stone.clibano", "Used ectoplasm will last longer. (Soon)")
        add(
            "item",
            "enhancer.soul_crimson_stone.hephaestus_forge",
            "Completely removes the essence requirement for one ritual."
        )
        add("item", "enhancer.elementarium.hephaestus_forge", "Allows the creation of elemental items.")
        add("item", "enhancer.divine_pact.hephaestus_forge", "Allows the creation of celestial items.")
        add("item", "enhancer.maledictus_pact.hephaestus_forge", "Allows the creation of cursed items.")

        add("item", "smithing_template.darkstone_upgrade.additions_slot_description", "Add a modifier item")
        add("item", "smithing_template.darkstone_upgrade.applies_to", "Equipment")
        add("item", "smithing_template.darkstone_upgrade.base_slot_description", "Add armor, weapon, or tool")
        add("item", "smithing_template.darkstone_upgrade.ingredients", "Modifier Item")

        add("item", "toggle_state", "(Right-Click to toggle)")
        add("item", "toggle_state.activated", "Activated")
        add("item", "toggle_state.deactivated", "Deactivated")
        add("item", "stored_entity", "Entity: %s")
        add("item", "stored_entity.with_name", "Entity: %s (%s)")
        add("item", "wand_material.info", "Can be used as %s material")
        add("item", "wand_material.damage", "+%s Damage")
        add("item", "wand_material.projectile_speed", "+%s Projectile Speed")
        add("item", "wand_material.accuracy", "+%s Accuracy")

        add("upgrade", "darkstone_upgrade", "Apply Item Modifier")

        add("tooltip", "essence.storage_format", "%s/%s")

        add("attribute.name.generic.aureal_regeneration", "Aureal Regeneration")

        add("jei", "category.clibano_melting", "Clibano Melting")
        add("jei", "category.clibano_alloying", "Clibano Alloying")
        add("jei", "category.hephaestus_smithing", "Hephaestus Smithing")
        add("jei", "category.hephaestus_forge_upgrading", "Hephaestus Forge Upgrading")
        add("jei", "hephaestus_smithing.required_essence", "Required %s: %s")

        //        PonderIndex.addPlugin(new ForbiddenArcanusPonderPlugin());
//        PonderIndex.getLangAccess().provideLang(ForbiddenArcanus.MOD_ID, this::add);
    }

    private fun addModifier(modifier: String, name: String) {
        add(Util.makeDescriptionId("modifier", ForbiddenArcanus.identifier(modifier)), name)
    }

    private fun <T : StringRepresentable> add(category: String, value: T, name: String) {
        add(Util.makeDescriptionId(category, ForbiddenArcanus.identifier(value.serializedName)), name)
    }

    fun add(category: String, path: String, name: String) {
        add(Util.makeDescriptionId(category, ForbiddenArcanus.identifier(path)), name)
    }

    companion object {
        val COLOR_TO_STRING: MutableMap<DyeColor, String> = Util.make(
            EnumMap(
                DyeColor::class.java
            ), Consumer {
                it[DyeColor.WHITE] = "White"
                it[DyeColor.ORANGE] = "Orange"
                it[DyeColor.MAGENTA] = "Magenta"
                it[DyeColor.LIGHT_BLUE] = "Light Blue"
                it[DyeColor.YELLOW] = "Yellow"
                it[DyeColor.LIME] = "Lime"
                it[DyeColor.PINK] = "Pink"
                it[DyeColor.GRAY] = "Gray"
                it[DyeColor.LIGHT_GRAY] = "Light Gray"
                it[DyeColor.CYAN] = "Cyan"
                it[DyeColor.PURPLE] = "Purple"
                it[DyeColor.BLUE] = "Blue"
                it[DyeColor.BROWN] = "Brown"
                it[DyeColor.GREEN] = "Green"
                it[DyeColor.RED] = "Red"
                it[DyeColor.BLACK] = "Black"
            })
    }
}
