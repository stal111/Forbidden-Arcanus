package com.stal111.forbidden_arcanus.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModifierConfig {

    public static ForgeConfigSpec.BooleanValue ETERNAL_REPAIR_ITEM;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("enchantments");

        ETERNAL_REPAIR_ITEM = builder.comment("Should the item be repaired after applying the Eternal modifier [default: true]").define("eternal.repair_item", true);

        builder.pop();
    }
}
