package com.stal111.forbidden_arcanus.config;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.*;

@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD, modid= ForbiddenArcanus.MOD_ID)
public class WorldGenConfig {
	public static ForgeConfigSpec.BooleanValue ARCANE_CRYSTAL_ORE_GENERATE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_COUNT;
	public static ForgeConfigSpec.IntValue ARCANE_CRYSTAL_ORE_MAX_HEIGHT;
	private static ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_BLACKLIST;
	private static ForgeConfigSpec.ConfigValue<List<? extends String>> ORE_WHITELIST;

	public static ForgeConfigSpec.BooleanValue RUNESTONE_GENERATE;
	public static ForgeConfigSpec.IntValue RUNESTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue RUNESTONE_COUNT;
	public static ForgeConfigSpec.IntValue RUNESTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue DARKSTONE_GENERATE;
	public static ForgeConfigSpec.IntValue DARKSTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue DARKSTONE_COUNT;
	public static ForgeConfigSpec.IntValue DARKSTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue ARCANE_GILDED_DARKSTONE_GENERATE;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_COUNT;
	public static ForgeConfigSpec.IntValue ARCANE_GILDED_DARKSTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue DARK_RUNESTONE_GENERATE;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_COUNT;
	public static ForgeConfigSpec.IntValue DARK_RUNESTONE_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue XPETRIFIED_ORE_GENERATE;

	public static ForgeConfigSpec.BooleanValue STELLA_ARCANUM_GENERATE;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_MAX_VEIN_SIZE;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_COUNT;
	public static ForgeConfigSpec.IntValue STELLA_ARCANUM_MAX_HEIGHT;

	public static ForgeConfigSpec.BooleanValue CHERRYWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue MYSTERYWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue EDELWOOD_TREE_GENERATE;
	public static ForgeConfigSpec.BooleanValue YELLOW_ORCHID_GENERATE;
	public static ForgeConfigSpec.BooleanValue PETRIFIED_ROOT_GENERATE;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> TREE_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> TREE_BLACKLIST;

	public static ForgeConfigSpec.BooleanValue NIPA_GENERATE;
	public static ForgeConfigSpec.IntValue NIPA_SPACING;
	public static ForgeConfigSpec.IntValue NIPA_SEPARATION;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> NIPA_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<List<? extends String>> NIPA_BLACKLIST;

	public static DimensionList treeList;
	public static DimensionList oreList;
	public static DimensionList nipaList;

	public static void init(ForgeConfigSpec.Builder builder) {
		builder.push("world_gen");

		ORE_WHITELIST = builder.comment("Which dimensions ores should spawn in? [example: [\"minecraft:overworld\"], default empty allows all dimensions]").defineList("ore_whitelist", Collections.emptyList(), o -> o instanceof String && ((String) o).contains(":"));
		ORE_BLACKLIST = builder.comment("Which dimensions ores shouldn't spawn in? [example: [\"minecraft:overworld\"], default empty allows all dimensions]").defineList("ore_blacklist", Collections.emptyList(), o -> o instanceof String && ((String) o).contains(":"));

		ARCANE_CRYSTAL_ORE_GENERATE = builder.comment("Generate Arcane Crystal Ore? [default: true]").define("arcane_crystal.generate", true);
		ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE = builder.comment("Maximum size of Arcane Crystal Ore veins [default: 6]").defineInRange("arcane_crystal.max_vein_size", 5, 0, 100);
		ARCANE_CRYSTAL_ORE_COUNT = builder.comment("Maximum Arcane Crystal Ore veins per chunk [default: 3]").defineInRange("arcane_crystal.count", 3, 1, 100);
		ARCANE_CRYSTAL_ORE_MAX_HEIGHT = builder.comment("Maximum height for Arcane Crystal Ore to generate [default: 25]").defineInRange("arcane_crystal.max_height", 25, 1, 256);

		RUNESTONE_GENERATE = builder.comment("Generate Runestone? [default: true]").define("runestone.generate", true);
		RUNESTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Runestone veins [default: 3]").defineInRange("runestone.max_vein_size", 3, 0, 100);
		RUNESTONE_COUNT = builder.comment("Maximum Runestone veins per chunk [default: 2]").defineInRange("runestone.count", 2, 1, 100);
		RUNESTONE_MAX_HEIGHT = builder.comment("Maximum height for the Runestone to generate [default: 20]").defineInRange("runestone.max_height", 20, 1, 256);

		DARKSTONE_GENERATE = builder.comment("Generate Darkstone? [default: true]").define("darkstone.generate", true);
		DARKSTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Darkstone veins [default: 20]").defineInRange("darkstone.max_vein_size", 20, 0, 100);
		DARKSTONE_COUNT = builder.comment("Maximum Darkstone veins per chunk [default: 20]").defineInRange("darkstone.count", 20, 1, 100);
		DARKSTONE_MAX_HEIGHT = builder.comment("Maximum height for Darkstone to generate [default: 8]").defineInRange("darkstone.max_height", 8, 1, 256);

		ARCANE_GILDED_DARKSTONE_GENERATE = builder.comment("Generate Arcane Gilded Darkstone? [default: true]").define("arcane_gilded_darkstone.generate", true);
		ARCANE_GILDED_DARKSTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Arcane Gilded Darkstone veins [default: 4]").defineInRange("arcane_gilded_darkstone.max_vein_size", 4, 0, 100);
		ARCANE_GILDED_DARKSTONE_COUNT = builder.comment("Maximum Arcane Gilded Darkstone veins per chunk [default: 4]").defineInRange("arcane_gilded_darkstone.count", 4, 1, 100);
		ARCANE_GILDED_DARKSTONE_MAX_HEIGHT = builder.comment("Maximum height for Arcane Gilded Darkstone to generate [default: 8]").defineInRange("arcane_gilded_darkstone.max_height", 8, 1, 256);

		DARK_RUNESTONE_GENERATE = builder.comment("Generate Dark Runestone? [default: true]").define("dark_runestone.generate", true);
		DARK_RUNESTONE_MAX_VEIN_SIZE = builder.comment("Maximum size of Dark Runestone veins [default: 3]").defineInRange("dark_runestone.max_vein_size", 3, 0, 100);
		DARK_RUNESTONE_COUNT = builder.comment("Maximum Dark Runestone veins per chunk [default: 2]").defineInRange("dark_runestone.count", 2, 1, 100);
		DARK_RUNESTONE_MAX_HEIGHT = builder.comment("Maximum height for Dark Runestone to generate [default: 8]").defineInRange("dark_runestone.max_height", 8, 1, 256);

		XPETRIFIED_ORE_GENERATE = builder.comment("Generate Xpetrified Ore? [default: true]").define("xpetrified_ore.generate", true);

		STELLA_ARCANUM_GENERATE = builder.comment("Generate Should Stella Arcanum? [default: true]").define("stella_arcanum.generate", true);
		STELLA_ARCANUM_MAX_VEIN_SIZE = builder.comment("Maximum size of Stella Arcanum veins [default: 3]").defineInRange("stella_arcanum.max_vein_size", 3, 0, 100);
		STELLA_ARCANUM_COUNT = builder.comment("Maximum Stella Arcanum veins per chunk [default: 2]").defineInRange("stella_arcanum.count", 2, 1, 100);
		STELLA_ARCANUM_MAX_HEIGHT = builder.comment("Maximum height for Stella Arcanum to generate [default: 50]").defineInRange("stella_arcanum.max_height", 50, 1, 256);

		CHERRYWOOD_TREE_GENERATE = builder.comment("Generate Cherrywood Trees? [default: true]").define("cherrywood_tree.generate", true);
		MYSTERYWOOD_TREE_GENERATE = builder.comment("Generate Mysterywood Trees? [default: true]").define("mysterywood_tree.generate", true);
		EDELWOOD_TREE_GENERATE = builder.comment("Generate Edelwood Trees? [default: true]").define("edelwood_tree.generate", true);
		YELLOW_ORCHID_GENERATE = builder.comment("Generate Yellow Orchids? [default: true]").define("yellow_orchid.generate", true);
		PETRIFIED_ROOT_GENERATE = builder.comment("Generate Petrified Roots? [default: true]").define("petrified_root.generate", true);
		TREE_WHITELIST = builder.comment("Which dimensions trees should spawn in? [example: [\"minecraft:the_end\"], default empty allows all dimensions]").defineList("tree_whitelist", Collections.emptyList(), o -> o instanceof String && ((String) o).contains(":"));
		TREE_BLACKLIST = builder.comment("Which dimensions trees shouldn't spawn in? [example: [\"minecraft:the_end\"], empty allows all dimensions]").defineList("tree_blacklist", Collections.singletonList("minecraft:the_end"), o -> o instanceof String && ((String) o).contains(":"));

		NIPA_GENERATE = builder.comment("Generate Nipas? [default: true]").define("nipa.generate", true);
		NIPA_SPACING = builder.comment("Nipa Structure Spacing [default: 35]").defineInRange("nipa.spacing", 35, 0, Integer.MAX_VALUE);
		NIPA_SEPARATION = builder.comment("Nipa Structure Separation [default: 8]").defineInRange("nipa.separation", 8, 0, Integer.MAX_VALUE);
		NIPA_WHITELIST = builder.comment("Which dimensions nipa structures should spawn in? [example: [\"minecraft:the_end\"], default empty allows all dimensions]").defineList("nipa_whitelist", Collections.emptyList(), o -> o instanceof String && ((String) o).contains(":"));
		NIPA_BLACKLIST = builder.comment("Which dimensions nipa structures  shouldn't spawn in? [example: [\"minecraft:the_end\"], empty allows all dimensions]").defineList("nipa_blacklist", Collections.singletonList("minecraft:the_end"), o -> o instanceof String && ((String) o).contains(":"));

		treeList = new DimensionList(TREE_WHITELIST, TREE_BLACKLIST);
		oreList = new DimensionList(ORE_WHITELIST, ORE_BLACKLIST);
		nipaList = new DimensionList(NIPA_WHITELIST, NIPA_BLACKLIST);

		builder.pop();
	}

	@SubscribeEvent
	public static void onConfig (ModConfig.ModConfigEvent event) {
		if (treeList != null) {
			treeList.invalidate();
		}
		if (oreList != null) {
			oreList.invalidate();
		}
		if (nipaList != null) {
			nipaList.invalidate();
		}
	}

	public static class DimensionList {
		private Set<RegistryKey<World>> whitelist = null;
		private Set<RegistryKey<World>> blacklist = null;
		private final ForgeConfigSpec.ConfigValue<List<? extends String>> whitelistRoot;
		private final ForgeConfigSpec.ConfigValue<List<? extends String>> blacklistRoot;

		public DimensionList(ForgeConfigSpec.ConfigValue<List<? extends String>> whitelistRoot, ForgeConfigSpec.ConfigValue<List<? extends String>> blacklistRoot) {
			this.whitelistRoot = whitelistRoot;
			this.blacklistRoot = blacklistRoot;
		}

		private void resolveList() {
			if (whitelist == null) {
				whitelist = new HashSet<>();
				for (String dim : whitelistRoot.get()) {
					whitelist.add(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(dim)));
				}
			}
			if (blacklist == null) {
				blacklist = new HashSet<>();
				for (String dim : blacklistRoot.get()) {
					blacklist.add(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(dim)));
				}
			}
		}

		public void invalidate() {
			whitelist = null;
		}


		public boolean allowed(RegistryKey<World> dimension) {
			resolveList();
			if (whitelist.contains(dimension)) {
				return true;
			} else {
				return whitelist.isEmpty() && !blacklist.contains(dimension);
			}
		}

		public Set<RegistryKey<World>> getWhitelist() {
			resolveList();
			return whitelist;
		}

		public Set<RegistryKey<World>> getBlacklist() {
			resolveList();
			return blacklist;
		}
	}
}