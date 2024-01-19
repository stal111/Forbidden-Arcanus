package com.stal111.forbidden_arcanus.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModifierConfig {

    public static ModConfigSpec.BooleanValue ETERNAL_REPAIR_ITEM;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("enchantments");

        ETERNAL_REPAIR_ITEM = builder.comment("Should the item be repaired after applying the Eternal modifier [default: true]").define("eternal.repair_item", true);

        builder.pop();
    }
}
