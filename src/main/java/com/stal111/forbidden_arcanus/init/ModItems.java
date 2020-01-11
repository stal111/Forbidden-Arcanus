package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.armor.ModArmorMaterial;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public enum ModItems {
    ARCANE_GOLD_INGOT(new Item(properties())),
    ARCANE_GOLD_NUGGET(new Item(properties())),
    ORB_OF_TEMPORARY_FLIGHT(new OrbOfTemporaryFlightItem(properties(Rarity.UNCOMMON, 1))),
    CHORUS_PEARL(new ChorusPearlItem(properties(16))),
    SPECTRAL_EYE_AMULET(new SpectralEyeAmuletItem(properties(Rarity.RARE, 1))),
    SOUL(new Item(properties())),
    DARK_SOUL(new Item(properties())),
    PIXIE(new PixieItem(properties(1))),
    CORRUPTED_PIXIE(new Item(properties(1))),
    ARCANE_CRYSTAL(new Item(properties())),
    ARCANE_CRYSTAL_DUST(new Item(properties())),
    MUNDABITUR_DUST(new MundabiturDustItem(properties())),
    CORRUPTI_DUST(new Item(properties())),
    DARK_MATTER(new Item(properties())),
    ENDER_PEARL_FRAGMENT(new Item(properties())),
    RUNE(new Item(properties())),
    DARK_RUNE(new Item(properties())),
    RUNE_BAG(new Item(properties())),
    DARK_RUNE_BAG(new Item(properties())),
    SKULL(new Item(properties())),
    OBSIDIAN_SKULL(new ObsidianSkullItem(properties(1))),
    OBSIDIAN_SKULL_SHIELD(new ObsidianSkullShieldItem(properties(Rarity.UNCOMMON,1))),
    OBSIDIAN_WITH_IRON(new Item(properties())),
    OBSIDIAN_INGOT(new Item(properties())),
    DARK_NETHER_STAR(new EnchantmentGlintItem(properties(Rarity.RARE))),
    DRAGON_SCALE(new Item(properties())),
    SILVER_DRAGON_SCALE(new Item(properties())),
    GOLDEN_DRAGON_SCALE(new Item(properties())),
    AQUATIC_DRAGON_SCALE(new Item(properties())),
    ROTTEN_LEATHER(new Item(properties())),
    LEATHER_OF_THE_SEA(new Item(properties())),
    CHERRY_PEACH(new Item(properties().food(build(4, 0.4F)))),
    CLOTH(new Item(properties())),
    GOLDEN_FEATHER(new Item(properties())),
    GOLDEN_ORCHID_SEEDS(new GoldenOrchidItem(ModBlocks.GOLDEN_ORCHID.getBlock(), properties())),
    SEED_BULLET(new SeedBulletItem(properties())),
    BAT_WING(new Item(properties().food(build(3, 0.2F, new EffectInstance(Effects.POISON, 160, 0), 0.9F)))),
    BAT_SOUP(new SoupItem(properties(1).food(build(7, 0.7F, new EffectInstance(Effects.NIGHT_VISION, 240, 0), 1.0F)))),
    TENTACLE(new Item(properties().food(build(2, 0.1F, true)))),
    COOKED_TENTACLE(new Item(properties().food(build(5, 0.6F, true)))),
    EDELWOOD_STICK(new Item(properties())),
    EDELWOOD_OIL(new ContainerItem(Items.GLASS_BOTTLE, properties())),
    EDELWOOD_BUCKET(new EdelwoodBucketItem(Fluids.EMPTY, 0, properties(16))),
    EDELWOOD_WATER_BUCKET(new EdelwoodBucketItem(Fluids.WATER, 4, properties(1).containerItem(EDELWOOD_BUCKET.getItem()))),
    EDELWOOD_LAVA_BUCKET(new EdelwoodBucketItem(Fluids.LAVA, 3, properties(1).containerItem(EDELWOOD_BUCKET.getItem()))),
    EDELWOOD_MILK_BUCKET(new EdelwoodMilkBucketItem(4, properties(1).containerItem(EDELWOOD_BUCKET.getItem()))),
    EDELWOOD_PUFFERFISH_BUCKET(new EdelwoodFishBucketItem(EntityType.PUFFERFISH, Fluids.WATER, 4, properties(1))),
    EDELWOOD_SALMON_BUCKET(new EdelwoodFishBucketItem(EntityType.SALMON, Fluids.WATER, 4, properties(1))),
    EDELWOOD_COD_BUCKET(new EdelwoodFishBucketItem(EntityType.COD, Fluids.WATER, 4, properties(1))),
    EDELWOOD_TROPICAL_FISH_BUCKET(new EdelwoodFishBucketItem(EntityType.TROPICAL_FISH, Fluids.WATER, 4, properties(1))),
    EDELWOOD_MUSHROOM_STEW_BUCKET(new EdelwoodSoupBucketItem(8, properties(1).containerItem(EDELWOOD_BUCKET.getItem()).food(Foods.MUSHROOM_STEW))),
    EDELWOOD_SUSPICIOUS_STEW_BUCKET(new EdelwoodSuspiciousStewBucketItem(properties(1).food(Foods.SUSPICIOUS_STEW))),
    EDELWOOD_BEETROOT_SOUP_BUCKET(new EdelwoodSoupBucketItem(8, properties(1).containerItem(EDELWOOD_BUCKET.getItem()).food(Foods.BEETROOT_SOUP))),
    EDELWOOD_BAT_SOUP_BUCKET(new EdelwoodSoupBucketItem(8, properties(1).containerItem(EDELWOOD_BUCKET.getItem()).food(build(7, 0.7F, new EffectInstance(Effects.NIGHT_VISION, 240, 0), 1.0F)))),
    EDELWOOD_BAT_BUCKET(new EdelwoodEntityBucketItem(EntityType.BAT, properties(1).containerItem(EDELWOOD_BUCKET.getItem()))),
    EDELWOOD_SQUID_BUCKET(new EdelwoodFishBucketItem(EntityType.SQUID, Fluids.WATER, 4, properties(1).containerItem(EDELWOOD_BUCKET.getItem()))),
    EDELWOOD_MAGMA_CUBE_BUCKET(new EdelwoodEntityBucketItem(EntityType.MAGMA_CUBE, properties(1))),
    EDELWOOD_SLIME_BUCKET(new EdelwoodEntityBucketItem(EntityType.SLIME, properties(1))),
    EDELWOOD_CHICKEN_BUCKET(new EdelwoodEntityBucketItem(EntityType.CHICKEN, properties(1))),
    AKU_AKU(new Item(properties(1))),
    GOLDEN_AKU_AKU(new Item(properties(1))),
    SOUL_EXTRACTOR(new SoulExtractorItem()),
    INFERNUM_PICKAXE(new InfernumPickaxeItem(ModItemTier.INFERNUM, 1, -2.8F, properties())),
    SLIMEC_PICKAXE(new SlimecPickaxeItem(ModItemTier.SLIMEC, 1, -2.5F, properties())),
    RUNIC_BATTLEAXE(new AxeItem(ItemTier.DIAMOND, 1, -2.8F, properties())),
    SACRED_SCEPTER(new Item(properties(1))),
    DRACO_ARCANUS_STAFF(new Item(properties(1))),
    DRACO_ARCANUS_SWORD(new SwordItem(ModItemTier.DRACO_ARCANUS, 4, -2.2F, properties())),
    DRACO_ARCANUS_SHOVEL(new ShovelItem(ModItemTier.DRACO_ARCANUS, 2.5F, -2.8F, properties())),
    DRACO_ARCANUS_PICKAXE(new PickaxeItem(ModItemTier.DRACO_ARCANUS, 2, -2.6F, properties())),
    DRACO_ARCANUS_AXE(new AxeItem(ModItemTier.DRACO_ARCANUS, 6, -2.8F, properties())),
    DRACO_ARCANUS_HOE(new HoeItem(ModItemTier.DRACO_ARCANUS, 0, properties())),
    DRACO_ARCANUS_SCEPTER(new DracoArcanusScepterItem(properties(1))),
    ARCANE_GOLDEN_SWORD(new SwordItem(ModItemTier.ARCANE_GOLDEN, 3, -2.4F, properties())),
    ARCANE_GOLDEN_SHOVEL(new ShovelItem(ModItemTier.ARCANE_GOLDEN, 1.5F, -3.0F, properties())),
    ARCANE_GOLDEN_PICKAXE(new PickaxeItem(ModItemTier.ARCANE_GOLDEN, 1, -2.8F, properties())),
    ARCANE_GOLDEN_AXE(new AxeItem(ModItemTier.ARCANE_GOLDEN, 5, -3.0F, properties())),
    ARCANE_GOLDEN_HOE(new HoeItem(ModItemTier.ARCANE_GOLDEN, 0.0F, properties())),
    REINFORCED_ARCANE_GOLDEN_SWORD(new SwordItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 3, -2.4F, properties())),
    REINFORCED_ARCANE_GOLDEN_SHOVEL(new ShovelItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F, properties())),
    REINFORCED_ARCANE_GOLDEN_PICKAXE(new PickaxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1, -2.8F, properties())),
    REINFORCED_ARCANE_GOLDEN_AXE(new AxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 5, -3.0F, properties())),
    REINFORCED_ARCANE_GOLDEN_HOE(new HoeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 0.0F, properties())),
    OBSIDIAN_SWORD(new SwordItem(ModItemTier.OBSIDIAN, 3, -2.4F, properties())),
    OBSIDIAN_SHOVEL(new ShovelItem(ModItemTier.OBSIDIAN, 1.5F, -3.0F, properties())),
    OBSIDIAN_PICKAXE(new PickaxeItem(ModItemTier.OBSIDIAN, 1, -2.8F, properties())),
    OBSIDIAN_AXE(new AxeItem(ModItemTier.OBSIDIAN, 5, -3.0F, properties())),
    OBSIDIAN_HOE(new HoeItem(ModItemTier.OBSIDIAN, 0.0F, properties())),
    BATTLE_SKULL(new MultiToolItem(ModItemTier.BONE, 3, -2.4F, properties())),
    BONE_SWORD(new SwordItem(ModItemTier.BONE, 3, -2.4F, properties())),
    BONE_SHOVEL(new ShovelItem(ModItemTier.BONE, 1.5F, -3.0F, properties())),
    BONE_PICKAXE(new PickaxeItem(ModItemTier.BONE, 1, -2.8F, properties())),
    BONE_AXE(new AxeItem(ModItemTier.BONE, 5, -3.0F, properties())),
    BONE_HOE(new HoeItem(ModItemTier.BONE, 0.0F, properties())),
    MYSTICAL_DAGGER(new MysticalDaggerItem(ModItemTier.MYSTICAL_DAGGER, 2.5F, -0.3F, properties(1))),
    DRACO_ARCANUS_HELMET(new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.HEAD, properties())),
    DRACO_ARCANUS_CHESTPLATE(new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.CHEST, properties())),
    DRACO_ARCANUS_LEGGINGS(new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.LEGS, properties())),
    DRACO_ARCANUS_BOOTS(new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.FEET, properties())),
    TYR_HELMET(new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.HEAD, properties())),
    TYR_CHESTPLATE(new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.CHEST, properties())),
    TYR_LEGGINGS(new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.LEGS, properties())),
    TYR_BOOTS(new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.FEET, properties())),
    MORTEM_HELMET(new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.HEAD, properties())),
    MORTEM_CHESTPLATE(new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.CHEST, properties())),
    MORTEM_LEGGINGS(new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.LEGS, properties())),
    MORTEM_BOOTS(new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.FEET, properties())),
    ARCANE_GOLD_HELMET(new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.HEAD, properties())),
    ARCANE_GOLD_CHESTPLATE(new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.CHEST, properties())),
    ARCANE_GOLD_LEGGINGS(new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.LEGS, properties())),
    ARCANE_GOLD_BOOTS(new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.FEET, properties())),
    OBSIDIAN_HELMET(new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD, properties())),
    OBSIDIAN_SHOULDER_PADS(new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST, properties())),
    OBSIDIAN_KNEE_PADS(new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS, properties())),
    OBSIDIAN_BOOTS(new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, properties())),
    BOOM_ARROW(new ArrowItem(properties())),
    DRACO_ARCANUS_ARROW(new ArrowItem(properties()));

    private final Item item;

    ModItems(Item item) {
        this.item = item;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public Item getItem() {
        if (item.getRegistryName() == null) {
            item.setRegistryName(Main.MOD_ID, getName());
        }
        return item;
    }

    public ItemStack getStack() {
        return getItem().getDefaultInstance();
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
        return new Item.Properties().group(Main.FORBIDDEN_ARCANUS).rarity(rarity).maxStackSize(maxStackSize);
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
