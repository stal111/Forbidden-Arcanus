package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.armor.ModArmorMaterial;
import com.stal111.forbidden_arcanus.item.block.StrangeRootItem;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Item> FORBIDDENMICON = register("forbiddenmicon", () -> new ForbiddenmiconItem(properties(1)));
    public static final RegistryObject<Item> STELLARITE_PIECE = register("stellarite_piece", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_CRYSTAL = register("arcane_crystal", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_CRYSTAL_DUST = register("arcane_crystal_dust", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = register("arcane_gold_ingot", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = register("arcane_gold_nugget", () -> new Item(properties()));
    public static final RegistryObject<Item> CHORUS_PEARL = register("chorus_pearl", () -> new ChorusPearlItem(properties(16)));
    public static final RegistryObject<Item> XPETRIFIED_ORB = register("xpetrified_orb", () -> new XpetrifiedOrbItem(properties(16)));
    public static final RegistryObject<Item> ETERNAL_STELLA = register("eternal_stella", () -> new EternalStellaItem(properties(1)));
    public static final RegistryObject<Item> ORB_OF_TEMPORARY_FLIGHT = register("orb_of_temporary_flight", () -> new OrbOfTemporaryFlightItem(properties(Rarity.UNCOMMON, 1)));
    public static final RegistryObject<Item> MUNDABITUR_DUST = register("mundabitur_dust", () -> new MundabiturDustItem(properties()));
    public static final RegistryObject<Item> CORRUPTI_DUST = register("corrupti_dust", () -> new Item(properties()));
    public static final RegistryObject<Item> DARK_MATTER = register("dark_matter", () -> new DarkMatterItem(properties()));
    public static final RegistryObject<Item> OBSIDIAN_WITH_IRON = register("obsidian_with_iron", () -> new Item(properties()));
    public static final RegistryObject<Item> OBSIDIAN_INGOT = register("obsidian_ingot", () -> new Item(properties()));
    public static final RegistryObject<Item> SOUL = register("soul", () -> new Item(properties()));
    public static final RegistryObject<Item> DARK_SOUL = register("dark_soul", () -> new Item(properties()));
    public static final RegistryObject<Item> PIXIE = register("pixie", () -> new PixieItem(properties(1)));
    public static final RegistryObject<Item> CORRUPTED_PIXIE = register("corrupted_pixie", () -> new Item(properties(1)));
    public static final RegistryObject<Item> RUNE = register("rune", () -> new Item(properties()));
    public static final RegistryObject<Item> DARK_RUNE = register("dark_rune", () -> new Item(properties()));
    public static final RegistryObject<Item> RUNE_BAG = register("rune_bag", () -> new Item(properties()));
    public static final RegistryObject<Item> DARK_RUNE_BAG = register("dark_rune_bag", () -> new Item(properties()));
    public static final RegistryObject<Item> CLOTH = register("cloth", () -> new Item(properties()));
    public static final RegistryObject<Item> CHERRY_PEACH = register("cherry_peach", () -> new Item(properties().food(build(4, 0.4F))));
    public static final RegistryObject<Item> ENDER_PEARL_FRAGMENT = register("ender_pearl_fragment", () -> new Item(properties()));
    public static final RegistryObject<Item> DARK_NETHER_STAR = register("dark_nether_star", () -> new EnchantmentGlintItem(properties(Rarity.RARE)));
    public static final RegistryObject<Item> DRAGON_SCALE = register("dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> SILVER_DRAGON_SCALE = register("silver_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> GOLDEN_DRAGON_SCALE = register("golden_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> AQUATIC_DRAGON_SCALE = register("aquatic_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> ROTTEN_LEATHER = register("rotten_leather", () -> new Item(properties()));
    public static final RegistryObject<Item> SPECTRAL_EYE_AMULET = register("spectral_eye_amulet", () -> new SpectralEyeAmuletItem(properties(Rarity.RARE, 1)));
    public static final RegistryObject<Item> SEED_BULLET = register("seed_bullet", () -> new SeedBulletItem(properties(16)));
    public static final RegistryObject<Item> BAT_WING = register("bat_wing", () -> new Item(properties().food(build(3, 0.2F, new EffectInstance(Effects.POISON, 160, 0), 0.9F))));
    public static final RegistryObject<Item> BAT_SOUP = register("bat_soup", () -> new SoupItem(properties(1).food(build(7, 0.7F, new EffectInstance(Effects.NIGHT_VISION, 240, 0), 1.0F))));
    public static final RegistryObject<Item> TENTACLE = register("tentacle", () -> new Item(properties().food(build(2, 0.1F, true))));
    public static final RegistryObject<Item> COOKED_TENTACLE = register("cooked_tentacle", () -> new Item(properties().food(build(5, 0.6F, true))));
    public static final RegistryObject<Item> STRANGE_ROOT = register("strange_root", () -> new StrangeRootItem(ModBlocks.STRANGE_ROOT.getBlock(), properties().food(build(3, 0.6F))));
    public static final RegistryObject<Item> BOOM_ARROW = register("boom_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> GOLDEN_ORCHID_SEEDS = register("golden_orchid_seeds", () -> new GoldenOrchidItem(ModBlocks.GOLDEN_ORCHID.getBlock(), properties()));
    public static final RegistryObject<Item> GOLDEN_FEATHER = register("golden_feather", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_STICK = register("edelwood_stick", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_OIL = register("edelwood_oil", () -> new ContainerItem(Items.GLASS_BOTTLE, properties()));
    public static final RegistryObject<Item> WAX = register("wax", () -> new Item(properties()));
    public static final RegistryObject<Item> SPAWNER_SCRAP = register("spawner_scrap", () -> new Item(properties()));
    public static final RegistryObject<Item> QUANTUM_CATCHER = register("quantum_catcher", () -> new QuantumCatcherItem(properties()));
    public static final RegistryObject<Item> EDELWOOD_BUCKET = register("edelwood_bucket", () -> new EdelwoodBucketItem(Fluids.EMPTY, properties(16)));
    public static final RegistryObject<Item> EDELWOOD_WATER_BUCKET = register("edelwood_water_bucket", () -> new EdelwoodBucketItem(Fluids.WATER, properties(1).containerItem(EDELWOOD_BUCKET.get())));
    public static final RegistryObject<Item> EDELWOOD_LAVA_BUCKET = register("edelwood_lava_bucket", () -> new EdelwoodBucketItem(Fluids.LAVA, properties(1).containerItem(EDELWOOD_BUCKET.get())));
    public static final RegistryObject<Item> EDELWOOD_MILK_BUCKET = register("edelwood_milk_bucket", () -> new EdelwoodMilkBucketItem(properties(1).containerItem(EDELWOOD_BUCKET.get())));
    public static final RegistryObject<Item> EDELWOOD_PUFFERFISH_BUCKET = register("edelwood_pufferfish_bucket", () -> new EdelwoodFishBucketItem(EntityType.PUFFERFISH, Fluids.WATER, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_SALMON_BUCKET = register("edelwood_salmon_bucket", () -> new EdelwoodFishBucketItem(EntityType.SALMON, Fluids.WATER, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_COD_BUCKET = register("edelwood_cod_bucket", () -> new EdelwoodFishBucketItem(EntityType.COD, Fluids.WATER, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_TROPICAL_FISH_BUCKET = register("edelwood_tropical_fish_bucket", () -> new EdelwoodFishBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_MUSHROOM_STEW_BUCKET = register("edelwood_mushroom_stew_bucket", () -> new EdelwoodSoupBucketItem(properties(1).containerItem(EDELWOOD_BUCKET.get()).food(Foods.MUSHROOM_STEW)));
    public static final RegistryObject<Item> EDELWOOD_SUSPICIOUS_STEW_BUCKET = register("edelwood_suspicious_stew_bucket", () -> new EdelwoodSuspiciousStewBucketItem(properties(1).food(Foods.SUSPICIOUS_STEW)));
    public static final RegistryObject<Item> EDELWOOD_BEETROOT_SOUP_BUCKET = register("edelwood_beetroot_soup_bucket", () -> new EdelwoodSoupBucketItem(properties(1).containerItem(EDELWOOD_BUCKET.get()).food(Foods.BEETROOT_SOUP)));
    public static final RegistryObject<Item> EDELWOOD_BAT_SOUP_BUCKET = register("edelwood_bat_soup_bucket", () -> new EdelwoodSoupBucketItem(properties(1).containerItem(EDELWOOD_BUCKET.get()).food(build(7, 0.7F, new EffectInstance(Effects.NIGHT_VISION, 240, 0), 1.0F))));
    public static final RegistryObject<Item> EDELWOOD_BAT_BUCKET = register("edelwood_bat_bucket", () -> new EdelwoodEntityBucketItem(EntityType.BAT, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_SQUID_BUCKET = register("edelwood_squid_bucket", () -> new EdelwoodFishBucketItem(EntityType.SQUID, Fluids.WATER, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_MAGMA_CUBE_BUCKET = register("edelwood_magma_cube_bucket", () -> new EdelwoodEntityBucketItem(EntityType.MAGMA_CUBE, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_SLIME_BUCKET = register("edelwood_slime_bucket", () -> new EdelwoodEntityBucketItem(EntityType.SLIME, properties(1)));
    public static final RegistryObject<Item> EDELWOOD_CHICKEN_BUCKET = register("edelwood_chicken_bucket", () -> new EdelwoodEntityBucketItem(EntityType.CHICKEN, properties(1)));
    public static final RegistryObject<Item> SOUL_EXTRACTOR = register("soul_extractor", SoulExtractorItem::new);
    public static final RegistryObject<Item> INFERNUM_PICKAXE = register("infernum_pickaxe", () -> new InfernumPickaxeItem(ModItemTier.INFERNUM, 1, -2.8F, properties()));
    public static final RegistryObject<Item> SLIMEC_PICKAXE = register("slimec_pickaxe", () -> new SlimecPickaxeItem(ModItemTier.SLIMEC, 1, -2.5F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_STAFF = register("draco_arcanus_staff", () -> new Item(properties(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_SWORD = register("draco_arcanus_sword", () -> new SwordItem(ModItemTier.DRACO_ARCANUS, 4, -2.2F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_SHOVEL = register("draco_arcanus_shovel", () -> new ShovelItem(ModItemTier.DRACO_ARCANUS, 2.5F, -2.8F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_PICKAXE = register("draco_arcanus_pickaxe", () -> new PickaxeItem(ModItemTier.DRACO_ARCANUS, 2, -2.6F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_AXE = register("draco_arcanus_axe", () -> new AxeItem(ModItemTier.DRACO_ARCANUS, 6, -2.8F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_HOE = register("draco_arcanus_hoe", () -> new ModHoeItem(ModItemTier.DRACO_ARCANUS, -4, 1, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_SCEPTER = register("draco_arcanus_scepter", () -> new DracoArcanusScepterItem(properties(1)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SWORD = register("arcane_golden_sword", () -> new SwordItem(ModItemTier.ARCANE_GOLDEN, 3, -2.4F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SHOVEL = register("arcane_golden_shovel", () -> new ShovelItem(ModItemTier.ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_PICKAXE = register("arcane_golden_pickaxe", () -> new PickaxeItem(ModItemTier.ARCANE_GOLDEN, 1, -2.8F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_AXE = register("arcane_golden_axe", () -> new AxeItem(ModItemTier.ARCANE_GOLDEN, 5, -3.0F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_HOE = register("arcane_golden_hoe", () -> new ModHoeItem(ModItemTier.ARCANE_GOLDEN, -3, 0, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SWORD = register("reinforced_arcane_golden_sword", () -> new SwordItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 3, -2.4F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SHOVEL = register("reinforced_arcane_golden_shovel", () -> new ShovelItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_PICKAXE = register("reinforced_arcane_golden_pickaxe", () -> new PickaxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1, -2.8F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_AXE = register("reinforced_arcane_golden_axe", () -> new AxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 5, -3.0F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_HOE = register("reinforced_arcane_golden_hoe", () -> new ModHoeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, -3, 0, properties()));
    public static final RegistryObject<Item> OBSIDIAN_SWORD = register("obsidian_sword", () -> new SwordItem(ModItemTier.OBSIDIAN, 3, -2.4F, properties()));
    public static final RegistryObject<Item> OBSIDIAN_SHOVEL = register("obsidian_shovel", () -> new ShovelItem(ModItemTier.OBSIDIAN, 1.5F, -3.0F, properties()));
    public static final RegistryObject<Item> OBSIDIAN_PICKAXE = register("obsidian_pickaxe", () -> new PickaxeItem(ModItemTier.OBSIDIAN, 1, -2.8F, properties()));
    public static final RegistryObject<Item> OBSIDIAN_AXE = register("obsidian_axe", () -> new AxeItem(ModItemTier.OBSIDIAN, 5, -3.0F, properties()));
    public static final RegistryObject<Item> OBSIDIAN_HOE = register("obsidian_hoe", () -> new ModHoeItem(ModItemTier.OBSIDIAN, -3, 0, properties()));
    public static final RegistryObject<Item> MYSTICAL_DAGGER = register("mystical_dagger", () -> new MysticalDaggerItem(ModItemTier.MYSTICAL_DAGGER, 2.5F, -0.3F, properties(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_HELMET = register("draco_arcanus_helmet", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_CHESTPLATE = register("draco_arcanus_chestplate", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_LEGGINGS = register("draco_arcanus_leggings", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_BOOTS = register("draco_arcanus_boots", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> TYR_HELMET = register("tyr_helmet", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> TYR_CHESTPLATE = register("tyr_chestplate", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> TYR_LEGGINGS = register("tyr_leggings", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> TYR_BOOTS = register("tyr_boots", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> MORTEM_HELMET = register("mortem_helmet", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> MORTEM_CHESTPLATE = register("mortem_chestplate", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> MORTEM_LEGGINGS = register("mortem_leggings", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> MORTEM_BOOTS = register("mortem_boots", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = register("arcane_gold_helmet", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = register("arcane_gold_chestplate", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = register("arcane_gold_leggings", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = register("arcane_gold_boots", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> OBSIDIAN_HELMET = register("obsidian_helmet", () -> new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD, properties()));
    public static final RegistryObject<Item> OBSIDIAN_SHOULDER_PADS = register("obsidian_shoulder_pads", () -> new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST, properties()));
    public static final RegistryObject<Item> OBSIDIAN_KNEE_PADS = register("obsidian_knee_pads", () -> new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS, properties()));
    public static final RegistryObject<Item> OBSIDIAN_BOOTS = register("obsidian_boots", () -> new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, properties()));
    public static final RegistryObject<Item> PIXIE_SPAWN_EGG = register("pixie_spawn_egg", () -> new ModSpawnEggItem<>(ModEntities.PIXIE, 894731, 0, properties()));

    private static RegistryObject<Item> register(String name, Supplier<? extends Item> item) {
        return ITEMS.register(name, item);
    }

    public static Item.Properties properties() {
        return properties(64);
    }

    public static Item.Properties properties(Rarity rarity) {
        return properties(rarity, 64);
    }

    public static Item.Properties properties(int maxStackSize) {
        return properties(Rarity.COMMON, maxStackSize);
    }

    public static Item.Properties properties(Rarity rarity, int maxStackSize) {
        return new Item.Properties().group(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(rarity).maxStackSize(maxStackSize);
    }

    public static Food build(int hunger, float saturation) {
        return build(hunger, saturation, false);
    }

    public static Food build(int hunger, float saturation, boolean meat) {
        Food.Builder food = new Food.Builder().hunger(hunger).saturation(saturation);
        return meat ? food.meat().build() : food.build();
    }

    public static Food build(int hunger, float saturation, EffectInstance effect, float chance) {
        return build(hunger, saturation, effect, chance, false);
    }

    public static Food build(int hunger, float saturation, EffectInstance effect, float chance, boolean meat) {
        Food.Builder food = new Food.Builder().hunger(hunger).saturation(saturation).effect(effect, chance);
        return meat ? food.meat().build() : food.build();
    }
}
