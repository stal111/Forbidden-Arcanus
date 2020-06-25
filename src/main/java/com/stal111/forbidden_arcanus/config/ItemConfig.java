package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig {

    public static ForgeConfigSpec.IntValue ORB_OF_TEMPORARY_FLIGHT_TIME;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Item Config");

        ORB_OF_TEMPORARY_FLIGHT_TIME = CLIENT_BUILDER.comment("Flight Time the Orb grants (in ticks) [default: 6000]").defineInRange("items.orb_of_temporary_flight.time", 6000, 20, 120000);

    }

}
