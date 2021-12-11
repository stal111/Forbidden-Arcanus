package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.armor.ModArmorMaterial;
import com.stal111.forbidden_arcanus.item.block.StrangeRootItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Item> FORBIDDENMICON = register("forbiddenmicon", () -> new ForbiddenmiconItem(properties(1)));
    public static final RegistryObject<Item> ARCANE_CRYSTAL = register("arcane_crystal", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_CRYSTAL_DUST = register("arcane_crystal_dust", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = register("arcane_gold_ingot", () -> new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = register("arcane_gold_nugget", () -> new Item(properties()));
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
    public static final RegistryObject<Item> CLOTH = register("cloth", () -> new Item(properties()));
    public static final RegistryObject<Item> CHERRY_PEACH = register("cherry_peach", () -> new Item(properties().food(build(4, 0.4F))));
    public static final RegistryObject<Item> ENDER_PEARL_FRAGMENT = register("ender_pearl_fragment", () -> new Item(properties()));
    public static final RegistryObject<Item> DRAGON_SCALE = register("dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> SILVER_DRAGON_SCALE = register("silver_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> GOLDEN_DRAGON_SCALE = register("golden_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> AQUATIC_DRAGON_SCALE = register("aquatic_dragon_scale", () -> new Item(properties()));
    public static final RegistryObject<Item> ROTTEN_LEATHER = register("rotten_leather", () -> new Item(properties()));
    public static final RegistryObject<Item> SPECTRAL_EYE_AMULET = register("spectral_eye_amulet", () -> new SpectralEyeAmuletItem(properties(Rarity.RARE, 1)));
    public static final RegistryObject<Item> SEED_BULLET = register("seed_bullet", () -> new SeedBulletItem(properties(16)));
    public static final RegistryObject<Item> BAT_WING = register("bat_wing", () -> new Item(properties().food(build(3, 0.2F, new MobEffectInstance(MobEffects.POISON, 160, 0), 0.9F))));
    public static final RegistryObject<Item> BAT_SOUP = register("bat_soup", () -> new BowlFoodItem(properties(1).food(build(7, 0.7F, new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0), 1.0F))));
    public static final RegistryObject<Item> TENTACLE = register("tentacle", () -> new Item(properties().food(build(2, 0.1F, true))));
    public static final RegistryObject<Item> COOKED_TENTACLE = register("cooked_tentacle", () -> new Item(properties().food(build(5, 0.6F, true))));
    public static final RegistryObject<Item> STRANGE_ROOT = register("strange_root", () -> new StrangeRootItem(NewModBlocks.STRANGE_ROOT.get(), properties().food(build(3, 0.6F))));
    public static final RegistryObject<Item> BOOM_ARROW = register("boom_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> GOLDEN_ORCHID_SEEDS = register("golden_orchid_seeds", () -> new GoldenOrchidItem(NewModBlocks.GOLDEN_ORCHID.get(), properties()));
    public static final RegistryObject<Item> GOLDEN_FEATHER = register("golden_feather", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_STICK = register("edelwood_stick", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_OIL = register("edelwood_oil", () -> new ContainerItem(Items.GLASS_BOTTLE, properties()));
    public static final RegistryObject<Item> WAX = register("wax", () -> new Item(properties()));
    public static final RegistryObject<Item> SPAWNER_SCRAP = register("spawner_scrap", () -> new Item(properties()));
    public static final RegistryObject<Item> QUANTUM_CATCHER = register("quantum_catcher", () -> new QuantumCatcherItem(properties()));

    public static final RegistryObject<Item> SOUL_EXTRACTOR = register("soul_extractor", SoulExtractorItem::new);
    public static final RegistryObject<Item> SLIMEC_PICKAXE = register("slimec_pickaxe", () -> new SlimecPickaxeItem(ModItemTier.SLIMEC, 1, -2.5F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_STAFF = register("draco_arcanus_staff", () -> new Item(properties(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_SWORD = register("draco_arcanus_sword", () -> new SwordItem(ModItemTier.DRACO_ARCANUS, 4, -2.2F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_SHOVEL = register("draco_arcanus_shovel", () -> new ShovelItem(ModItemTier.DRACO_ARCANUS, 2.5F, -2.8F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_PICKAXE = register("draco_arcanus_pickaxe", () -> new PickaxeItem(ModItemTier.DRACO_ARCANUS, 2, -2.6F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_AXE = register("draco_arcanus_axe", () -> new AxeItem(ModItemTier.DRACO_ARCANUS, 6, -2.8F, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_HOE = register("draco_arcanus_hoe", () -> new HoeItem(ModItemTier.DRACO_ARCANUS, -4, 1, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_SCEPTER = register("draco_arcanus_scepter", () -> new DracoArcanusScepterItem(properties(1)));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SWORD = register("arcane_golden_sword", () -> new SwordItem(ModItemTier.ARCANE_GOLDEN, 3, -2.4F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_SHOVEL = register("arcane_golden_shovel", () -> new ShovelItem(ModItemTier.ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_PICKAXE = register("arcane_golden_pickaxe", () -> new PickaxeItem(ModItemTier.ARCANE_GOLDEN, 1, -2.8F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_AXE = register("arcane_golden_axe", () -> new AxeItem(ModItemTier.ARCANE_GOLDEN, 5, -3.0F, properties()));
    public static final RegistryObject<Item> ARCANE_GOLDEN_HOE = register("arcane_golden_hoe", () -> new HoeItem(ModItemTier.ARCANE_GOLDEN, -3, 0, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SWORD = register("reinforced_arcane_golden_sword", () -> new SwordItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 3, -2.4F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_SHOVEL = register("reinforced_arcane_golden_shovel", () -> new ShovelItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_PICKAXE = register("reinforced_arcane_golden_pickaxe", () -> new PickaxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1, -2.8F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_AXE = register("reinforced_arcane_golden_axe", () -> new AxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 5, -3.0F, properties()));
    public static final RegistryObject<Item> REINFORCED_ARCANE_GOLDEN_HOE = register("reinforced_arcane_golden_hoe", () -> new HoeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, -3, 0, properties()));
    public static final RegistryObject<Item> MYSTICAL_DAGGER = register("mystical_dagger", () -> new MysticalDaggerItem(ModItemTier.MYSTICAL_DAGGER, 2.5F, -0.3F, properties(1)));
    public static final RegistryObject<Item> DRACO_ARCANUS_HELMET = register("draco_arcanus_helmet", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlot.HEAD, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_CHESTPLATE = register("draco_arcanus_chestplate", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlot.CHEST, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_LEGGINGS = register("draco_arcanus_leggings", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlot.LEGS, properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_BOOTS = register("draco_arcanus_boots", () -> new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlot.FEET, properties()));
    public static final RegistryObject<Item> TYR_HELMET = register("tyr_helmet", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlot.HEAD, properties()));
    public static final RegistryObject<Item> TYR_CHESTPLATE = register("tyr_chestplate", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlot.CHEST, properties()));
    public static final RegistryObject<Item> TYR_LEGGINGS = register("tyr_leggings", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlot.LEGS, properties()));
    public static final RegistryObject<Item> TYR_BOOTS = register("tyr_boots", () -> new ArmorItem(ModArmorMaterial.TYR, EquipmentSlot.FEET, properties()));
    public static final RegistryObject<Item> MORTEM_HELMET = register("mortem_helmet", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlot.HEAD, properties()));
    public static final RegistryObject<Item> MORTEM_CHESTPLATE = register("mortem_chestplate", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlot.CHEST, properties()));
    public static final RegistryObject<Item> MORTEM_LEGGINGS = register("mortem_leggings", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlot.LEGS, properties()));
    public static final RegistryObject<Item> MORTEM_BOOTS = register("mortem_boots", () -> new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlot.FEET, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = register("arcane_gold_helmet", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlot.HEAD, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = register("arcane_gold_chestplate", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlot.CHEST, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = register("arcane_gold_leggings", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlot.LEGS, properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = register("arcane_gold_boots", () -> new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlot.FEET, properties()));

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
        return new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS).rarity(rarity).stacksTo(maxStackSize);
    }

    public static FoodProperties build(int hunger, float saturation) {
        return build(hunger, saturation, false);
    }

    public static FoodProperties build(int hunger, float saturation, boolean meat) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation);
        return meat ? food.meat().build() : food.build();
    }

    public static FoodProperties build(int hunger, float saturation, MobEffectInstance effect, float chance) {
        return build(hunger, saturation, effect, chance, false);
    }

    public static FoodProperties build(int hunger, float saturation, MobEffectInstance effect, float chance, boolean meat) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation).effect(effect, chance);
        return meat ? food.meat().build() : food.build();
    }
}
