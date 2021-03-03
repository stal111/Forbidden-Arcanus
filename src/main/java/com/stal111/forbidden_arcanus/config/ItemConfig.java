package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig {

    public static ForgeConfigSpec.IntValue ORB_OF_TEMPORARY_FLIGHT_TIME;

    public static ForgeConfigSpec.IntValue EDELWOOD_WATER_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_LAVA_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_MILK_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_SOUP_BUCKET_CAPACITY;

    public static ForgeConfigSpec.BooleanValue BOOM_ARROW_BLOCK_DAMAGE;
    public static ForgeConfigSpec.IntValue BOOM_ARROW_EXPLOSION_RADIUS;

    public static ForgeConfigSpec.BooleanValue MUNDABITUR_DUST_CHARGE_CREEPER;

    public static ForgeConfigSpec.IntValue XPETRIFIED_ORB_EXPERIENCE_POINTS;

    public static ForgeConfigSpec.IntValue ETERNAL_STELLA_USES;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("items");

        ORB_OF_TEMPORARY_FLIGHT_TIME = builder.comment("Flight Time the Orb grants (in ticks) [default: 6000]").defineInRange("orb_of_temporary_flight.time", 6000, 20, 120000);

        EDELWOOD_WATER_BUCKET_CAPACITY = builder.comment("How many Water Blocks the Edelwood Bucket can store [default: 4]").defineInRange("edelwood_bucket.water_capacity", 4, 1, 10);
        EDELWOOD_LAVA_BUCKET_CAPACITY = builder.comment("How many Lava Blocks the Edelwood Bucket can store [default: 3]").defineInRange("edelwood_bucket.lava_capacity", 3, 1, 10);
        EDELWOOD_MILK_BUCKET_CAPACITY = builder.comment("How much Milk the Edelwood Bucket can store [default: 4]").defineInRange("edelwood_bucket.milk_capacity", 4, 1, 10);
        EDELWOOD_SOUP_BUCKET_CAPACITY = builder.comment("How many Soups/Stews the Edelwood Bucket can store [default: 8]").defineInRange("edelwood_bucket.soup_capacity", 8, 1, 10);

        BOOM_ARROW_BLOCK_DAMAGE = builder.comment("Should Boom Arrow explosions deal Block Damage [default: false]").define("boom_arrow.block_damage", false);
        BOOM_ARROW_EXPLOSION_RADIUS = builder.comment("Radius of Boom Arrow explosions [default: 3]").defineInRange("boom_arrow.explosion_radius", 3, 1, 10);

        MUNDABITUR_DUST_CHARGE_CREEPER = builder.comment("Should right-clicking a Creeper with Mundabitur Dust charge the Creeper [default: true]").define("mundabitur_dust.charge_creeper", true);

        XPETRIFIED_ORB_EXPERIENCE_POINTS = builder.comment("Experience Points the Orb grants on use [default: 91]").defineInRange("xpetrified_orb.experience_points", 91, 1, 120000);

        ETERNAL_STELLA_USES = builder.comment("How many times the Eternal Stella can be used before breaking [default: 3]").defineInRange("eternal_stella.uses", 3, 1, 100);

        builder.pop();
    }
}
