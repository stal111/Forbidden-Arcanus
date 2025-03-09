package com.stal111.forbidden_arcanus.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ItemConfig {

    public static ModConfigSpec.BooleanValue BOOM_ARROW_BLOCK_DAMAGE;
    public static ModConfigSpec.IntValue BOOM_ARROW_EXPLOSION_RADIUS;

    public static ModConfigSpec.BooleanValue MUNDABITUR_DUST_CHARGE_CREEPER;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("items");

        BOOM_ARROW_BLOCK_DAMAGE = builder.comment("Should Boom Arrow explosions deal Block Damage [default: false]").define("boom_arrow.block_damage", false);
        BOOM_ARROW_EXPLOSION_RADIUS = builder.comment("Radius of Boom Arrow explosions [default: 3]").defineInRange("boom_arrow.explosion_radius", 3, 1, 10);

        MUNDABITUR_DUST_CHARGE_CREEPER = builder.comment("Should right-clicking a Creeper with Mundabitur Dust charge the Creeper [default: true]").define("mundabitur_dust.charge_creeper", true);

        builder.pop();
    }
}
