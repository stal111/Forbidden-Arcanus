package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.common.item.bucket.BucketFamily;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityBucketItem;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityMilkBucketItem;
import com.stal111.forbidden_arcanus.common.item.bucket.SolidCapacityBucketItem;
import com.stal111.forbidden_arcanus.common.item.mundabitur.MundabiturDustItem;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
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

    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL = HELPER.register("arcane_crystal", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> CORRUPTED_ARCANE_CRYSTAL = HELPER.register("corrupted_arcane_crystal", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL_DUST = HELPER.register("arcane_crystal_dust", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DEORUM_INGOT = HELPER.register("deorum_ingot", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DEORUM_NUGGET = HELPER.register("deorum_nugget", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> XPETRIFIED_ORB = HELPER.register("xpetrified_orb", () -> new XpetrifiedOrbItem(new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.EXPERIENCE, 91))));
    public static final ItemRegistryEntry<Item> ETERNAL_STELLA = HELPER.register("eternal_stella", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<Item> MUNDABITUR_DUST = HELPER.register("mundabitur_dust", () -> new MundabiturDustItem(new Item.Properties()));
    public static final ItemRegistryEntry<Item> CORRUPTI_DUST = HELPER.register("corrupti_dust", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DARK_MATTER = HELPER.register("dark_matter", () -> new DarkMatterItem(new Item.Properties()));
    public static final ItemRegistryEntry<Item> OBSIDIANSTEEL_INGOT = HELPER.register("obsidiansteel_ingot", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SOUL = HELPER.register("soul", () -> new Item(new Item.Properties().component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.SOULS, 1))));
    public static final ItemRegistryEntry<DarkSoulItem> CORRUPT_SOUL = HELPER.register("corrupt_soul", () -> new DarkSoulItem(new Item.Properties().component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.SOULS, 1))));
    public static final ItemRegistryEntry<Item> ENCHANTED_SOUL = HELPER.register("enchanted_soul", () -> new Item(new Item.Properties().component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.SOULS, 10))));
    public static final ItemRegistryEntry<Item> RUNE = HELPER.register("rune", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DARK_RUNE = HELPER.register("dark_rune", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> ENDER_PEARL_FRAGMENT = HELPER.register("ender_pearl_fragment", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRAGON_SCALE = HELPER.register("dragon_scale", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SILVER_DRAGON_SCALE = HELPER.register("silver_dragon_scale", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> GOLDEN_DRAGON_SCALE = HELPER.register("golden_dragon_scale", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> AQUATIC_DRAGON_SCALE = HELPER.register("aquatic_dragon_scale", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SPECTRAL_EYE_AMULET = HELPER.register("spectral_eye_amulet", () -> new SpectralEyeAmuletItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final ItemRegistryEntry<Item> BAT_WING = HELPER.register("bat_wing", () -> new Item(new Item.Properties().food(ModFoods.BAT_WING)));
    public static final ItemRegistryEntry<Item> BAT_SOUP = HELPER.register("bat_soup", () -> new Item(new Item.Properties().stacksTo(1).food(ModFoods.BAT_SOUP)));
    public static final ItemRegistryEntry<Item> TENTACLE = HELPER.register("tentacle", () -> new Item(new Item.Properties().food(ModFoods.TENTACLE)));
    public static final ItemRegistryEntry<Item> COOKED_TENTACLE = HELPER.register("cooked_tentacle", () -> new Item(new Item.Properties().food(ModFoods.COOKED_TENTACLE)));
    public static final ItemRegistryEntry<Item> EDELWOOD_STICK = HELPER.register("edelwood_stick", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> WAX = HELPER.register("wax", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<FerrogneticMixtureItem> FERROGNETIC_MIXTURE = HELPER.register("ferrognetic_mixture", () -> new FerrogneticMixtureItem(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SOUL_BINDING_CRYSTAL = HELPER.register("soul_binding_crystal", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SPAWNER_SCRAP = HELPER.register("spawner_scrap", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> QUANTUM_CATCHER = HELPER.register("quantum_catcher", () -> new QuantumCatcherItem(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED, new Item.Properties()));
    public static final ItemEntrySet<QuantumCatcherItem, DyeColor> DYED_QUANTUM_CATCHERS = HELPER.registerColorEntrySet("quantum_catcher", dyeColor -> new QuantumCatcherItem(ModTags.EntityTypes.QUANTUM_CATCHER_BLACKLISTED, new Item.Properties()));
    public static final ItemRegistryEntry<Item> BOSS_CATCHER = HELPER.register("boss_catcher", () -> new QuantumCatcherItem(ModTags.EntityTypes.BOSS_CATCHER_BLACKLISTED, new Item.Properties()));
    public static final ItemRegistryEntry<Item> ARTISAN_RELIC = HELPER.register("artisan_relic", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> CRESCENT_MOON = HELPER.register("crescent_moon", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> CRIMSON_STONE = HELPER.register("crimson_stone", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SOUL_CRIMSON_STONE = HELPER.register("soul_crimson_stone", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> ELEMENTARIUM = HELPER.register("elementarium", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DIVINE_PACT = HELPER.register("divine_pact", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> MALEDICTUS_PACT = HELPER.register("maledictus_pact", () -> new Item(new Item.Properties()));

    public static final ItemRegistryEntry<Item> SANITY_METER = HELPER.register("sanity_meter", () -> new SanityMeterItem(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<Item> AUREAL_TANK = HELPER.register("aureal_tank", () -> new AurealTankItem(new Item.Properties().stacksTo(1).component(ModDataComponents.ESSENCE_STORAGE, AurealTankItem.DEFAULT_DATA)));

    public static final ItemRegistryEntry<LensOfVeritatisItem> LENS_OF_VERITATIS = HELPER.register("lens_of_veritatis", () -> new LensOfVeritatisItem(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<BlockItem> UTREM_JAR = HELPER.register("utrem_jar", () -> new BlockItem(ModBlocks.UTREM_JAR.get(), new Item.Properties()));
    public static final ItemRegistryEntry<BlockItem> ESSENCE_UTREM_JAR = HELPER.register("essence_utrem_jar", () -> new BlockItem(ModBlocks.ESSENCE_UTREM_JAR.get(), new Item.Properties().component(ModDataComponents.EMPTY_ITEM, ModItems.UTREM_JAR)));

    public static final ItemRegistryEntry<AurealBottleItem> AUREAL_BOTTLE = HELPER.register("aureal_bottle", () -> new AurealBottleItem(new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.AUREAL, 35))));
    public static final ItemRegistryEntry<SplashAurealBottleItem> SPLASH_AUREAL_BOTTLE = HELPER.register("splash_aureal_bottle", () -> new SplashAurealBottleItem(new Item.Properties().stacksTo(16).component(ModDataComponents.ESSENCE_DATA, EssenceData.of(EssenceType.AUREAL, 30))));
    public static final ItemRegistryEntry<Item> ARCANE_CRYSTAL_DUST_SPECK = HELPER.register("arcane_crystal_dust_speck", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<ArcaneBoneMealItem> ARCANE_BONE_MEAL = HELPER.register("arcane_bone_meal", () -> new ArcaneBoneMealItem(new Item.Properties()));
    public static final ItemRegistryEntry<Item> TEST_TUBE = HELPER.register("test_tube", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<BloodTestTubeItem> BLOOD_TEST_TUBE = HELPER.register("blood_test_tube", () -> new BloodTestTubeItem(new Item.Properties().stacksTo(1).component(ModDataComponents.ESSENCE_STORAGE, BloodTestTubeItem.DEFAULT_DATA).component(ModDataComponents.EMPTY_ITEM, ModItems.TEST_TUBE)));
    public static final ItemRegistryEntry<Item> BLACKSMITH_GAVEL_HEAD = HELPER.register("blacksmith_gavel_head", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> WOODEN_BLACKSMITH_GAVEL = HELPER.register("wooden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.WOOD, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 1)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> STONE_BLACKSMITH_GAVEL = HELPER.register("stone_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.STONE, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 3)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> GOLDEN_BLACKSMITH_GAVEL = HELPER.register("golden_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.GOLD, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 2)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> IRON_BLACKSMITH_GAVEL = HELPER.register("iron_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.IRON, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 10)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> DIAMOND_BLACKSMITH_GAVEL = HELPER.register("diamond_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.DIAMOND, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 30)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> NETHERITE_BLACKSMITH_GAVEL = HELPER.register("netherite_blacksmith_gavel", () -> new BlacksmithGavelItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).fireResistant().component(ModDataComponents.RITUAL_USES, 60)));
    public static final ItemRegistryEntry<BlacksmithGavelItem> REINFORCED_DEORUM_BLACKSMITH_GAVEL = HELPER.register("reinforced_deorum_blacksmith_gavel", () -> new BlacksmithGavelItem(ModTiers.REINFORCED_DEORUM, new Item.Properties().stacksTo(1).component(ModDataComponents.RITUAL_USES, 80)));
    public static final ItemRegistryEntry<Item> STELLARITE_PIECE = HELPER.register("stellarite_piece", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> DARK_NETHER_STAR = HELPER.register("dark_nether_star", () -> new Item(new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
    public static final ItemRegistryEntry<Item> TERRASTOMP_PRISM = HELPER.register("terrastomp_prism", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> SEA_PRISM = HELPER.register("sea_prism", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<Item> WHIRLWIND_PRISM = HELPER.register("whirlwind_prism", () -> new WhirlwindPrismItem(new Item.Properties()));
    public static final ItemRegistryEntry<SmelterPrismItem> SMELTER_PRISM = HELPER.register("smelter_prism", () -> new SmelterPrismItem(new Item.Properties().durability(200)));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_BUCKET = HELPER.register("edelwood_bucket", () -> new CapacityBucketItem(Fluids.EMPTY, 0, BucketFamily.EDELWOOD_BUCKET, new Item.Properties().stacksTo(16)));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_WATER_BUCKET = HELPER.register("edelwood_water_bucket", () -> new CapacityBucketItem(Fluids.WATER, 4, BucketFamily.EDELWOOD_BUCKET, new Item.Properties().stacksTo(1).component(ModDataComponents.STORED_FLUID_AMOUNT, 1)));
    public static final ItemRegistryEntry<CapacityBucketItem> EDELWOOD_LAVA_BUCKET = HELPER.register("edelwood_lava_bucket", () -> new CapacityBucketItem(Fluids.LAVA, 3, BucketFamily.EDELWOOD_BUCKET, new Item.Properties().stacksTo(1).component(ModDataComponents.STORED_FLUID_AMOUNT, 1)));
    public static final ItemRegistryEntry<CapacityMilkBucketItem> EDELWOOD_MILK_BUCKET = HELPER.register("edelwood_milk_bucket", () -> new CapacityMilkBucketItem(BucketFamily.EDELWOOD_BUCKET, new Item.Properties().stacksTo(1).component(ModDataComponents.STORED_FLUID_AMOUNT, 1)));
    public static final ItemRegistryEntry<SolidCapacityBucketItem> EDELWOOD_POWDER_SNOW_BUCKET = HELPER.register("edelwood_powder_snow_bucket", () -> new SolidCapacityBucketItem(Blocks.POWDER_SNOW, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, BucketFamily.EDELWOOD_BUCKET, new Item.Properties().stacksTo(1).component(ModDataComponents.STORED_FLUID_AMOUNT, 1)));
    public static final ItemRegistryEntry<ModArrowItem> BOOM_ARROW = HELPER.register("boom_arrow", () -> new ModArrowItem(new Item.Properties()));
    public static final ItemRegistryEntry<ModArrowItem> DRACO_ARCANUS_ARROW = HELPER.register("draco_arcanus_arrow", () -> new ModArrowItem(new Item.Properties()));
    public static final ItemRegistryEntry<EdelwoodOilItem> EDELWOOD_OIL = HELPER.register("edelwood_oil", () -> new EdelwoodOilItem(new Item.Properties().stacksTo(16)));
    public static final ItemRegistryEntry<ItemNameBlockItem> GOLDEN_ORCHID_SEEDS = HELPER.register("golden_orchid_seeds", () -> new ItemNameBlockItem(ModBlocks.GOLDEN_ORCHID.get(), new Item.Properties()));
    public static final ItemRegistryEntry<Item> APPLY_MODIFIER_SMITHING_TEMPLATE = HELPER.register("apply_modifier_smithing_template", SmithingTemplateConstants::createApplyModifierTemplate);
    public static final ItemRegistryEntry<Item> OMEGA_ARCOIN = HELPER.register("omega_arcoin", () -> new Item(new Item.Properties()));
    public static final ItemRegistryEntry<BoatItem> AURUM_BOAT = HELPER.register("aurum_boat", () -> new ModBoatItem(false, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<BoatItem> AURUM_CHEST_BOAT = HELPER.register("aurum_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.AURUM, new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<BoatItem> EDELWOOD_BOAT = HELPER.register("edelwood_boat", () -> new ModBoatItem(false, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<BoatItem> EDELWOOD_CHEST_BOAT = HELPER.register("edelwood_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.EDELWOOD, new Item.Properties().stacksTo(1)));
    //public static final ItemRegistryEntry<BoatItem> FUNGYSS_BOAT = HELPER.register("fungyss_boat", () -> new ModBoatItem(false, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));
    //public static final ItemRegistryEntry<BoatItem> FUNGYSS_CHEST_BOAT = HELPER.register("fungyss_chest_boat", () -> new ModBoatItem(true, ModBoat.Type.FUNGYSS, new Item.Properties().stacksTo(1)));

    public static final ItemRegistryEntry<Item> SOUL_EXTRACTOR = HELPER.register("soul_extractor", () -> new SoulExtractorItem(new Item.Properties().durability(128)));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_STAFF = HELPER.register("draco_arcanus_staff", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SWORD = HELPER.register("draco_arcanus_sword", () -> new SwordItem(ModTiers.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SHOVEL = HELPER.register("draco_arcanus_shovel", () -> new ShovelItem(ModTiers.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_PICKAXE = HELPER.register("draco_arcanus_pickaxe", () -> new PickaxeItem(ModTiers.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_AXE = HELPER.register("draco_arcanus_axe", () -> new AxeItem(ModTiers.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_HOE = HELPER.register("draco_arcanus_hoe", () -> new HoeItem(ModTiers.DRACO_ARCANUS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_SCEPTER = HELPER.register("draco_arcanus_scepter", () -> new DracoArcanusScepterItem(new Item.Properties().stacksTo(1)));
    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_SWORD = HELPER.register("reinforced_deorum_sword", () -> new SwordItem(ModTiers.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_SHOVEL = HELPER.register("reinforced_deorum_shovel", () -> new ShovelItem(ModTiers.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_PICKAXE = HELPER.register("reinforced_deorum_pickaxe", () -> new PickaxeItem(ModTiers.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_AXE = HELPER.register("reinforced_deorum_axe", () -> new AxeItem(ModTiers.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> REINFORCED_DEORUM_HOE = HELPER.register("reinforced_deorum_hoe", () -> new HoeItem(ModTiers.REINFORCED_DEORUM, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_HELMET = HELPER.register("draco_arcanus_helmet", () -> new ArmorItem(FAArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_CHESTPLATE = HELPER.register("draco_arcanus_chestplate", () -> new ArmorItem(FAArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_LEGGINGS = HELPER.register("draco_arcanus_leggings", () -> new ArmorItem(FAArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> DRACO_ARCANUS_BOOTS = HELPER.register("draco_arcanus_boots", () -> new ArmorItem(FAArmorMaterials.DRACO_ARCANUS, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> TYR_HELMET = HELPER.register("tyr_helmet", () -> new ArmorItem(FAArmorMaterials.TYR, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final ItemRegistryEntry<Item> TYR_CHESTPLATE = HELPER.register("tyr_chestplate", () -> new ArmorItem(FAArmorMaterials.TYR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final ItemRegistryEntry<Item> TYR_LEGGINGS = HELPER.register("tyr_leggings", () -> new ArmorItem(FAArmorMaterials.TYR, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> TYR_BOOTS = HELPER.register("tyr_boots", () -> new ArmorItem(FAArmorMaterials.TYR, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> MORTEM_HELMET = HELPER.register("mortem_helmet", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final ItemRegistryEntry<Item> MORTEM_CHESTPLATE = HELPER.register("mortem_chestplate", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final ItemRegistryEntry<Item> MORTEM_LEGGINGS = HELPER.register("mortem_leggings", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final ItemRegistryEntry<Item> MORTEM_BOOTS = HELPER.register("mortem_boots", () -> new ArmorItem(FAArmorMaterials.MORTEM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static class Stacks {
        public static final ItemStack LENS_OF_VERITATIS = new ItemStack(ModItems.LENS_OF_VERITATIS.get());
        public static final ItemStack SANITY_METER = new ItemStack(ModItems.SANITY_METER.get());
        public static final ItemStack ARCANE_CRYSTAL_DUST_SPECK = new ItemStack(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get());
    }
}