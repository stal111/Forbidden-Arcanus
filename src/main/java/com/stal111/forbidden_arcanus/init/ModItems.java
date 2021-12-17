package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.block.EternalObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.item.block.StrangeRootItem;
import com.stal111.forbidden_arcanus.item.block.UtremJarItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper;

/**
 * Mod Items <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.NewModItems
 *
 * @author Valhelsia Team
 * @version 16.2.0
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final ItemRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getItemHelper();

    public static final RegistryObject<Item> ARCANE_CRYSTAL = HELPER.register("arcane_crystal", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_CRYSTAL_DUST = HELPER.register("arcane_crystal_dust", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = HELPER.register("arcane_gold_ingot", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = HELPER.register("arcane_gold_nugget", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> XPETRIFIED_ORB = HELPER.register("xpetrified_orb", () -> new XpetrifiedOrbItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<Item> ETERNAL_STELLA = HELPER.register("eternal_stella", () -> new EternalStellaItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> ORB_OF_TEMPORARY_FLIGHT = HELPER.register("orb_of_temporary_flight", () -> new OrbOfTemporaryFlightItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> MUNDABITUR_DUST = HELPER.register("mundabitur_dust", () -> new MundabiturDustItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> CORRUPTI_DUST = HELPER.register("corrupti_dust", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DARK_MATTER = HELPER.register("dark_matter", () -> new DarkMatterItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> OBSIDIAN_WITH_IRON = HELPER.register("obsidian_with_iron", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> OBSIDIAN_INGOT = HELPER.register("obsidian_ingot", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> SOUL = HELPER.register("soul", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DARK_SOUL = HELPER.register("dark_soul", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> PIXIE = HELPER.register("pixie", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> CORRUPTED_PIXIE = HELPER.register("corrupted_pixie", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> RUNE = HELPER.register("rune", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DARK_RUNE = HELPER.register("dark_rune", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> CLOTH = HELPER.register("cloth", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> CHERRY_PEACH = HELPER.register("cherry_peach", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).food(ModFoods.CHERRY_PEACH)));
    public static final RegistryObject<Item> ENDER_PEARL_FRAGMENT = HELPER.register("ender_pearl_fragment", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRAGON_SCALE = HELPER.register("dragon_scale", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> SILVER_DRAGON_SCALE = HELPER.register("silver_dragon_scale", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> GOLDEN_DRAGON_SCALE = HELPER.register("golden_dragon_scale", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> AQUATIC_DRAGON_SCALE = HELPER.register("aquatic_dragon_scale", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ROTTEN_LEATHER = HELPER.register("rotten_leather", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> SPECTRAL_EYE_AMULET = HELPER.register("spectral_eye_amulet", () -> new SpectralEyeAmuletItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> SEED_BULLET = HELPER.register("seed_bullet", () -> new SeedBulletItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<Item> BAT_WING = HELPER.register("bat_wing", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).food(ModFoods.BAT_WING)));
    public static final RegistryObject<Item> BAT_SOUP = HELPER.register("bat_soup", () -> new BowlFoodItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(ModFoods.BAT_SOUP)));
    public static final RegistryObject<Item> TENTACLE = HELPER.register("tentacle", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).food(ModFoods.TENTACLE)));
    public static final RegistryObject<Item> COOKED_TENTACLE = HELPER.register("cooked_tentacle", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).food(ModFoods.COOKED_TENTACLE)));
    public static final RegistryObject<Item> STRANGE_ROOT = HELPER.register("strange_root", () -> new StrangeRootItem(ModBlocks.STRANGE_ROOT.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).food(ModFoods.STRANGE_ROOT)));
    public static final RegistryObject<Item> GOLDEN_FEATHER = HELPER.register("golden_feather", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> EDELWOOD_STICK = HELPER.register("edelwood_stick", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> WAX = HELPER.register("wax", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> SPAWNER_SCRAP = HELPER.register("spawner_scrap", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> QUANTUM_CATCHER = HELPER.register("quantum_catcher", () -> new QuantumCatcherItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));

    public static final RegistryObject<Item> SANITY_METER = HELPER.register("sanity_meter", () -> new SanityMeterItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> LENS_OF_VERITATIS = HELPER.register("lens_of_veritatis", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> PURIFYING_SOAP = HELPER.register("purifying_soap", () -> new PurifyingSoapItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> WET_PURIFYING_SOAP = HELPER.register("wet_purifying_soap", () -> new WetPurifyingSoapItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<ObsidianSkullItem> OBSIDIAN_SKULL = HELPER.register("obsidian_skull", () -> new ObsidianSkullItem(ModBlocks.OBSIDIAN_SKULL.get(), ModBlocks.OBSIDIAN_WALL_SKULL.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<ObsidianSkullShieldItem> OBSIDIAN_SKULL_SHIELD = HELPER.register("obsidian_skull_shield", () -> new ObsidianSkullShieldItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).durability(1008).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<EternalObsidianSkullItem> ETERNAL_OBSIDIAN_SKULL = HELPER.register("eternal_obsidian_skull", () -> new EternalObsidianSkullItem(ModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<UtremJarItem> UTREM_JAR = HELPER.register("utrem_jar", () -> new UtremJarItem(ModBlocks.UTREM_JAR.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", () -> new AurealBottleItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<Item> ARCANE_CRYSTAL_DUST_SPECK = HELPER.register("arcane_crystal_dust_speck", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<ArcaneBoneMealItem> ARCANE_BONE_MEAL = HELPER.register("arcane_bone_meal", () -> new ArcaneBoneMealItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<ZombieArmItem> ZOMBIE_ARM = HELPER.register("zombie_arm", () -> new ZombieArmItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<ZombieArmItem> SHINY_ZOMBIE_ARM = HELPER.register("shiny_zombie_arm", () -> new ZombieArmItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> TEST_TUBE = HELPER.register("test_tube", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<BloodTestTubeItem> BLOOD_TEST_TUBE = HELPER.register("blood_test_tube", () -> new BloodTestTubeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BLACKSMITH_GAVEL_HEAD = HELPER.register("blacksmith_gavel_head", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<BlacksmithGavelItem> WOODEN_BLACKSMITH_GAVEL = HELPER.register("wooden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.WOOD, 7, -3.25F, 1, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> STONE_BLACKSMITH_GAVEL = HELPER.register("stone_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.STONE, 7, -3.25F, 3, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> GOLDEN_BLACKSMITH_GAVEL = HELPER.register("golden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.GOLD, 7, -3.25F, 2, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> IRON_BLACKSMITH_GAVEL = HELPER.register("iron_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.IRON, 7, -3.25F, 10, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> DIAMOND_BLACKSMITH_GAVEL = HELPER.register("diamond_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.DIAMOND, 7, -3.25F, 30, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> NETHERITE_BLACKSMITH_GAVEL = HELPER.register("netherite_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.NETHERITE, 7, -3.25F, 60, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> ARCANE_GOLDEN_BLACKSMITH_GAVEL = HELPER.register("arcane_golden_blacksmith_gavel", () -> new BlacksmithGavelItem(ModTiers.ARCANE_GOLDEN, 7, -3.25F, 15, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<BlacksmithGavelItem> REINFORCED_ARCANE_GOLDEN_BLACKSMITH_GAVEL = HELPER.register("reinforced_arcane_golden_blacksmith_gavel", () -> new BlacksmithGavelItem(ModTiers.REINFORCED_ARCANE_GOLDEN, 7, -3.25F, 80, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> STELLARITE_PIECE = HELPER.register("stellarite_piece", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<SimpleFoiledItem> DARK_NETHER_STAR = HELPER.register("dark_nether_star", () -> new SimpleFoiledItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SMELTER_PRISM = HELPER.register("smelter_prism", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_BUCKET = HELPER.register("edelwood_bucket", () -> new EdelwoodBucketItem(() -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_WATER_BUCKET = HELPER.register("edelwood_water_bucket", () -> new EdelwoodBucketItem(() -> Fluids.WATER, 4, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodBucketItem> EDELWOOD_LAVA_BUCKET = HELPER.register("edelwood_lava_bucket", () -> new EdelwoodBucketItem(() -> Fluids.LAVA, 3, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMilkBucketItem> EDELWOOD_MILK_BUCKET = HELPER.register("edelwood_milk_bucket", () -> new EdelwoodMilkBucketItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<SolidEdelwoodBucketItem> EDELWOOD_POWDER_SNOW_BUCKET = HELPER.register("edelwood_powder_snow_bucket", () -> new SolidEdelwoodBucketItem(Blocks.POWDER_SNOW, SoundEvents.POWDER_SNOW_BREAK, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_PUFFERFISH_BUCKET = HELPER.register("edelwood_pufferfish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.PUFFERFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SALMON_BUCKET = HELPER.register("edelwood_salmon_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SALMON, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_COD_BUCKET = HELPER.register("edelwood_cod_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.COD, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_TROPICAL_FISH_BUCKET = HELPER.register("edelwood_tropical_fish_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.TROPICAL_FISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_BAT_BUCKET = HELPER.register("edelwood_bat_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.BAT, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SQUID_BUCKET = HELPER.register("edelwood_squid_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SQUID, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_MAGMA_CUBE_BUCKET = HELPER.register("edelwood_magma_cube_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.MAGMA_CUBE, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_SLIME_BUCKET = HELPER.register("edelwood_slime_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.SLIME, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_CHICKEN_BUCKET = HELPER.register("edelwood_chicken_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.CHICKEN, () -> Fluids.EMPTY, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodMobBucketItem> EDELWOOD_AXOLOTL_BUCKET = HELPER.register("edelwood_axolotl_bucket", () -> new EdelwoodMobBucketItem(() -> EntityType.AXOLOTL, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_AXOLOTL, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_MUSHROOM_STEW_BUCKET = HELPER.register("edelwood_mushroom_stew_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.MUSHROOM_STEW, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.MUSHROOM_STEW)));
    public static final RegistryObject<EdelwoodSuspiciousStewBucketItem> EDELWOOD_SUSPICIOUS_STEW_BUCKET = HELPER.register("edelwood_suspicious_stew_bucket", () -> new EdelwoodSuspiciousStewBucketItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.SUSPICIOUS_STEW)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_BEETROOT_SOUP_BUCKET = HELPER.register("edelwood_beetroot_soup_bucket", () -> new EdelwoodSoupBucketItem(() -> Items.BEETROOT_SOUP, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(Foods.BEETROOT_SOUP)));
    public static final RegistryObject<EdelwoodSoupBucketItem> EDELWOOD_BAT_SOUP_BUCKET = HELPER.register("edelwood_bat_soup_bucket", () -> new EdelwoodSoupBucketItem(ModItems.BAT_SOUP, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1).food(ModFoods.BAT_SOUP)));
    public static final RegistryObject<ModArrowItem> BOOM_ARROW = HELPER.register("boom_arrow", () -> new ModArrowItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<ModArrowItem> DRACO_ARCANUS_ARROW = HELPER.register("draco_arcanus_arrow", () -> new ModArrowItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<EdelwoodOilItem> EDELWOOD_OIL = HELPER.register("edelwood_oil", () -> new EdelwoodOilItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(16)));
    public static final RegistryObject<ItemNameBlockItem> GOLDEN_ORCHID_SEEDS = HELPER.register("golden_orchid_seeds", () -> new ItemNameBlockItem(ModBlocks.GOLDEN_ORCHID.get(), new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));

    public static final RegistryObject<Item> SOUL_EXTRACTOR = HELPER.register("soul_extractor", () -> new SoulExtractorItem(new Item.Properties().durability(128)));
    public static final RegistryObject<Item> SLIMEC_PICKAXE = HELPER.register("slimec_pickaxe", () -> new SlimecPickaxeItem(ModTiers.SLIMEC, 1, -2.5F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_STAFF = HELPER.register("draco_arcanus_staff", () -> new Item(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_SWORD = HELPER.register("draco_arcanus_sword", () -> new SwordItem(ModTiers.DRACO_ARCANUS, 4, -2.2F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_SHOVEL = HELPER.register("draco_arcanus_shovel", () -> new ShovelItem(ModTiers.DRACO_ARCANUS, 2.5F, -2.8F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_PICKAXE = HELPER.register("draco_arcanus_pickaxe", () -> new PickaxeItem(ModTiers.DRACO_ARCANUS, 2, -2.6F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_AXE = HELPER.register("draco_arcanus_axe", () -> new AxeItem(ModTiers.DRACO_ARCANUS, 6, -2.8F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_HOE = HELPER.register("draco_arcanus_hoe", () -> new HoeItem(ModTiers.DRACO_ARCANUS, -4, 1, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_SCEPTER = HELPER.register("draco_arcanus_scepter", () -> new DracoArcanusScepterItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SWORD = HELPER.register("arcane_golden_sword", () -> new SwordItem(ModTiers.ARCANE_GOLDEN, 3, -2.4F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SHOVEL = HELPER.register("arcane_golden_shovel", () -> new ShovelItem(ModTiers.ARCANE_GOLDEN, 1.5F, -3.0F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_PICKAXE = HELPER.register("arcane_golden_pickaxe", () -> new PickaxeItem(ModTiers.ARCANE_GOLDEN, 1, -2.8F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_AXE = HELPER.register("arcane_golden_axe", () -> new AxeItem(ModTiers.ARCANE_GOLDEN, 5, -3.0F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_HOE = HELPER.register("arcane_golden_hoe", () -> new HoeItem(ModTiers.ARCANE_GOLDEN, -3, 0, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SWORD = HELPER.register("reinforced_arcane_golden_sword", () -> new SwordItem(ModTiers.REINFORCED_ARCANE_GOLDEN, 3, -2.4F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SHOVEL = HELPER.register("reinforced_arcane_golden_shovel", () -> new ShovelItem(ModTiers.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_PICKAXE = HELPER.register("reinforced_arcane_golden_pickaxe", () -> new PickaxeItem(ModTiers.REINFORCED_ARCANE_GOLDEN, 1, -2.8F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_AXE = HELPER.register("reinforced_arcane_golden_axe", () -> new AxeItem(ModTiers.REINFORCED_ARCANE_GOLDEN, 5, -3.0F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_HOE = HELPER.register("reinforced_arcane_golden_hoe", () -> new HoeItem(ModTiers.REINFORCED_ARCANE_GOLDEN, -3, 0, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> MYSTICAL_DAGGER = HELPER.register("mystical_dagger", () -> new MysticalDaggerItem(ModTiers.MYSTICAL_DAGGER, 2.5F, -0.3F, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).stacksTo(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_HELMET = HELPER.register("draco_arcanus_helmet", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, EquipmentSlot.HEAD, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_CHESTPLATE = HELPER.register("draco_arcanus_chestplate", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, EquipmentSlot.CHEST, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_LEGGINGS = HELPER.register("draco_arcanus_leggings", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, EquipmentSlot.LEGS, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> DRACO_ARCANUS_BOOTS = HELPER.register("draco_arcanus_boots", () -> new ArmorItem(ModArmorMaterials.DRACO_ARCANUS, EquipmentSlot.FEET, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> TYR_HELMET = HELPER.register("tyr_helmet", () -> new ArmorItem(ModArmorMaterials.TYR, EquipmentSlot.HEAD, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> TYR_CHESTPLATE = HELPER.register("tyr_chestplate", () -> new ArmorItem(ModArmorMaterials.TYR, EquipmentSlot.CHEST, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> TYR_LEGGINGS = HELPER.register("tyr_leggings", () -> new ArmorItem(ModArmorMaterials.TYR, EquipmentSlot.LEGS, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> TYR_BOOTS = HELPER.register("tyr_boots", () -> new ArmorItem(ModArmorMaterials.TYR, EquipmentSlot.FEET, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> MORTEM_HELMET = HELPER.register("mortem_helmet", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, EquipmentSlot.HEAD, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> MORTEM_CHESTPLATE = HELPER.register("mortem_chestplate", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, EquipmentSlot.CHEST, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> MORTEM_LEGGINGS = HELPER.register("mortem_leggings", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, EquipmentSlot.LEGS, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> MORTEM_BOOTS = HELPER.register("mortem_boots", () -> new MortemArmorItem(ModArmorMaterials.MORTEM, EquipmentSlot.FEET, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = HELPER.register("arcane_gold_helmet", () -> new ArmorItem(ModArmorMaterials.ARCANE_GOLD, EquipmentSlot.HEAD, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = HELPER.register("arcane_gold_chestplate", () -> new ArmorItem(ModArmorMaterials.ARCANE_GOLD, EquipmentSlot.CHEST, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = HELPER.register("arcane_gold_leggings", () -> new ArmorItem(ModArmorMaterials.ARCANE_GOLD, EquipmentSlot.LEGS, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = HELPER.register("arcane_gold_boots", () -> new ArmorItem(ModArmorMaterials.ARCANE_GOLD, EquipmentSlot.FEET, new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS)));

    public static class Stacks {
        public static final ItemStack LENS_OF_VERITATIS = new ItemStack(ModItems.LENS_OF_VERITATIS.get());
        public static final ItemStack ORB_OF_TEMPORARY_FLIGHT = new ItemStack(ModItems.ORB_OF_TEMPORARY_FLIGHT.get());
        public static final ItemStack SANITY_METER = new ItemStack(ModItems.SANITY_METER.get());
        public static final ItemStack ARCANE_CRYSTAL_DUST_SPECK = new ItemStack(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
    }
}