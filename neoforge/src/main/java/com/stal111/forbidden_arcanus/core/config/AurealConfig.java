package com.stal111.forbidden_arcanus.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Aureal Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.config.AurealConfig
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-03
 */
public class AurealConfig {

    public static ModConfigSpec.BooleanValue DISABLE_CONSEQUENCES;
    public static ModConfigSpec.DoubleValue AUREAL_ENTITY_SPAWN_CHANCE;
    public static ModConfigSpec.IntValue ENTITY_DEATH_INCREASEMENT_AMOUNT;
    public static ModConfigSpec.IntValue AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT;
    public static ModConfigSpec.DoubleValue ENTITY_DEATH_INCREASEMENT_CHANCE;
    public static ModConfigSpec.DoubleValue AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE;
    public static ModConfigSpec.BooleanValue NATURAL_CORRUPTION_DECREASEMENT;
    public static ModConfigSpec.IntValue NATURAL_CORRUPTION_DECREASEMENT_TIME;
    public static ModConfigSpec.DoubleValue BREEDING_DECREASEMENT_CHANCE;

    public static void init(ModConfigSpec.Builder builder) {
        builder.push("aureal");

        DISABLE_CONSEQUENCES = builder.comment("Do you want to completely disable corruption consequences? [default: false]").define("corruption.disable_consequences", false);
        AUREAL_ENTITY_SPAWN_CHANCE = builder.comment("Chance that an entity spawns as Aureal Entity [default: 0.25]").defineInRange("aureal_entity_chance", 0.25, 0.0, 1.0);
        ENTITY_DEATH_INCREASEMENT_AMOUNT = builder.comment("How much Corruption should killing a normal Entity give [default: 1]").defineInRange("corruption.entity_death_increasement_amount", 1, 0, 100);
        AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT = builder.comment("How much Corruption should killing an Aureal Entity give [default: 3]").defineInRange("corruption.aureal_entity_death_increasement_amount", 3, 0, 100);
        ENTITY_DEATH_INCREASEMENT_CHANCE = builder.comment("Chance that killing a normal Entity increases your Corruption [default: 0.35]").defineInRange("corruption.entity_death_increasement_chance", 0.35, 0.0, 1.0);
        AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE = builder.comment("Chance that killing an Aureal Entity increases your Corruption [default: 0.42]").defineInRange("corruption.aureal_entity_death_increasement_chance", 0.42, 0.0, 1.0);
        NATURAL_CORRUPTION_DECREASEMENT = builder.comment("Should the players Corruption value decrease after a certain amount of time [default: true]").define("corruption.natural_decreasement", true);
        NATURAL_CORRUPTION_DECREASEMENT_TIME = builder.comment("Time before the players Corruption value is reduced by one (in ticks) [default: 6000]").defineInRange("corruption.natural_decreasement_time", 6000, 0, Integer.MAX_VALUE);
        BREEDING_DECREASEMENT_CHANCE = builder.comment("Chance that breeding animals decreases your Corruption [default: 0.45]").defineInRange("corruption.breeding_decreasement_chance", 0.45, 0.0, 1.0);

        builder.pop();
    }
}
