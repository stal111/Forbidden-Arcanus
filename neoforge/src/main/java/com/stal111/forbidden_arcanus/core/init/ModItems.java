package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.UtremJarBlock;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.common.item.bucket.BucketFamily;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityBucketItem;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityMilkBucketItem;
import com.stal111.forbidden_arcanus.common.item.bucket.SolidCapacityBucketItem;
import com.stal111.forbidden_arcanus.common.item.component.*;
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers;
import com.stal111.forbidden_arcanus.common.item.equipment.FAArmorMaterials;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturDustItem;
import com.stal111.forbidden_arcanus.common.item.wand.WandPart;
import com.stal111.forbidden_arcanus.common.item.wand.WandStats;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemEntrySet;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryHelper;

/**
 * Mod Items <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModItems
 *
 * @author Valhelsia Team
 * @version 1.19 - 2.1.0
 * @since 2021-01-26
 */
public class ModItems implements RegistryClass {

    public static final ItemRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper();

    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL = HELPER.register("arcane_crystal", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> CORRUPTED_ARCANE_CRYSTAL = HELPER.register("corrupted_arcane_crystal", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL_DUST = HELPER.register("arcane_crystal_dust", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> DEORUM_INGOT = HELPER.register("deorum_ingot", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> DEORUM_NUGGET = HELPER.register("deorum_nugget", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> XPETRIFIED_ORB = HELPER.register("xpetrified_orb", XpetrifiedOrbItem::new, () -> new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.EXPERIENCE, 91)));
    public static final ItemRegistryEntry<Item> ETERNAL_STELLA = HELPER.register("eternal_stella", Item::new, () -> new Item.Properties().stacksTo(1));
    public static final ItemRegistryEntry<Item> MUNDABITUR_DUST = HELPER.register("mundabitur_dust", MundabiturDustItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> CORRUPTI_DUST = HELPER.register("corrupti_dust", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> DARK_MATTER = HELPER.register("dark_matter", DarkMatterItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> OBSIDIANSTEEL_INGOT = HELPER.register("obsidiansteel_ingot", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> SOUL = HELPER.register("soul", Item::new, () -> new Item.Properties().component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.SOULS, 1)));
    public static final ItemRegistryEntry<DarkSoulItem> CORRUPT_SOUL = HELPER.register("corrupt_soul", DarkSoulItem::new, () -> new Item.Properties().component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.SOULS, 1)));
    public static final ItemRegistryEntry<Item> ENCHANTED_SOUL = HELPER.register("enchanted_soul", Item::new, () -> new Item.Properties().component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.SOULS, 10)));
    public static final ItemRegistryEntry<Item> RUNE = HELPER.register("rune", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> ENDER_PEARL_FRAGMENT = HELPER.register("ender_pearl_fragment", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> DRAGON_SCALE = HELPER.register("dragon_scale", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> SILVER_DRAGON_SCALE = HELPER.register("silver_dragon_scale", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> GOLDEN_DRAGON_SCALE = HELPER.register("golden_dragon_scale", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> AQUATIC_DRAGON_SCALE = HELPER.register("aquatic_dragon_scale", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> SPECTRAL_EYE_AMULET = HELPER.register("spectral_eye_amulet", SpectralEyeAmuletItem::new, () -> new Item.Properties().rarity(Rarity.RARE).stacksTo(1).component(ModDataComponents.TOGGLEABLE_STATE, ToggleableState.DEFAULT));
    public static final ItemRegistryEntry<Item> BAT_WING = HELPER.register("bat_wing", Item::new, () -> new Item.Properties().food(ModFoods.BAT_WING, FAConsumables.BAT_WING));
    public static final ItemRegistryEntry<Item> BAT_SOUP = HELPER.register("bat_soup", Item::new, () -> new Item.Properties().stacksTo(1).food(ModFoods.BAT_SOUP, FAConsumables.BAT_SOUP));
    public static final ItemRegistryEntry<Item> TENTACLE = HELPER.register("tentacle", Item::new, () -> new Item.Properties().food(ModFoods.TENTACLE));
    public static final ItemRegistryEntry<Item> COOKED_TENTACLE = HELPER.register("cooked_tentacle", Item::new, () -> new Item.Properties().food(ModFoods.COOKED_TENTACLE));
    public static final ItemRegistryEntry<Item> EDELWOOD_STICK = HELPER.register("edelwood_stick", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> WAX = HELPER.register("wax", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<FerrogneticMixtureItem> FERROGNETIC_MIXTURE = HELPER.register("ferrognetic_mixture", FerrogneticMixtureItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> SOUL_BINDING_CRYSTAL = HELPER.register("soul_binding_crystal", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> AUREAL_WARDSTONE = HELPER.register("aureal_wardstone", Item::new, () -> new Item.Properties().requiredFeatures(ForbiddenArcanus.PREVIEW));
    public static final ItemRegistryEntry<Item> SPAWNER_SCRAP = HELPER.register("spawner_scrap", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> QUANTUM_CATCHER = HELPER.register("quantum_catcher", (properties) -> new QuantumCatcherItem(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED, properties), () -> new Item.Properties().component(ModDataComponents.SHOWS_AUREAL_METER.get(), Unit.INSTANCE));
    public static final ItemEntrySet<QuantumCatcherItem, DyeColor> DYED_QUANTUM_CATCHERS = HELPER.registerColorEntrySet("quantum_catcher", (dyeColor, properties) -> new QuantumCatcherItem(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED, properties), color -> new Item.Properties().component(ModDataComponents.SHOWS_AUREAL_METER.get(), Unit.INSTANCE));
    public static final ItemRegistryEntry<Item> BOSS_CATCHER = HELPER.register("boss_catcher", (properties) -> new QuantumCatcherItem(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED, properties), () -> new Item.Properties().component(ModDataComponents.SHOWS_AUREAL_METER.get(), Unit.INSTANCE));
    public static final ItemRegistryEntry<Item> ARTISAN_RELIC = HELPER.register("artisan_relic", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.ARTISAN_RELIC));
    public static final ItemRegistryEntry<Item> CRESCENT_MOON = HELPER.register("crescent_moon", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.CRESCENT_MOON));
    public static final ItemRegistryEntry<Item> CRIMSON_STONE = HELPER.register("crimson_stone", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.CRIMSON_STONE));
    public static final ItemRegistryEntry<Item> SOUL_CRIMSON_STONE = HELPER.register("soul_crimson_stone", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.SOUL_CRIMSON_STONE));
    public static final ItemRegistryEntry<Item> ELEMENTARIUM = HELPER.register("elementarium", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.ELEMENTARIUM));
    public static final ItemRegistryEntry<Item> DIVINE_PACT = HELPER.register("divine_pact", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.DIVINE_PACT));
    public static final ItemRegistryEntry<Item> MALEDICTUS_PACT = HELPER.register("maledictus_pact", Item::new, () -> new Item.Properties().component(ModDataComponents.ENHANCER.value(), BuiltInEnhancers.MALEDICTUS_PACT));

    public static final ItemRegistryEntry<Item> AUREAL_TANK = HELPER.register("aureal_tank", AurealTankItem::new, () -> new Item.Properties().stacksTo(1).component(ModDataComponents.ESSENCE_STORAGE, EssenceStorage.EMPTY_AUREAL_TANK));

    public static final ItemRegistryEntry<BlockItem> HEPHAESTUS_FORGE_TIER_1 = HELPER.register("hephaestus_forge_tier_1", (properties) -> new BlockItem(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get(), properties), () -> new Item.Properties().overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID));
    public static final ItemRegistryEntry<BlockItem> HEPHAESTUS_FORGE_TIER_2 = HELPER.register("hephaestus_forge_tier_2", (properties) -> new BlockItem(ModBlocks.HEPHAESTUS_FORGE_TIER_2.get(), properties), () -> new Item.Properties().overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID));
    public static final ItemRegistryEntry<BlockItem> HEPHAESTUS_FORGE_TIER_3 = HELPER.register("hephaestus_forge_tier_3", (properties) -> new BlockItem(ModBlocks.HEPHAESTUS_FORGE_TIER_3.get(), properties), () -> new Item.Properties().overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID));
    public static final ItemRegistryEntry<BlockItem> HEPHAESTUS_FORGE_TIER_4 = HELPER.register("hephaestus_forge_tier_4", (properties) -> new BlockItem(ModBlocks.HEPHAESTUS_FORGE_TIER_4.get(), properties), () -> new Item.Properties().overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID));
    public static final ItemRegistryEntry<BlockItem> HEPHAESTUS_FORGE_TIER_5 = HELPER.register("hephaestus_forge_tier_5", (properties) -> new BlockItem(ModBlocks.HEPHAESTUS_FORGE_TIER_5.get(), properties), () -> new Item.Properties().overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID));

    public static final ItemRegistryEntry<BlockItem> UTREM_JAR = HELPER.register("utrem_jar", (properties) -> new BlockItem(ModBlocks.UTREM_JAR.get(), properties), () -> new Item.Properties().overrideDescription(UtremJarBlock.DESCRIPTION_ID));
    public static final ItemRegistryEntry<BlockItem> ESSENCE_UTREM_JAR = HELPER.register("essence_utrem_jar", (properties) -> new BlockItem(ModBlocks.ESSENCE_UTREM_JAR.get(), properties), () -> new Item.Properties().overrideDescription(UtremJarBlock.DESCRIPTION_ID).component(ModDataComponents.EMPTY_ITEM, ModItems.UTREM_JAR));

    public static final ItemRegistryEntry<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", AurealBottleItem::new, () -> new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.AUREAL, 35)).component(ModDataComponents.SHOWS_AUREAL_METER.get(), Unit.INSTANCE));
    public static final ItemRegistryEntry<SplashAurealBottleItem> SPLASH_AUREAL_BOTTLE = HELPER.register("splash_aureal_bottle", SplashAurealBottleItem::new, () -> new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_VALUE, EssenceValue.of(EssenceType.AUREAL, 30)).component(ModDataComponents.SHOWS_AUREAL_METER.get(), Unit.INSTANCE));
    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL_DUST_SPECK = HELPER.register("arcane_crystal_dust_speck", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<ArcaneBoneMealItem> ARCANE_BONE_MEAL = HELPER.register("arcane_bone_meal", ArcaneBoneMealItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> TEST_TUBE = HELPER.register("test_tube", Item::new, () -> new Item.Properties().stacksTo(1));
    public static final ItemRegistryEntry<Item> BLOOD_TEST_TUBE = HELPER.register("blood_test_tube", Item::new, () -> new Item.Properties().stacksTo(1).overrideDescription(TEST_TUBE.value().getDescriptionId()).component(ModDataComponents.ESSENCE_STORAGE, EssenceStorage.EMPTY_BLOOD_TEST_TUBE).component(ModDataComponents.EMPTY_ITEM, ModItems.TEST_TUBE));
    public static final ItemRegistryEntry<Item> BLACKSMITH_GAVEL_HEAD = HELPER.register("blacksmith_gavel_head", Item::new, () -> new Item.Properties().stacksTo(16));
    public static final ItemRegistryEntry<Item> WOODEN_BLACKSMITH_GAVEL = HELPER.register("wooden_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.WOOD, 1.0F, -2.8F).stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
    public static final ItemRegistryEntry<Item> STONE_BLACKSMITH_GAVEL = HELPER.register("stone_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.STONE, 1.0F, -2.8F).stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
    public static final ItemRegistryEntry<Item> GOLDEN_BLACKSMITH_GAVEL = HELPER.register("golden_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.GOLD, 1.0F, -2.8F).stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
    public static final ItemRegistryEntry<Item> IRON_BLACKSMITH_GAVEL = HELPER.register("iron_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.IRON, 1.0F, -2.8F).stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
    public static final ItemRegistryEntry<Item> DIAMOND_BLACKSMITH_GAVEL = HELPER.register("diamond_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.DIAMOND, 1.0F, -2.8F).stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
    public static final ItemRegistryEntry<Item> NETHERITE_BLACKSMITH_GAVEL = HELPER.register("netherite_blacksmith_gavel", Item::new, () -> new Item.Properties().pickaxe(ToolMaterial.NETHERITE, 1.0F, -2.8F).stacksTo(1).fireResistant().component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL));
//    public static final ItemRegistryEntry<PickaxeItem> REINFORCED_DEORUM_BLACKSMITH_GAVEL = HELPER.register("reinforced_deorum_blacksmith_gavel", () -> new PickaxeItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_STARTER, RitualStarter.BLACKSMITH_GAVEL)));
    public static final ItemRegistryEntry<Item> STELLARITE_PIECE = HELPER.register("stellarite_piece", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> DARK_NETHER_STAR = HELPER.register("dark_nether_star", Item::new, () -> new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final ItemRegistryEntry<Item> TERRASTOMP_PRISM = HELPER.register("terrastomp_prism", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> SEA_PRISM = HELPER.register("sea_prism", Item::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> WHIRLWIND_PRISM = HELPER.register("whirlwind_prism", WhirlwindPrismItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<SmelterPrismItem> SMELTER_PRISM = HELPER.register("smelter_prism", SmelterPrismItem::new, () -> new Item.Properties().durability(200));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_BUCKET = HELPER.register("edelwood_bucket", (properties) -> new CapacityBucketItem(Fluids.EMPTY, BucketFamily.EDELWOOD_BUCKET, properties), () -> new Item.Properties().stacksTo(16));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_WATER_BUCKET = HELPER.register("edelwood_water_bucket", (properties) -> new CapacityBucketItem(Fluids.WATER, BucketFamily.EDELWOOD_BUCKET, properties), () -> new Item.Properties().stacksTo(1).component(ModDataComponents.BUCKET_CAPACITY, 4).component(ModDataComponents.STORED_FLUID_AMOUNT, 1));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_LAVA_BUCKET = HELPER.register("edelwood_lava_bucket", (properties) -> new CapacityBucketItem(Fluids.LAVA, BucketFamily.EDELWOOD_BUCKET, properties), () -> new Item.Properties().stacksTo(1).component(ModDataComponents.BUCKET_CAPACITY, 3).component(ModDataComponents.STORED_FLUID_AMOUNT, 1));
    public static final ItemRegistryEntry<CapacityMilkBucketItem> EDELWOOD_MILK_BUCKET = HELPER.register("edelwood_milk_bucket", (properties) -> new CapacityMilkBucketItem(BucketFamily.EDELWOOD_BUCKET, properties), () -> new Item.Properties().stacksTo(1).component(ModDataComponents.BUCKET_CAPACITY, 4).component(ModDataComponents.STORED_FLUID_AMOUNT, 1));
    public static final ItemRegistryEntry<SolidCapacityBucketItem> EDELWOOD_POWDER_SNOW_BUCKET = HELPER.register("edelwood_powder_snow_bucket", (properties) -> new SolidCapacityBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, BucketFamily.EDELWOOD_BUCKET, properties), () -> new Item.Properties().stacksTo(1).component(ModDataComponents.BUCKET_CAPACITY, 3).component(ModDataComponents.STORED_FLUID_AMOUNT, 1));
    public static final ItemRegistryEntry<ModArrowItem> BOOM_ARROW = HELPER.register("boom_arrow", ModArrowItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<ModArrowItem> DRACO_ARCANUS_ARROW = HELPER.register("draco_arcanus_arrow", ModArrowItem::new, Item.Properties::new);
    public static final ItemRegistryEntry<Item> EDELWOOD_OIL = HELPER.register("edelwood_oil", Item::new, () -> new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
    public static final ItemRegistryEntry<Item> APPLY_MODIFIER_SMITHING_TEMPLATE = HELPER.register("apply_modifier_smithing_template", SmithingTemplateConstants::createApplyModifierTemplate, () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final ItemRegistryEntry<Item> OMEGA_ARCOIN = HELPER.register("omega_arcoin", Item::new, Item.Properties::new);
    //TODO: boats
//    public static final ItemRegistryEntry<BoatItem> AURUM_BOAT = HELPER.register("aurum_boat", () -> new ModBoatItem(false, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
//    public static final ItemRegistryEntry<BoatItem> AURUM_CHEST_BOAT = HELPER.register("aurum_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
//    public static final ItemRegistryEntry<BoatItem> EDELWOOD_BOAT = HELPER.register("edelwood_boat", () -> new ModBoatItem(false, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
//    public static final ItemRegistryEntry<BoatItem> EDELWOOD_CHEST_BOAT = HELPER.register("edelwood_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
    //public static final ItemRegistryEntry<BoatItem> FUNGYSS_BOAT = HELPER.register("fungyss_boat", () -> new ModBoatItem(false, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));
    //public static final ItemRegistryEntry<BoatItem> FUNGYSS_CHEST_BOAT = HELPER.register("fungyss_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));

    public static final ItemRegistryEntry<Item> SOUL_EXTRACTOR = HELPER.register("soul_extractor", SoulExtractorItem::new, () -> new Item.Properties().durability(128));
    public static final ItemRegistryEntry<MagicWandItem> MAGIC_WAND = HELPER.register("magic_wand", MagicWandItem::new, () -> new Item.Properties().stacksTo(1).component(ModDataComponents.SHOWS_AUREAL_METER, Unit.INSTANCE).component(ModDataComponents.AUREAL_COST, new AurealCost(5)).component(ModDataComponents.WAND_PARTS, new WandParts(new WandPart(Component.literal("Aurum Wood"), new WandStats(20, 0, 0)), new WandPart(Component.literal("Deorum"), new WandStats(0, 10, 0)), new WandPart(Component.literal("Arcane Crystal"), new WandStats(0, 0, 100)))));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_STAFF = HELPER.register("draco_arcanus_staff", Item::new, () -> new Item.Properties().stacksTo(1));
//    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SWORD = HELPER.register("draco_arcanus_sword", () -> new SwordItem(FAToolMaterials.DRACO_ARCANUS, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SHOVEL = HELPER.register("draco_arcanus_shovel", () -> new ShovelItem(FAToolMaterials.DRACO_ARCANUS, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_PICKAXE = HELPER.register("draco_arcanus_pickaxe", () -> new PickaxeItem(FAToolMaterials.DRACO_ARCANUS, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_AXE = HELPER.register("draco_arcanus_axe", () -> new AxeItem(FAToolMaterials.DRACO_ARCANUS, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_HOE = HELPER.register("draco_arcanus_hoe", () -> new HoeItem(FAToolMaterials.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SCEPTER = HELPER.register("draco_arcanus_scepter", DracoArcanusScepterItem::new, () -> new Item.Properties().stacksTo(1));
//    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_SWORD = HELPER.register("reinforced_deorum_sword", () -> new SwordItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_SHOVEL = HELPER.register("reinforced_deorum_shovel", () -> new ShovelItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_PICKAXE = HELPER.register("reinforced_deorum_pickaxe", () -> new PickaxeItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_AXE = HELPER.register("reinforced_deorum_axe", () -> new AxeItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_HOE = HELPER.register("reinforced_deorum_hoe", () -> new HoeItem(FAToolMaterials.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_HELMET = HELPER.register("draco_arcanus_helmet", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.DRACO_ARCANUS, ArmorType.HELMET));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_CHESTPLATE = HELPER.register("draco_arcanus_chestplate", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.DRACO_ARCANUS, ArmorType.CHESTPLATE));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_LEGGINGS = HELPER.register("draco_arcanus_leggings", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.DRACO_ARCANUS, ArmorType.LEGGINGS));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_BOOTS = HELPER.register("draco_arcanus_boots", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.DRACO_ARCANUS, ArmorType.BOOTS));
    public static final ItemRegistryEntry<Item> TYR_HELMET = HELPER.register("tyr_helmet", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.TYR, ArmorType.HELMET));
    public static final ItemRegistryEntry<Item> TYR_CHESTPLATE = HELPER.register("tyr_chestplate", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.TYR, ArmorType.CHESTPLATE));
    public static final ItemRegistryEntry<Item> TYR_LEGGINGS = HELPER.register("tyr_leggings", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.TYR, ArmorType.LEGGINGS));
    public static final ItemRegistryEntry<Item> TYR_BOOTS = HELPER.register("tyr_boots", Item::new, () -> new Item.Properties().humanoidArmor(FAArmorMaterials.TYR, ArmorType.BOOTS));
//    public static final ItemRegistryEntry<Item> MORTEM_HELMET = HELPER.register("mortem_helmet", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorType.HELMET, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> MORTEM_CHESTPLATE = HELPER.register("mortem_chestplate", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorType.CHESTPLATE, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> MORTEM_LEGGINGS = HELPER.register("mortem_leggings", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorType.LEGGINGS, new Item.Properties()));
//    public static final ItemRegistryEntry<Item> MORTEM_BOOTS = HELPER.register("mortem_boots", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorType.BOOTS, new Item.Properties()));

}
