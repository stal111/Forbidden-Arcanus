package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlockConfig {

    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_EXPLODE;
    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL;
    public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_BLOCK_DAMAGE;
    public static ForgeConfigSpec.IntValue STELLA_ARCANUM_EXPLOSION_RADIUS;

    public static ForgeConfigSpec.IntValue RUNIC_CHISELED_POLISHED_DARKSTONE_RADIUS;


    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("blocks");

        STELLA_ARCANUM_EXPLODE = builder.comment("Should Stella Arcanum explode when mined [default: true]").define("stella_arcanum.explode", true);
        STELLA_ARCANUM_ONLY_EXPLODE_WRONG_TOOL = builder.comment("Should Stella Arcanum only explode when mined with a wrong tool (if explosions enabled) [default: false]").define("stella_arcanum.only_explode_wrong_tool", false);
        STELLA_ARCANUM_BLOCK_DAMAGE = builder.comment("Should Stella Arcanum explosions deal Block Damage (if explosions enabled) [default: true]").define("stella_arcanum.block_damage", true);
        STELLA_ARCANUM_EXPLOSION_RADIUS = builder.comment("Radius of Stella Arcanum explosions (if explosions enabled) [default: 3]").defineInRange("stella_arcanum.explosion_radius", 3, 1, 10);

        RUNIC_CHISELED_POLISHED_DARKSTONE_RADIUS = builder.comment("Radius the Activated Runic Chiseled Polished Darkstone prevents Entities from spawning in (in Blocks) [default: 25]").defineInRange("runic_chiseled_polished_darkstone.entity_spawn_blocking_radius", 25, 1, 100);

        builder.pop();
    }
}
