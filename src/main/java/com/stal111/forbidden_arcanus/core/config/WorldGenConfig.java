package com.stal111.forbidden_arcanus.core.config;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ForbiddenArcanus.MOD_ID)
public class WorldGenConfig {
	public static ForgeConfigSpec.BooleanValue ARCANE_CRYSTAL_ORE_GENERATE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_COUNT;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue RUNIC_STONE_GENERATE;
	public static ForgeConfigSpec.IntValue RUNIC_STONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue RUNIC_STONE_COUNT;
	public static ForgeConfigSpec.IntValue RUNIC_STONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue RUNIC_STONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue DARKSTONE_GENERATE;
	public static ForgeConfigSpec.IntValue DARKSTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue DARKSTONE_COUNT;
	public static ForgeConfigSpec.IntValue DARKSTONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue DARKSTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue ARCANE_GILDED_DARKSTONE_GENERATE;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_COUNT;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue XPETRIFIED_ORE_GENERATE;
	public static ForgeConfigSpec.IntValue XPETRIFIED_ORE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue XPETRIFIED_ORE_COUNT;
	public static ForgeConfigSpec.IntValue XPETRIFIED_ORE_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue XPETRIFIED_ORE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_GENERATE;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_COUNT;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_MIN_HEIGHT;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue CHERRYWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue MYSTERYWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue EDELWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue YELLOW_ORCHID_GENERATE;
	public static ForgeConfigSpec.BooleanValue PETRIFIED_ROOT_GENERATE;

	public static ForgeConfigSpec.BooleanValue NIPA_GENERATE;
	public static ForgeConfigSpec.IntValue NIPA_SPACING;
	public static ForgeConfigSpec.IntValue NIPA_SEPARATION;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder.push("world_gen");

		ARCANE_CRYSTAL_ORE_GENERATE = builder.comment("Generate Arcane Crystal Ore? [default: true]").define("arcane_crystal.generate", true);
		ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE = builder.comment("Maximum size of Arcane Crystal Ore veins [default: 6]").defineInRange("arcane_crystal.max_vein_size", 5, 0, 100);
		ARCANE_CRYSTAL_ORE_COUNT = builder.comment("Maximum Arcane Crystal Ore veins per chunk [default: 3]").defineInRange("arcane_crystal.count", 3, 1, 100);
		ARCANE_CRYSTAL_ORE_MIN_HEIGHT = builder.comment("Minimum height for Arcane Crystal Ore to generate [default: -40]").defineInRange("arcane_crystal.min_height", -40, Integer.MIN_VALUE, Integer.MAX_VALUE);
		ARCANE_CRYSTAL_ORE_MAX_HEIGHT = builder.comment("Maximum height for Arcane Crystal Ore to generate [default: 14]").defineInRange("arcane_crystal.max_height", 14, Integer.MIN_VALUE, Integer.MAX_VALUE);

		RUNIC_STONE_GENERATE = builder.comment("Generate Runestone? [default: true]").define("runestone.generate", true);
		RUNIC_STONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Runestone veins [default: 3]").defineInRange("runestone.max_vein_size", 3, 0, 100);
		RUNIC_STONE_COUNT = builder.comment("Maximum Runestone veins per chunk [default: 3]").defineInRange("runestone.count", 3, 1, 100);
		RUNIC_STONE_MIN_HEIGHT = builder.comment("Minimum height for the Runestone to generate [default: -64]").defineInRange("runestone.min_height", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
		RUNIC_STONE_MAX_HEIGHT = builder.comment("Maximum height for the Runestone to generate [default: 2]").defineInRange("runestone.max_height", 2, Integer.MIN_VALUE, Integer.MAX_VALUE);

		DARKSTONE_GENERATE = builder.comment("Generate Darkstone? [default: true]").define("darkstone.generate", true);
		DARKSTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Darkstone veins [default: 20]").defineInRange("darkstone.max_vein_size", 20, 0, 100);
		DARKSTONE_COUNT = builder.comment("Maximum Darkstone veins per chunk [default: 28]").defineInRange("darkstone.count", 28, 1, 100);
		DARKSTONE_MIN_HEIGHT = builder.comment("Minimum height for Darkstone to generate [default: -64]").defineInRange("darkstone.min_height", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
		DARKSTONE_MAX_HEIGHT = builder.comment("Maximum height for Darkstone to generate [default: -51]").defineInRange("darkstone.max_height", -51, Integer.MIN_VALUE, Integer.MAX_VALUE);

		ARCANE_GILDED_DARKSTONE_GENERATE = builder.comment("Generate Arcane Gilded Darkstone? [default: true]").define("arcane_gilded_darkstone.generate", true);
		ARCANE_GILDED_DARKSTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Arcane Gilded Darkstone veins [default: 4]").defineInRange("arcane_gilded_darkstone.max_vein_size", 4, 0, 100);
		ARCANE_GILDED_DARKSTONE_COUNT = builder.comment("Maximum Arcane Gilded Darkstone veins per chunk [default: 4]").defineInRange("arcane_gilded_darkstone.count", 4, 1, 100);
		ARCANE_GILDED_DARKSTONE_MIN_HEIGHT = builder.comment("Mimumum height for Arcane Gilded Darkstone to generate [default: -64]").defineInRange("arcane_gilded_darkstone.min_height", -64, Integer.MIN_VALUE, Integer.MAX_VALUE);
		ARCANE_GILDED_DARKSTONE_MAX_HEIGHT = builder.comment("Maximum height for Arcane Gilded Darkstone to generate [default: -51]").defineInRange("arcane_gilded_darkstone.max_height", -51, Integer.MIN_VALUE, Integer.MAX_VALUE);

		XPETRIFIED_ORE_GENERATE = builder.comment("Generate Xpetrified Ore? [default: true]").define("xpetrified_ore.generate", true);
		XPETRIFIED_ORE_MAX_VEIN_SIZE = builder.comment("Maximum size of Xpetrified Ore veins [default: 3]").defineInRange("xpetrified_ore.max_vein_size", 3, 0, 100);
		XPETRIFIED_ORE_COUNT = builder.comment("Maximum Xpetrified Ore veins per chunk [default: 18]").defineInRange("xpetrified_ore.count", 18, 1, 100);
		XPETRIFIED_ORE_MIN_HEIGHT = builder.comment("Minumum height for Xpetrified Ore to generate [default: -6]").defineInRange("xpetrified_ore.min_height", -6, Integer.MIN_VALUE, Integer.MAX_VALUE);
		XPETRIFIED_ORE_MAX_HEIGHT = builder.comment("Maximum height for Xpetrified Ore to generate [default: 35]").defineInRange("xpetrified_ore.max_height", 35, Integer.MIN_VALUE, Integer.MAX_VALUE);

		STELLA_ARCANUM_GENERATE = builder.comment("Generate Should Stella Arcanum? [default: true]").define("stella_arcanum.generate", true);
		STELLA_ARCANUM_MAX_VEIN_SIZE = builder.comment("Maximum size of Stella Arcanum veins [default: 3]").defineInRange("stella_arcanum.max_vein_size", 3, 0, 100);
		STELLA_ARCANUM_COUNT = builder.comment("Maximum Stella Arcanum veins per chunk [default: 2]").defineInRange("stella_arcanum.count", 2, 1, 100);
		STELLA_ARCANUM_MIN_HEIGHT = builder.comment("Minimum height for Stella Arcanum to generate [default: -44]").defineInRange("stella_arcanum.min_height", -44, Integer.MIN_VALUE, Integer.MAX_VALUE);
		STELLA_ARCANUM_MAX_HEIGHT = builder.comment("Maximum height for Stella Arcanum to generate [default: 42]").defineInRange("stella_arcanum.max_height", 42, Integer.MIN_VALUE, Integer.MAX_VALUE);

		CHERRYWOOD_TREE_GENERATE = builder.comment("Generate Cherrywood Trees? [default: true]").define("cherrywood_tree.generate", true);
		MYSTERYWOOD_TREE_GENERATE = builder.comment("Generate Mysterywood Trees? [default: true]").define("mysterywood_tree.generate", true);
		EDELWOOD_TREE_GENERATE = builder.comment("Generate Edelwood Trees? [default: true]").define("edelwood_tree.generate", true);
		YELLOW_ORCHID_GENERATE = builder.comment("Generate Yellow Orchids? [default: true]").define("yellow_orchid.generate", true);
		PETRIFIED_ROOT_GENERATE = builder.comment("Generate Petrified Roots? [default: true]").define("petrified_root.generate", true);

		NIPA_GENERATE = builder.comment("Generate Nipas? [default: true]").define("nipa.generate", true);
		NIPA_SPACING = builder.comment("Nipa Structure Spacing [default: 35]").defineInRange("nipa.spacing", 35, 0, Integer.MAX_VALUE);
		NIPA_SEPARATION = builder.comment("Nipa Structure Separation [default: 8]").defineInRange("nipa.separation", 8, 0, Integer.MAX_VALUE);

		builder.pop();
	}
}