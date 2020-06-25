package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig {

    public static ForgeConfigSpec.IntValue ORB_OF_TEMPORARY_FLIGHT_TIME;

    public static ForgeConfigSpec.IntValue EDELWOOD_WATER_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_LAVA_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_MILK_BUCKET_CAPACITY;
    public static ForgeConfigSpec.IntValue EDELWOOD_SOUP_BUCKET_CAPACITY;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Item Config");

        ORB_OF_TEMPORARY_FLIGHT_TIME = CLIENT_BUILDER.comment("Flight Time the Orb grants (in ticks) [default: 6000]").defineInRange("items.orb_of_temporary_flight.time", 6000, 20, 120000);
        EDELWOOD_WATER_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Water Blocks the Edelwood Bucket can store [default: 4]").defineInRange("items.edelwood_bucket.water_capacity", 4, 1, 10);
        EDELWOOD_LAVA_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Lava Blocks the Edelwood Bucket can store [default: 3]").defineInRange("items.edelwood_bucket.lava_capacity", 3, 1, 10);
        EDELWOOD_MILK_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How much Milk the Edelwood Bucket can store [default: 4]").defineInRange("items.edelwood_bucket.milk_capacity", 4, 1, 10);
        EDELWOOD_SOUP_BUCKET_CAPACITY = CLIENT_BUILDER.comment("How many Soups/Stews the Edelwood Bucket can store [default: 8]").defineInRange("items.edelwood_bucket.soup_capacity", 8, 1, 10);
    }
}
