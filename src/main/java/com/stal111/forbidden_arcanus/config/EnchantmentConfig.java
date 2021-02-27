package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EnchantmentConfig {

    public static ForgeConfigSpec.BooleanValue INDESTRUCTIBLE_FULLY_REPAIR_ITEM;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Enchantment Config");

        INDESTRUCTIBLE_FULLY_REPAIR_ITEM = CLIENT_BUILDER.comment("Should the item be fully repaired after applying the Indestructible enchantment [default: true]").define("enchantments.indestructible.fully_repair_item", true);
    }
}
