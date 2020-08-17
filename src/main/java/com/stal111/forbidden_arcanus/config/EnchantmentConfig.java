package com.stal111.forbidden_arcanus.config;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EnchantmentConfig {

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_ITEM_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> INDESTRUCTIBLE_ENCHANTMENT_BLACKLIST;


    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Enchantment Config");

        INDESTRUCTIBLE_ITEM_BLACKLIST = CLIENT_BUILDER.comment("Damageable Items that shouldn't be able to contain the Indestructible Enchantment").defineList("enchantments.indestructible.item_blacklist", Collections.emptyList(), EnchantmentConfig::validateDamageableItem);
        INDESTRUCTIBLE_ENCHANTMENT_BLACKLIST = CLIENT_BUILDER.comment("Enchantments that cannot be applied together with the Indestructible Enchantment").defineList("enchantments.indestructible.enchantment_blacklist", Arrays.asList(Objects.requireNonNull(Enchantments.UNBREAKING.getRegistryName()).toString(), Objects.requireNonNull(Enchantments.MENDING.getRegistryName()).toString()), EnchantmentConfig::validateEnchantment);
    }

    private static boolean validateDamageableItem(Object o) {
        return o == null || (ForgeRegistries.ITEMS.containsKey(new ResourceLocation((String) o)) && Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation((String) o))).isDamageable());
    }

    private static boolean validateEnchantment(Object o) {
        return o == null || (ForgeRegistries.ENCHANTMENTS.containsKey(new ResourceLocation((String) o)));
    }
}
