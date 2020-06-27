package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlockConfig {

    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_EXPLODE;
    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL;
    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_BLOCK_DAMAGE;
    public static ForgeConfigSpec.IntValue STELLA_ARCANUM_EXPLOSION_RADIUS;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Block Config");

        STELLA_ARCANUM_EXPLODE = CLIENT_BUILDER.comment("Should Stella Arcanum explode when mined [default: true]").define("block.stella_arcanum.explode", true);
        STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL = CLIENT_BUILDER.comment("Should Stella Arcanum only explode when mined with a wrong tool (if explosions enabled) [default: false]").define("block.stella_arcanum.only_explode_wrong_tool", false);
        STELLA_ARCANUM_BLOCK_DAMAGE = CLIENT_BUILDER.comment("Should Stella Arcanum explosions deal Block Damage (if explosions enabled) [default: true]").define("block.stella_arcanum.block_damage", true);
        STELLA_ARCANUM_EXPLOSION_RADIUS = CLIENT_BUILDER.comment("Radius of Stella Arcanum explosions (if explosions enabled) [default: 3]").defineInRange("block.stella_arcanum.explosion_radius", 3, 1, 10);
    }
}
