package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.item.block.StrangeRootItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

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
    public static final RegistryObject<Item> STRANGE_ROOT = register("strange_root", () -> new StrangeRootItem(ModBlocks.STRANGE_ROOT.get(), properties().food(build(3, 0.6F))));
    public static final RegistryObject<Item> BOOM_ARROW = register("boom_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> DRACO_ARCANUS_ARROW = register("draco_arcanus_arrow", () -> new ModArrowItem(properties()));
    public static final RegistryObject<Item> GOLDEN_ORCHID_SEEDS = register("golden_orchid_seeds", () -> new GoldenOrchidItem(ModBlocks.GOLDEN_ORCHID.get(), properties()));
    public static final RegistryObject<Item> GOLDEN_FEATHER = register("golden_feather", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_STICK = register("edelwood_stick", () -> new Item(properties()));
    public static final RegistryObject<Item> EDELWOOD_OIL = register("edelwood_oil", () -> new ContainerItem(Items.GLASS_BOTTLE, properties()));
    public static final RegistryObject<Item> WAX = register("wax", () -> new Item(properties()));
    public static final RegistryObject<Item> SPAWNER_SCRAP = register("spawner_scrap", () -> new Item(properties()));
    public static final RegistryObject<Item> QUANTUM_CATCHER = register("quantum_catcher", () -> new QuantumCatcherItem(properties()));

    public static final RegistryObject<Item> SOUL_EXTRACTOR = register("soul_extractor", SoulExtractorItem::new);

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
