package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturDustItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.ItemRegistryHelper;

/**
 * Mod Items <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModItems
 *
 * @author Valhelsia Team
 * @version 1.19 - 2.1.0
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems implements RegistryClass {

    public static final ItemRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper();

    public static final RegistryEntry<Item> ARCANE_CRYSTAL = HELPER.register("arcane_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CORRUPTED_ARCANE_CRYSTAL = HELPER.register("corrupted_arcane_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ARCANE_CRYSTAL_DUST = HELPER.register("arcane_crystal_dust", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DEORUM_INGOT = HELPER.register("deorum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DEORUM_NUGGET = HELPER.register("deorum_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> XPETRIFIED_ORB = HELPER.register("xpetrified_orb", () -> new XpetrifiedOrbItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> ETERNAL_STELLA = HELPER.register("eternal_stella", () -> new EternalStellaItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> ORB_OF_TEMPORARY_FLIGHT = HELPER.register("orb_of_temporary_flight", () -> new OrbOfTemporaryFlightItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryEntry<Item> MUNDABITUR_DUST = HELPER.register("mundabitur_dust", () -> new MundabiturDustItem(new Item.Properties()));
    public static final RegistryEntry<Item> CORRUPTI_DUST = HELPER.register("corrupti_dust", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DARK_MATTER = HELPER.register("dark_matter", () -> new DarkMatterItem(new Item.Properties()));
    public static final RegistryEntry<Item> OBSIDIAN_WITH_IRON = HELPER.register("obsidian_with_iron", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> OBSIDIAN_INGOT = HELPER.register("obsidian_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SOUL = HELPER.register("soul", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<DarkSoulItem> CORRUPT_SOUL = HELPER.register("corrupt_soul", () -> new DarkSoulItem(new Item.Properties()));
    public static final RegistryEntry<Item> ENCHANTED_SOUL = HELPER.register("enchanted_soul", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> PIXIE = HELPER.register("pixie", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> CORRUPTED_PIXIE = HELPER.register("corrupted_pixie", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> RUNE = HELPER.register("rune", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DARK_RUNE = HELPER.register("dark_rune", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CLOTH = HELPER.register("cloth", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ENDER_PEARL_FRAGMENT = HELPER.register("ender_pearl_fragment", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DRAGON_SCALE = HELPER.register("dragon_scale", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SILVER_DRAGON_SCALE = HELPER.register("silver_dragon_scale", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> GOLDEN_DRAGON_SCALE = HELPER.register("golden_dragon_scale", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AQUATIC_DRAGON_SCALE = HELPER.register("aquatic_dragon_scale", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ROTTEN_LEATHER = HELPER.register("rotten_leather", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SPECTRAL_EYE_AMULET = HELPER.register("spectral_eye_amulet", () -> new SpectralEyeAmuletItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryEntry<Item> BAT_WING = HELPER.register("bat_wing", () -> new Item(new Item.Properties().food(ModFoods.BAT_WING)));
    public static final RegistryEntry<Item> BAT_SOUP = HELPER.register("bat_soup", () -> new BowlFoodItem(new Item.Properties().stacksTo(1).food(ModFoods.BAT_SOUP)));
    public static final RegistryEntry<Item> TENTACLE = HELPER.register("tentacle", () -> new Item(new Item.Properties().food(ModFoods.TENTACLE)));
    public static final RegistryEntry<Item> COOKED_TENTACLE = HELPER.register("cooked_tentacle", () -> new Item(new Item.Properties().food(ModFoods.COOKED_TENTACLE)));
    public static final RegistryEntry<BlockItem> STRANGE_ROOT = HELPER.register("strange_root", () -> new StrangeRootItem(ModBlocks.STRANGE_ROOT.get(), new Item.Properties().food(ModFoods.STRANGE_ROOT)));
    public static final RegistryEntry<Item> GOLDEN_FEATHER = HELPER.register("golden_feather", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> EDELWOOD_STICK = HELPER.register("edelwood_stick", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> WAX = HELPER.register("wax", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<FerrogneticMixtureItem> FERROGNETIC_MIXTURE = HELPER.register("ferrognetic_mixture", () -> new FerrogneticMixtureItem(new Item.Properties()));
    public static final RegistryEntry<Item> SPAWNER_SCRAP = HELPER.register("spawner_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> QUANTUM_CATCHER = HELPER.register("quantum_catcher", () -> new QuantumCatcherItem(new Item.Properties()));
    public static final RegistryEntry<Item> ARTISAN_RELIC = HELPER.register("artisan_relic", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CRESCENT_MOON = HELPER.register("crescent_moon", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CRIMSON_STONE = HELPER.register("crimson_stone", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SOUL_CRIMSON_STONE = HELPER.register("soul_crimson_stone", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ELEMENTARIUM = HELPER.register("elementarium", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DIVINE_PACT = HELPER.register("divine_pact", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> MALEDICTUS_PACT = HELPER.register("maledictus_pact", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> SANITY_METER = HELPER.register("sanity_meter", () -> new SanityMeterItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<LensOfVeritatisItem> LENS_OF_VERITATIS = HELPER.register("lens_of_veritatis", () -> new LensOfVeritatisItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> PURIFYING_SOAP = HELPER.register("purifying_soap", () -> new PurifyingSoapItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> WET_PURIFYING_SOAP = HELPER.register("wet_purifying_soap", () -> new WetPurifyingSoapItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<ObsidianSkullItem> OBSIDIAN_SKULL = HELPER.register("obsidian_skull", () -> new ObsidianSkullItem(ModBlocks.OBSIDIAN_SKULL.get(), ModBlocks.OBSIDIAN_WALL_SKULL.get(), false, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryEntry<ObsidianSkullShieldItem> OBSIDIAN_SKULL_SHIELD = HELPER.register("obsidian_skull_shield", () -> new ObsidianSkullShieldItem(new Item.Properties().durability(1008).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryEntry<ObsidianSkullItem> ETERNAL_OBSIDIAN_SKULL = HELPER.register("eternal_obsidian_skull", () -> new ObsidianSkullItem(ModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get(), true, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryEntry<UtremJarItem> UTREM_JAR = HELPER.register("utrem_jar", () -> new UtremJarItem(ModBlocks.UTREM_JAR.get(), new Item.Properties()));
    public static final RegistryEntry<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", () -> new AurealBottleItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<SplashAurealBottleItem> SPLASH_AUREAL_BOTTLE = HELPER.register("splash_aureal_bottle", () -> new SplashAurealBottleItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> ARCANE_CRYSTAL_DUST_SPECK = HELPER.register("arcane_crystal_dust_speck", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<ArcaneBoneMealItem> ARCANE_BONE_MEAL = HELPER.register("arcane_bone_meal", () -> new ArcaneBoneMealItem(new Item.Properties()));
    public static final RegistryEntry<ZombieArmItem> ZOMBIE_ARM = HELPER.register("zombie_arm", () -> new ZombieArmItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<ZombieArmItem> SHINY_ZOMBIE_ARM = HELPER.register("shiny_zombie_arm", () -> new ZombieArmItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> TEST_TUBE = HELPER.register("test_tube", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<BloodTestTubeItem> BLOOD_TEST_TUBE = HELPER.register("blood_test_tube", () -> new BloodTestTubeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> BLACKSMITH_GAVEL_HEAD = HELPER.register("blacksmith_gavel_head", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<BlacksmithGavelItem> WOODEN_BLACKSMITH_GAVEL = HELPER.register("wooden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.WOOD, 7, -3.25F, 1, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BlacksmithGavelItem> STONE_BLACKSMITH_GAVEL = HELPER.register("stone_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.STONE, 7, -3.25F, 3, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BlacksmithGavelItem> GOLDEN_BLACKSMITH_GAVEL = HELPER.register("golden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.GOLD, 7, -3.25F, 2, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BlacksmithGavelItem> IRON_BLACKSMITH_GAVEL = HELPER.register("iron_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.IRON, 7, -3.25F, 10, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BlacksmithGavelItem> DIAMOND_BLACKSMITH_GAVEL = HELPER.register("diamond_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.DIAMOND, 7, -3.25F, 30, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BlacksmithGavelItem> NETHERITE_BLACKSMITH_GAVEL = HELPER.register("netherite_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.NETHERITE, 7, -3.25F, 60, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryEntry<BlacksmithGavelItem> REINFORCED_DEORUM_BLACKSMITH_GAVEL = HELPER.register("reinforced_deorum_blacksmith_gavel", () -> new BlacksmithGavelItem(ModTiers.REINFORCED_DEORUM, 7, -3.25F, 80, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> STELLARITE_PIECE = HELPER.register("stellarite_piece", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<SimpleFoiledItem> DARK_NETHER_STAR = HELPER.register("dark_nether_star", () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> TERRASTOMP_PRISM = HELPER.register("terrastomp_prism", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> SEA_PRISM = HELPER.register("sea_prism", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> WHIRLWIND_PRISM = HELPER.register("whirlwind_prism", () -> new WhirlwindPrismItem(new Item.Properties()));
    public static final RegistryEntry<SmelterPrismItem> SMELTER_PRISM = HELPER.register("smelter_prism", () -> new SmelterPrismItem(new Item.Properties().durability(200)));
    public static final RegistryEntry<EdelwoodBucketItem> EDELWOOD_BUCKET = HELPER.register("edelwood_bucket", () -> new EdelwoodBucketItem(() -> Fluids.EMPTY, new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<EdelwoodBucketItem> EDELWOOD_WATER_BUCKET = HELPER.register("edelwood_water_bucket", () -> new EdelwoodBucketItem(() -> Fluids.WATER, 4, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodBucketItem> EDELWOOD_LAVA_BUCKET = HELPER.register("edelwood_lava_bucket", () -> new EdelwoodBucketItem(() -> Fluids.LAVA, 3, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMilkBucketItem> EDELWOOD_MILK_BUCKET = HELPER.register("edelwood_milk_bucket", () -> new EdelwoodMilkBucketItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<SolidEdelwoodBucketItem> EDELWOOD_POWDER_SNOW_BUCKET = HELPER.register("edelwood_powder_snow_bucket", () -> new SolidEdelwoodBucketItem(Blocks.POWDER_SNOW, SoundEvents.POWDER_SNOW_BREAK, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_PUFFERFISH_BUCKET = HELPER.register("edelwood_pufferfish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.PUFFERFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_SALMON_BUCKET = HELPER.register("edelwood_salmon_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SALMON, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_COD_BUCKET = HELPER.register("edelwood_cod_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.COD, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_TROPICAL_FISH_BUCKET = HELPER.register("edelwood_tropical_fish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.TROPICAL_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_BAT_BUCKET = HELPER.register("edelwood_bat_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.BAT, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_SQUID_BUCKET = HELPER.register("edelwood_squid_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SQUID, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_GLOW_SQUID_BUCKET = HELPER.register("edelwood_glow_squid_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.GLOW_SQUID, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_BEE_BUCKET = HELPER.register("edelwood_bee_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.BEE, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_MAGMA_CUBE_BUCKET = HELPER.register("edelwood_magma_cube_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.MAGMA_CUBE, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_STRIDER_BUCKET = HELPER.register("edelwood_strider_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.STRIDER, () -> Fluids.LAVA, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_SLIME_BUCKET = HELPER.register("edelwood_slime_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SLIME, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_CHICKEN_BUCKET = HELPER.register("edelwood_chicken_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.CHICKEN, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_AXOLOTL_BUCKET = HELPER.register("edelwood_axolotl_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.AXOLOTL, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodMobBucketItem> EDELWOOD_ALLAY_BUCKET = HELPER.register("edelwood_allay_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.ALLAY, () -> Fluids.EMPTY, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<EdelwoodSoupBucketItem> EDELWOOD_MUSHROOM_STEW_BUCKET = HELPER.register("edelwood_mushroom_stew_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.MUSHROOM_STEW, new Item.Properties().stacksTo(1).food(Foods.MUSHROOM_STEW)));
    public static final RegistryEntry<EdelwoodSuspiciousStewBucketItem> EDELWOOD_SUSPICIOUS_STEW_BUCKET = HELPER.register("edelwood_suspicious_stew_bucket", () -> new EdelwoodSuspiciousStewBucketItem(new Item.Properties().stacksTo(1).food(Foods.SUSPICIOUS_STEW)));
    public static final RegistryEntry<EdelwoodSoupBucketItem> EDELWOOD_BEETROOT_SOUP_BUCKET = HELPER.register("edelwood_beetroot_soup_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.BEETROOT_SOUP, new Item.Properties().stacksTo(1).food(Foods.BEETROOT_SOUP)));
    public static final RegistryEntry<EdelwoodSoupBucketItem> EDELWOOD_BAT_SOUP_BUCKET = HELPER.register("edelwood_bat_soup_bucket", () -> new EdelwoodSoupBucketItem(ModItems.BAT_SOUP, new Item.Properties().stacksTo(1).food(ModFoods.BAT_SOUP)));
    public static final RegistryEntry<ModArrowItem> BOOM_ARROW = HELPER.register("boom_arrow", () -> new ModArrowItem(new Item.Properties()));
    public static final RegistryEntry<ModArrowItem> DRACO_ARCANUS_ARROW = HELPER.register("draco_arcanus_arrow", () -> new ModArrowItem(new Item.Properties()));
    public static final RegistryEntry<EdelwoodOilItem> EDELWOOD_OIL = HELPER.register("edelwood_oil", () -> new EdelwoodOilItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<ItemNameBlockItem> GOLDEN_ORCHID_SEEDS = HELPER.register("golden_orchid_seeds", () -> new ItemNameBlockItem(ModBlocks.GOLDEN_ORCHID.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DARKSTONE_UPGRADE_SMITHING_TEMPLATE = HELPER.register("darkstone_upgrade_smithing_template", SmithingTemplateConstants::createDarkstoneUpgradeTemplate);

    public static final RegistryEntry<BoatItem> AURUM_BOAT = HELPER.register("aurum_boat", () -> new ModBoatItem(false, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BoatItem> AURUM_CHEST_BOAT = HELPER.register("aurum_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BoatItem> EDELWOOD_BOAT = HELPER.register("edelwood_boat", () -> new ModBoatItem(false, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<BoatItem> EDELWOOD_CHEST_BOAT = HELPER.register("edelwood_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
    //public static final RegistryEntry<BoatItem> FUNGYSS_BOAT = HELPER.register("fungyss_boat", () -> new ModBoatItem(false, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));
    //public static final RegistryEntry<BoatItem> FUNGYSS_CHEST_BOAT = HELPER.register("fungyss_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));

    public static final RegistryEntry<Item> SOUL_EXTRACTOR = HELPER.register("soul_extractor", () -> new SoulExtractorItem(new Item.Properties().durability(128)));
    public static final RegistryEntry<Item> SLIMEC_PICKAXE = HELPER.register("slimec_pickaxe", () -> new SlimecPickaxeItem(ModTiers.SLIMEC, 1, -2.5F, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_STAFF = HELPER.register("draco_arcanus_staff", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> DRACO_ARCANUS_SWORD = HELPER.register("draco_arcanus_sword", () -> new SwordItem(ModTiers.DRACO_ARCANUS, 4, -2.2F, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_SHOVEL = HELPER.register("draco_arcanus_shovel", () -> new ShovelItem(ModTiers.DRACO_ARCANUS, 2.5F, -2.8F, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_PICKAXE = HELPER.register("draco_arcanus_pickaxe", () -> new PickaxeItem(ModTiers.DRACO_ARCANUS, 2, -2.6F, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_AXE = HELPER.register("draco_arcanus_axe", () -> new AxeItem(ModTiers.DRACO_ARCANUS, 6, -2.8F, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_HOE = HELPER.register("draco_arcanus_hoe", () -> new HoeItem(ModTiers.DRACO_ARCANUS, -4, 1, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_SCEPTER = HELPER.register("draco_arcanus_scepter", () -> new DracoArcanusScepterItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> REINFORCED_DEORUM_SWORD = HELPER.register("reinforced_deorum_sword", () -> new SwordItem(ModTiers.REINFORCED_DEORUM, 3, -2.4F, new Item.Properties()));
    public static final RegistryEntry<Item> REINFORCED_DEORUM_SHOVEL = HELPER.register("reinforced_deorum_shovel", () -> new ShovelItem(ModTiers.REINFORCED_DEORUM, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryEntry<Item> REINFORCED_DEORUM_PICKAXE = HELPER.register("reinforced_deorum_pickaxe", () -> new PickaxeItem(ModTiers.REINFORCED_DEORUM, 1, -2.8F, new Item.Properties()));
    public static final RegistryEntry<Item> REINFORCED_DEORUM_AXE = HELPER.register("reinforced_deorum_axe", () -> new AxeItem(ModTiers.REINFORCED_DEORUM, 5, -3.0F, new Item.Properties()));
    public static final RegistryEntry<Item> REINFORCED_DEORUM_HOE = HELPER.register("reinforced_deorum_hoe", () -> new HoeItem(ModTiers.REINFORCED_DEORUM, -3, 0, new Item.Properties()));
    public static final RegistryEntry<Item> MYSTICAL_DAGGER = HELPER.register("mystical_dagger", () -> new MysticalDaggerItem(ModTiers.MYSTICAL_DAGGER, 2.5F, -0.3F, new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> DRACO_ARCANUS_HELMET = HELPER.register("draco_arcanus_helmet", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_CHESTPLATE = HELPER.register("draco_arcanus_chestplate", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_LEGGINGS = HELPER.register("draco_arcanus_leggings", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryEntry<Item> DRACO_ARCANUS_BOOTS = HELPER.register("draco_arcanus_boots", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryEntry<Item> TYR_HELMET = HELPER.register("tyr_helmet", () -> new ArmorItem(ModArmorMaterials.TYR, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryEntry<Item> TYR_CHESTPLATE = HELPER.register("tyr_chestplate", () -> new ArmorItem(ModArmorMaterials.TYR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryEntry<Item> TYR_LEGGINGS = HELPER.register("tyr_leggings", () -> new ArmorItem(ModArmorMaterials.TYR, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryEntry<Item> TYR_BOOTS = HELPER.register("tyr_boots", () -> new ArmorItem(ModArmorMaterials.TYR, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryEntry<Item> MORTEM_HELMET = HELPER.register("mortem_helmet", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryEntry<Item> MORTEM_CHESTPLATE = HELPER.register("mortem_chestplate", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryEntry<Item> MORTEM_LEGGINGS = HELPER.register("mortem_leggings", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryEntry<Item> MORTEM_BOOTS = HELPER.register("mortem_boots", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static class Stacks {
        public static final ItemStack LENS_OF_VERITATIS = new ItemStack(ModItems.LENS_OF_VERITATIS.get());
        public static final ItemStack ORB_OF_TEMPORARY_FLIGHT = new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get());
        public static final ItemStack SANITY_METER = new ItemStack(ModItems.SANITY_METER.get());
        public static final ItemStack ARCANE_CRYSTAL_DUST_SPECK = new ItemStack(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
        public static final ItemStack OBSIDIAN_SKULL = new ItemStack(ModItems.OBSIDIAN_SKULL.get());
        public static final ItemStack ETERNAL_OBSIDIAN_SKULL = new ItemStack(ModItems.ETERNAL_OBSIDIAN_SKULL.get());
        public static final ItemStack OBSIDIAN_SKULL_SHIELD = new ItemStack(ModItems.OBSIDIAN_SKULL_SHIELD.get());
    }
}