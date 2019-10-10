package com.stal111.forbidden_arcanus.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WorldGenConfig {

	public static ForgeConfigSpec.BooleanValue GENERATE_ARCANE_CRYSTAL_ORE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_COUNT;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue GENERATE_RUNESTONE;
	public static ForgeConfigSpec.IntValue RUNESTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue RUNESTONE_COUNT;
	public static ForgeConfigSpec.IntValue RUNESTONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue RUNESTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue GENERATE_DARK_RUNESTONE;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_COUNT;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue GENERATE_DARK_STONE;
	public static ForgeConfigSpec.IntValue DARK_STONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue DARK_STONE_COUNT;
	public static ForgeConfigSpec.IntValue DARK_STONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue DARK_STONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue GENERATE_CHERRYWOOD_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_MYSTERYWOOD_TREES;
	public static ForgeConfigSpec.BooleanValue GENERATE_EDELWOOD;
	public static ForgeConfigSpec.BooleanValue GENERATE_YELLOW_ORCHID;

	public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
		SERVER_BUILDER.comment("World Generation Config");

		GENERATE_ARCANE_CRYSTAL_ORE = SERVER_BUILDER.comment("Generate Arcane Crystal Ore").define("oregen.generateArcaneCrystalOre", true);
		ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE = SERVER_BUILDER.comment("Maximum size of Arcane Crystal Ore vein").defineInRange("oregen.maxArcaneCrystalOreVeinSize", 6, 0, 500);
		ARCANE_CRYSTAL_ORE_COUNT = SERVER_BUILDER.comment("Maximum Arcane Crystal Ore veins per chunk").defineInRange("oregen.ArcaneCrystalOreCount", 3, 1, 10000000);
		ARCANE_CRYSTAL_ORE_MIN_HEIGHT = SERVER_BUILDER.comment("Minimum height for the Arcane Crystal Ore").defineInRange("oregen.ArcaneCrystalOreMinHeight", 0, 1, 256);
		ARCANE_CRYSTAL_ORE_MAX_HEIGHT = SERVER_BUILDER.comment("Maximum height for the Arcane Crystal Ore").defineInRange("oregen.ArcaneCrystalOreMaxHeight", 20, 1, 256);

		GENERATE_RUNESTONE = SERVER_BUILDER.comment("Generate Runestone").define("oregen.generateRunestone", true);
		RUNESTONE_MAX_VEIN_SIZE = SERVER_BUILDER.comment("Maximum size of Runestone vein").defineInRange("oregen.maxRunestoneVeinSize", 3, 0, 500);
		RUNESTONE_COUNT = SERVER_BUILDER.comment("Maximum Runestone veins per chunk").defineInRange("oregen.RunestoneCount", 3, 1, 10000000);
		RUNESTONE_MIN_HEIGHT = SERVER_BUILDER.comment("Minimum height for the Runestone").defineInRange("oregen.RunestoneMinHeight", 0, 1, 256);
		RUNESTONE_MAX_HEIGHT = SERVER_BUILDER.comment("Maximum height for the Runestone").defineInRange("oregen.RunestoneMaxHeight", 25, 1, 256);

		GENERATE_DARK_RUNESTONE = SERVER_BUILDER.comment("Generate Dark Runestone").define("oregen.generateDarkRunestone", true);
		DARK_RUNESTONE_MAX_VEIN_SIZE = SERVER_BUILDER.comment("Maximum size of Dark Runestone vein").defineInRange("oregen.maxDarkRunestoneVeinSize", 3, 0, 500);
		DARK_RUNESTONE_COUNT = SERVER_BUILDER.comment("Maximum Dark Runestone veins per chunk").defineInRange("oregen.DarkRunestoneCount", 3, 1, 10000000);
		DARK_RUNESTONE_MIN_HEIGHT = SERVER_BUILDER.comment("Minimum height for the Dark Runestone").defineInRange("oregen.DarkRunestoneMinHeight", 0, 1, 256);
		DARK_RUNESTONE_MAX_HEIGHT = SERVER_BUILDER.comment("Maximum height for the Dark Runestone").defineInRange("oregen.DarkRunestoneMaxHeight", 9, 1, 256);

		GENERATE_DARK_STONE = SERVER_BUILDER.comment("Generate Dark Stone").define("oregen.generateDarkStone", true);
		DARK_STONE_MAX_VEIN_SIZE = SERVER_BUILDER.comment("Maximum size of Dark Stone vein").defineInRange("oregen.maxDarkStoneVeinSize", 20, 0, 500);
		DARK_STONE_COUNT = SERVER_BUILDER.comment("Maximum Dark Stone veins per chunk").defineInRange("oregen.DarkStoneCount", 20, 1, 10000000);
		DARK_STONE_MIN_HEIGHT = SERVER_BUILDER.comment("Minimum height for the Dark Stone").defineInRange("oregen.DarkStoneMinHeight", 0, 1, 256);
		DARK_STONE_MAX_HEIGHT = SERVER_BUILDER.comment("Maximum height for the Dark Stone").defineInRange("oregen.DarkStoneMaxHeight", 8, 1, 256);

		GENERATE_CHERRYWOOD_TREES = SERVER_BUILDER.comment("Generate Cherrywood Trees").define("worldgen.generateCherrywood", true);
		GENERATE_MYSTERYWOOD_TREES = SERVER_BUILDER.comment("Generate Mysterywood Trees").define("worldgen.generateMysterywood", true);
		GENERATE_EDELWOOD = SERVER_BUILDER.comment("Generate Edelwood Trees").define("worldgen.generateEdelwood", true);
		GENERATE_YELLOW_ORCHID = SERVER_BUILDER.comment("Generate Yellow Orchid").define("worldgen.generateYellowOrchid", true);
	}
}
