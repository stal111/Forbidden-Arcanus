package com.stal111.forbidden_arcanus.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BlockConfig {

    public static ModConfigSpec.BooleanValue STELLA_ARCANUM_EXPLODE;
    public static ModConfigSpec.BooleanValue STELLA_ARCANUM_BLOCK_DAMAGE;
    public static ModConfigSpec.IntValue STELLA_ARCANUM_EXPLOSION_RADIUS;

    public static ModConfigSpec.DoubleValue EDELWOOD_LADDER_SPEED;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("blocks");

        STELLA_ARCANUM_EXPLODE = builder.comment("Should Stella Arcanum explode when mined [default: true]").define("stella_arcanum.explode", true);
        STELLA_ARCANUM_BLOCK_DAMAGE = builder.comment("Should Stella Arcanum explosions deal Block Damage (if explosions enabled) [default: true]").define("stella_arcanum.block_damage", true);
        STELLA_ARCANUM_EXPLOSION_RADIUS = builder.comment("Radius of Stella Arcanum explosions (if explosions enabled) [default: 3]").defineInRange("stella_arcanum.explosion_radius", 3, 1, 10);

        EDELWOOD_LADDER_SPEED = builder.comment("The speed multiplier that gets added to the players y movement when on the ladder [default: 2.0").defineInRange("edelwood_ladder.speed", 2.0, 0, 10);

        builder.pop();
    }
}
