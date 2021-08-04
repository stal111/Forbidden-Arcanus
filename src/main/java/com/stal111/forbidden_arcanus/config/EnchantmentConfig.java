package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EnchantmentConfig {

    public static ForgeConfigSpec.BooleanValue INDESTRUCTIBLE_REPAIR_ITEM;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("enchantments");

        INDESTRUCTIBLE_REPAIR_ITEM = builder.comment("Should the item be repaired after applying the Indestructible enchantment [default: true]").define("indestructible.repair_item", true);

        builder.pop();
    }
}
