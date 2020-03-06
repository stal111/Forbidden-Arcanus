package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NewModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> FORBIDDENMICON = register("forbiddenmicon", new ForbiddenmiconItem(properties(1)));
    public static final RegistryObject<Item> STELLARITE_PIECE = register("stellarite_piece", new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = register("arcane_gold_ingot", new Item(properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = register("arcane_gold_nugget", new Item(properties()));
    public static final RegistryObject<Item> XPETRIFIED_ORB = register("xpetrified_orb", new XpetrifiedOrbItem(properties(16)));
    public static final RegistryObject<Item> ORB_OF_TEMPORARY_FLIGHT = register("orb_of_temporary_flight", new OrbOfTemporaryFlightItem(properties(Rarity.UNCOMMON, 1)));
    public static final RegistryObject<Item> CHORUS_PEARL = register("chorus_pearl", new ChorusPearlItem(properties(16)));
    public static final RegistryObject<Item> SPECTRAL_EYE_AMULET = register("spectral_eye_amulet", new SpectralEyeAmuletItem(properties(Rarity.RARE, 1)));
    public static final RegistryObject<Item> SOUL = register("soul", new Item(properties()));
    public static final RegistryObject<Item> DARK_SOUL = register("dark_soul", new Item(properties()));

    private static <T extends Item> RegistryObject<T> register(String name, T item) {
        return ITEMS.register(name, () -> item);
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
}
