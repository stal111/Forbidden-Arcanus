package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.item.armor.BasicArmor;
import com.stal111.forbidden_arcanus.item.armor.ModArmorMaterial;
import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Map;

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
			soul_extractor = null,
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
		register("orb_of_temporary_flight", new OrbOfTemporaryFlightItem(properties(Rarity.UNCOMMON).maxStackSize(1)));
		register("chorus_pearl", new ChorusPearlItem(properties().maxStackSize(16)));
		register("spectral_eye_amulet", new SpectralEyeAmuletItem(properties(Rarity.RARE).maxStackSize(1)));
		register("soul", new SoulItem(properties()));
		register("dark_soul", new Item(properties()));
		register("pixi", new PixiItem(properties().maxDamage(256)));
		register("corrupt_pixi", new Item(properties().maxStackSize(1)));
		register("arcane_crystal", new Item(properties()));
		register("arcane_crystal_dust", new Item(properties()));
		register("mundabitur_dust", new Item(properties()));
		register("corrupti_dust", new Item(properties()));
		register("dark_matter", new Item(properties()));
		register("ender_pearl_fragment", new Item(properties()));
		register("rune", new Item(properties()));
		register("dark_rune", new Item(properties()));
		register("rune_bag", new Item(properties()));
		register("dark_rune_bag", new Item(properties()));
		register("skull", new Item(properties()));
		register("obsidian_skull", new ObsidianSkullItem(properties().maxStackSize(1)));
		register("obsidian_skull_shield", new ObsidianSkullShieldItem(properties().maxStackSize(1)));
		register("obsidian_with_iron", new Item(properties()));
		register("obsidian_ingot", new Item(properties()));
		register("dark_nether_star", new EnchantmentGlintItem(properties(Rarity.RARE)));
		register("dragon_scale", new Item(properties()));
		register("silver_dragon_scale", new Item(properties()));
		register("golden_dragon_scale", new Item(properties()));
		register("aquatic_dragon_scale", new Item(properties()));
		register("rotten_leather", new Item(properties()));
		register("leather_of_the_sea", new Item(properties()));
		register("cherry_peach", new Item(properties().food(food(4, 0.4F))));
		register("cloth", new Item(properties()));
		register("golden_feather", new Item(properties()));
		register("golden_orchid_seeds", new GoldenOrchidItem(ModBlocks.golden_orchid, properties()));
		register("seed_bullet", new SeedBulletItem(properties().maxStackSize(16)));
		register("aku_aku", new Item(properties().maxStackSize(1)));
		register("golden_aku_aku", new Item(properties().maxStackSize(1)));
		register("edelwood_stick", new Item(properties()));
		register("soul_extractor", new SoulExtractorItem());
		register("infernum_pickaxe", new InfernumPickaxeItem(ItemTier.DIAMOND, 1, -2.8F, properties()));
		register("slimec_pickaxe", new SlimecPickaxeItem(ItemTier.DIAMOND, 1, -2.5F, properties()));
		register("runic_battleaxe", new AxeItem(ItemTier.DIAMOND, 1, -2.8F, properties()));
		register("sacred_scepter", new Item(properties().maxStackSize(1)));
		register("draco_arcanus_staff", new Item(properties().maxStackSize(1)));
		register("draco_arcanus_sword", new SwordItem(ModItemTier.DRACO_ARCANUS, 4, -2.2F, properties()));
		register("draco_arcanus_shovel", new ShovelItem(ModItemTier.DRACO_ARCANUS, 2.5F, -2.8F, properties()));
		register("draco_arcanus_pickaxe", new PickaxeItem(ModItemTier.DRACO_ARCANUS, 2, -2.6F, properties()));
		register("draco_arcanus_axe", new AxeItem(ModItemTier.DRACO_ARCANUS, 6, -2.8F, properties()));
		register("draco_arcanus_hoe", new HoeItem(ModItemTier.DRACO_ARCANUS, 0, properties()));
		register("draco_arcanus_scepter", new DracoArcanusScepterItem(properties().maxStackSize(1)));
		register("arcane_golden_sword", new SwordItem(ModItemTier.ARCANE_GOLDEN, 3, -2.4F, properties()));
		register("arcane_golden_shovel", new ShovelItem(ModItemTier.ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
		register("arcane_golden_pickaxe", new PickaxeItem(ModItemTier.ARCANE_GOLDEN, 1, -2.8F, properties()));
		register("arcane_golden_axe", new AxeItem(ModItemTier.ARCANE_GOLDEN, 5, -3.0F, properties()));
		register("arcane_golden_hoe", new HoeItem(ModItemTier.ARCANE_GOLDEN, 0.0F, properties()));
		register("reinforced_arcane_golden_sword", new SwordItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 3, -2.4F, properties()));
		register("reinforced_arcane_golden_shovel", new ShovelItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1.5F, -3.0F, properties()));
		register("reinforced_arcane_golden_pickaxe", new PickaxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 1, -2.8F, properties()));
		register("reinforced_arcane_golden_axe", new AxeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN,5, -3.0F, properties()));
		register("reinforced_arcane_golden_hoe", new HoeItem(ModItemTier.REINFORCED_ARCANE_GOLDEN, 0.0F, properties()));
		register("obsidian_sword", new SwordItem(ModItemTier.OBSIDIAN, 3, -2.4F, properties()));
		register("obsidian_shovel", new ShovelItem(ModItemTier.OBSIDIAN, 1.5F, -3.0F, properties()));
		register("obsidian_pickaxe", new PickaxeItem(ModItemTier.OBSIDIAN, 1 , -2.8F, properties()));
		register("obsidian_axe", new AxeItem(ModItemTier.OBSIDIAN, 5, -3.0F, properties()));
		register("obsidian_hoe", new HoeItem(ModItemTier.OBSIDIAN, 0.0F, properties()));
		register("battle_skull", new MultiToolItem(ModItemTier.BONE, 3, -2.4F, properties()));
		register("bone_sword", new BoneSwordItem(ModItemTier.BONE, 3, -2.4F, properties()));
		register("bone_shovel", new BoneShovelItem(ModItemTier.BONE, 1.5F, -3.0F, properties()));
		register("bone_pickaxe", new BonePickaxeItem(ModItemTier.BONE, 1, -2.8F, properties()));
		register("bone_axe", new BoneAxeItem(ModItemTier.BONE, 5, -3.0F, properties()));
		register("bone_hoe", new BoneHoeItem(ModItemTier.BONE, 0.0F, properties()));
		register("mystical_dagger", new MysticalDaggerItem(ModItemTier.MYSTICAL_DAGGER, 2.5F, -0.7F, properties()));
		register("draco_arcanus_helmet", new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.HEAD, properties()));
		register("draco_arcanus_chestplate", new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.CHEST, properties()));
		register("draco_arcanus_leggings", new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.LEGS, properties()));
		register("draco_arcanus_boots", new ArmorItem(ModArmorMaterial.DRACO_ARCANUS, EquipmentSlotType.FEET, properties()));
		register("tyr_helmet", new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.HEAD, properties()));
		register("tyr_chestplate", new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.CHEST, properties()));
		register("tyr_leggings", new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.LEGS, properties()));
		register("tyr_boots", new ArmorItem(ModArmorMaterial.TYR, EquipmentSlotType.FEET, properties()));
		register("mortem_helmet", new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.HEAD, properties()));
		register("mortem_chestplate", new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.CHEST, properties()));
		register("mortem_leggings", new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.LEGS, properties()));
		register("mortem_boots", new ArmorItem(ModArmorMaterial.MORTEM, EquipmentSlotType.FEET, properties()));
		register("arcane_gold_helmet", new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.HEAD, properties()));
		register("arcane_gold_chestplate", new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.CHEST, properties()));
		register("arcane_gold_leggings", new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.LEGS, properties()));
		register("arcane_gold_boots", new ArmorItem(ModArmorMaterial.ARCANE_GOLD, EquipmentSlotType.FEET, properties()));
		register("obsidian_helmet", new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.HEAD, properties()));
		register("obsidian_shoulder_pads", new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.CHEST, properties()));
		register("obsidian_knee_pads", new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.LEGS, properties()));
		register("obsidian_boots", new ArmorItem(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, properties()));
	}

	public static <T extends Item> T register(String name, T item) {
		item.setRegistryName(ModUtils.location(name));
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	public static Item.Properties properties() {
		return properties(Rarity.COMMON);
	}

	public static Item.Properties properties(Rarity rarity) {
		return new Item.Properties().group(Main.FORBIDDEN_ARCANUS).rarity(rarity);
	}

	public static Food food(int hunger, float saturation) {
		return new Food.Builder().hunger(hunger).saturation(saturation).build();
	}
}
