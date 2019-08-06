package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.item.armor.BasicArmor;
import com.stal111.forbidden_arcanus.item.armor.ModArmorMaterial;
import com.stal111.forbidden_arcanus.item.tool.InfernumPickaxeItem;
import com.stal111.forbidden_arcanus.item.tool.ModAxeItem;
import com.stal111.forbidden_arcanus.item.tool.DracoArcanusScepterItem;
import com.stal111.forbidden_arcanus.item.tool.ModHoeItem;
import com.stal111.forbidden_arcanus.item.tool.MultiToolItem;
import com.stal111.forbidden_arcanus.item.tool.SlimecPickaxeItem;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.item.tool.ModPickaxeItem;
import com.stal111.forbidden_arcanus.item.tool.ModShovelItem;
import com.stal111.forbidden_arcanus.item.tool.ModSwordItem;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Properties;

@ObjectHolder(Main.MOD_ID)
public class ModItems {

	public static final Item
			arcane_gold_ingot = null,
			arcane_gold_nugget = null,
			orb_of_temporary_flight = null,
			chorus_pearl = null,
			spectral_eye_amulet = null,
			soul = null,
			dark_soul = null,
			pixi = null,
			corrupt_pixi = null,
			arcane_crystal = null,
			arcane_crystal_dust = null,
			mundabitur_dust = null,
			corrupti_dust = null,
			dark_matter = null,
			ender_pearl_fragment = null,
			rune = null,
			dark_rune = null,
			rune_bag = null,
			dark_rune_bag = null,
			skull = null,
			obsidian_skull = null,
			obsidian_skull_shield = null,
			obsidian_with_iron = null,
			obsidian_ingot = null,
			dark_nether_star = null,
			dragon_scale = null,
			silver_dragon_scale = null,
			golden_dragon_scale = null,
			aquatic_dragon_scale = null,
			rotten_leather = null,
			leather_of_the_sea = null,
			cherry_peach = null,
			cloth = null,
			golden_feather = null,
			golden_orchid_seeds = null,
			seed_bullet = null,
			aku_aku = null,
			golden_aku_aku = null,
			edelwood_stick = null,
			infernum_pickaxe = null,
			slimec_pickaxe = null,
			runic_battleaxe = null,
			sacred_scepter = null,
			draco_arcanus_staff = null,
			draco_arcanus_sword = null,
			draco_arcanus_shovel = null,
			draco_arcanus_pickaxe = null,
			draco_arcanus_axe = null,
			draco_arcanus_hoe = null,
			draco_arcanus_scepter = null,
			arcane_golden_sword = null,
			arcane_golden_shovel = null,
			arcane_golden_pickaxe = null,
			arcane_golden_axe = null,
			arcane_golden_hoe = null,
			reinforced_arcane_golden_sword = null,
			reinforced_arcane_golden_shovel = null,
			reinforced_arcane_golden_pickaxe = null,
			reinforced_arcane_golden_axe = null,
			reinforced_arcane_golden_hoe = null,
			obsidian_sword = null,
			obsidian_shovel = null,
			obsidian_pickaxe = null,
			obsidian_axe = null,
			obsidian_hoe = null,
			battle_skull = null,
			bone_sword = null,
			bone_shovel = null,
			bone_pickaxe = null,
			bone_axe = null,
			bone_hoe = null,
			draco_arcanus_helmet = null,
			draco_arcanus_chestplate = null,
			draco_arcanus_leggings = null,
			draco_arcanus_boots = null,
			tyr_helmet = null,
			tyr_chestplate = null,
			tyr_leggings = null,
			tyr_boots = null,
			mortem_helmet = null,
			mortem_chestplate = null,
			mortem_leggings = null,
			mortem_boots = null,
			arcane_gold_helmet = null,
			arcane_gold_chestplate = null,
			arcane_gold_leggings = null,
			arcane_gold_boots = null,
			obsidian_helmet = null,
			obsidian_shoulder_pads = null,
			obsidian_knee_pads = null,
			obsidian_boots = null;

	public static void register(RegistryEvent.Register<Item> registry) {
		register("arcane_gold_ingot", new Item(properties()));
		register("arcane_gold_nugget", new Item(properties()));
		register("orb_of_temporary_flight", new Item(properties()));
		register("chorus_pearl", new Item(properties()));
		register("spectral_eye_amulet", new Item(properties()));
		register("soul", new Item(properties()));
		register("dark_soul", new Item(properties()));
		register("pixi", new Item(properties()));
		register("corrupt_pixi", new Item(properties()));
		register("arcane_crystal", new Item(properties()));
		register("arcane_crystal_dust", new Item(properties()));
		register("mundabitur_dust", new Item(properties()));
		register("corrupti_dust", new Item(properties()));
		registerAll(registry,
//				register("arcane_crystal_dust", new BlockNamedItem(ModBlocks.arcane_crystal_dust_wire, new Item.Properties().group(Main.FORBIDDEN_ARCANUS))),
				new BasicItem("dark_matter"),
				new BasicItem("ender_pearl_fragment"),
				new BasicItem("rune"),
				new BasicItem("dark_rune"),
				new BasicItem("rune_bag"),
				new BasicItem("dark_rune_bag"),
				new BasicItem("skull"),
				new ObsidianSkullItem("obsidian_skull"),
				new ObsidianSkullShieldItem("obsidian_skull_shield"),
				new BasicItem("obsidian_with_iron"),
				new BasicItem("obsidian_ingot"),
				new EnchantmentGlintItem("dark_nether_star"),
				new BasicItem("dragon_scale"),
				new BasicItem("silver_dragon_scale"),
				new BasicItem("golden_dragon_scale"),
				new BasicItem("aquatic_dragon_scale"),
				new BasicItem("rotten_leather"),
				new BasicItem("leather_of_the_sea"),
				new BasicItem("cherry_peach"),
				new BasicItem("cloth"),
				new BasicItem("golden_feather"));
		register("golden_orchid_seeds", new GoldenOrchidItem(ModBlocks.golden_orchid, properties()));
		registerAll(registry,
				new SeedBulletItem("seed_bullet"),
				new BasicItem("aku_aku"),
				new BasicItem("golden_aku_aku"),
				new BasicItem("edelwood_stick"),
				new InfernumPickaxeItem("infernum_pickaxe", ItemTier.DIAMOND, 1, -2.8F),
				new SlimecPickaxeItem("slimec_pickaxe", ItemTier.DIAMOND, 1, -2.5F),
				new ModAxeItem("runic_battleaxe", ItemTier.DIAMOND, 1, -2.8F),
				new BasicItem("sacred_scepter"),
				new BasicItem("draco_arcanus_staff"),
				new ModSwordItem("draco_arcanus_sword", ModItemTier.DRACO_ARCANUS, 4, -2.2F),
				new ModShovelItem("draco_arcanus_shovel", ModItemTier.DRACO_ARCANUS, 2.5F, -2.8F),
				new ModPickaxeItem("draco_arcanus_pickaxe", ModItemTier.DRACO_ARCANUS, 2, -2.6F),
				new ModAxeItem("draco_arcanus_axe", ModItemTier.DRACO_ARCANUS, 6.0F, -2.8F),
				new ModHoeItem("draco_arcanus_hoe", ModItemTier.DRACO_ARCANUS, 0.0F),
				new DracoArcanusScepterItem("draco_arcanus_scepter"),
				new ModSwordItem("arcane_golden_sword", ModItemTier.ARCANE_GOLDEN, 3, -2.4F),
				new ModShovelItem("arcane_golden_shovel", ModItemTier.ARCANE_GOLDEN, 1.5F, -3.0F),
				new ModPickaxeItem("arcane_golden_pickaxe", ModItemTier.ARCANE_GOLDEN, 1, -2.8F),
				new ModAxeItem("arcane_golden_axe", ModItemTier.ARCANE_GOLDEN, 5, -3.0F),
				new ModHoeItem("arcane_golden_hoe", ModItemTier.ARCANE_GOLDEN, 0.0F),
				new ModSwordItem("reinforced_arcane_golden_sword", ModItemTier.REINFORCED_ARCANE_GOLDEN, 3, -2.4F),
				new ModShovelItem("reinforced_arcane_golden_shovel", ModItemTier.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F),
				new ModPickaxeItem("reinforced_arcane_golden_pickaxe", ModItemTier.REINFORCED_ARCANE_GOLDEN, 1, -2.8F),
				new ModAxeItem("reinforced_arcane_golden_axe", ModItemTier.REINFORCED_ARCANE_GOLDEN, 5, -3.0F),
				new ModHoeItem("reinforced_arcane_golden_hoe", ModItemTier.REINFORCED_ARCANE_GOLDEN, 0.0F),
				new ModSwordItem("obsidian_sword", ModItemTier.OBSIDIAN, 3, -2.4F),
				new ModShovelItem("obsidian_shovel", ModItemTier.OBSIDIAN, 1.5F, -3.0F),
				new ModPickaxeItem("obsidian_pickaxe", ModItemTier.OBSIDIAN, 1, -2.8F),
				new ModAxeItem("obsidian_axe", ModItemTier.OBSIDIAN, 5, -3.0F),
				new ModHoeItem("obsidian_hoe", ModItemTier.OBSIDIAN, 0.0F),
				new MultiToolItem("battle_skull", 3, -2.4F, ModItemTier.BONE),
				new ModSwordItem("bone_sword", ModItemTier.BONE, 3, -2.4F),
				new ModShovelItem("bone_shovel", ModItemTier.BONE, 1.5F, -3.0F),
				new ModPickaxeItem("bone_pickaxe", ModItemTier.BONE, 1, -2.8F),
				new ModAxeItem("bone_axe", ModItemTier.BONE, 5, -3.0F),
				new ModHoeItem("bone_hoe", ModItemTier.BONE, 0.0F),
				new MysticalDaggerItem("mystical_dagger", ModItemTier.MYSTICAL_DAGGER, 2.5F, -0.8F),
				new BasicArmor("draco_arcanus_helmet", ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.HEAD),
				new BasicArmor("draco_arcanus_chestplate", ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.CHEST),
				new BasicArmor("draco_arcanus_leggings", ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.LEGS),
				new BasicArmor("draco_arcanus_boots", ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.FEET),
				new BasicArmor("tyr_helmet", ModArmorMaterial.TYR, EquipmentSlotType.HEAD),
				new BasicArmor("tyr_chestplate", ModArmorMaterial.TYR, EquipmentSlotType.CHEST),
				new BasicArmor("tyr_leggings", ModArmorMaterial.TYR, EquipmentSlotType.LEGS),
				new BasicArmor("tyr_boots", ModArmorMaterial.TYR, EquipmentSlotType.FEET),
				new BasicArmor("mortem_helmet", ModArmorMaterial.MORTEM, EquipmentSlotType.HEAD),
				new BasicArmor("mortem_chestplate", ModArmorMaterial.MORTEM, EquipmentSlotType.CHEST),
				new BasicArmor("mortem_leggings", ModArmorMaterial.MORTEM, EquipmentSlotType.LEGS),
				new BasicArmor("mortem_boots", ModArmorMaterial.MORTEM, EquipmentSlotType.FEET),
				new BasicArmor("arcane_gold_helmet", ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.HEAD),
				new BasicArmor("arcane_gold_chestplate", ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.CHEST),
				new BasicArmor("arcane_gold_leggings", ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.LEGS),
				new BasicArmor("arcane_gold_boots", ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.FEET),
				new BasicArmor("obsidian_helmet", ModArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD),
				new BasicArmor("obsidian_shoulder_pads", ModArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST),
				new BasicArmor("obsidian_knee_pads", ModArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS),
				new BasicArmor("obsidian_boots", ModArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET));

		ModBlocks.registerItemBlocks(registry);
	}

	private static <T extends Item> T register(String name, T item) {
		item.setRegistryName(ModUtils.location(name));
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	private static Item.Properties properties() {
		return new Item.Properties().group(Main.FORBIDDEN_ARCANUS);
	}

	public static void registerAll(RegistryEvent.Register<Item> registry, Item... items) {
		for (Item item : items) {
			registry.getRegistry().register(item);
		}
	}

}
