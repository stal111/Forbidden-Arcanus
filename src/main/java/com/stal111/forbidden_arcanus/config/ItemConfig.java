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

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Item Config");

        ORB_OF_TEMPORARY_FLIGHT_TIME = CLIENT_BUILDER.comment("Flight Time the Orb grants (in ticks) [default: 6000]").defineInRange("items.orb_of_temporary_flight.time", 6000, 20, 120000);

        EDELWOOD_WATER_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Water Blocks the Edelwood Bucket can store [default: 4]").defineInRange("items.edelwood_bucket.water_capacity", 4, 1, 10);
        EDELWOOD_LAVA_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Lava Blocks the Edelwood Bucket can store [default: 3]").defineInRange("items.edelwood_bucket.lava_capacity", 3, 1, 10);
        EDELWOOD_MILK_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How much Milk the Edelwood Bucket can store [default: 4]").defineInRange("items.edelwood_bucket.milk_capacity", 4, 1, 10);
        EDELWOOD_SOUP_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Soups/Stews the Edelwood Bucket can store [default: 8]").defineInRange("items.edelwood_bucket.soup_capacity", 8, 1, 10);

        BOOM_ARROW_BLOCK_DAMAGE = CLIENT_BUILDER.comment("Should Boom Arrow Explosions deal Block Damage [default: false]").define("items.boom_arrow.block_damage", false);
        BOOM_ARROW_EXPLOSION_RADIUS = CLIENT_BUILDER.comment("Radius of Boom Arrow Explosions [default: 3]").defineInRange("items.boom_arrow.explosion_radius", 3, 1, 10);

        MUNDABITUR_DUST_CHARGE_CREEPER = CLIENT_BUILDER.comment("Should right-clicking a Creeper with Mundabitur Dust charge the Creeper [default: true]").define("items.mundabitur_dust.charge_creeper", true);
    }
}
