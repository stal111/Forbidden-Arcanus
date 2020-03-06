package com.stal111.forbidden_arcanus.item.armor;

import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.Main;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements IArmorMaterial {
	DRACO_ARCANUS(Main.MOD_ID + ":draco_arcanus", 40, new int[]{6, 8, 10, 6}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3.0F, () -> {
	      return Ingredient.fromItems(ModItems.DRAGON_SCALE.getItem());
	}),
	TYR(Main.MOD_ID + ":tyr", 50, new int[]{8, 10, 12, 7}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 4.0F, () -> {
		return Ingredient.fromItems(ModItems.GOLDEN_DRAGON_SCALE.getItem(), ModItems.AQUATIC_DRAGON_SCALE.getItem());
	}),
	MORTEM(Main.MOD_ID + ":mortem", 40, new int[]{1, 4, 5, 1}, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, () -> {
		return Ingredient.fromItems(ModItems.CLOTH.getItem());
	}),
	ARCANE_GOLD(Main.MOD_ID + ":arcane_gold", 38, new int[]{4, 6, 8, 4}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.5F, () -> {
		return Ingredient.fromItems(NewModItems.ARCANE_GOLD_INGOT.get());
	}),
	OBSIDIAN(Main.MOD_ID + ":obsidian", 36, new int[]{4, 3, 3, 4}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5F, () -> {
		return Ingredient.fromItems(ModItems.OBSIDIAN_INGOT.getItem());
	});
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

	private String name;
	private int maxDamageFactor;
	private int[] damageReductionAmountArray;
	private int enchantability;
	private SoundEvent soundEvent;
	private float toughness;
	private LazyValue<Ingredient> repairMaterial;

	private ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
			SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyValue<>(repairMaterial);
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slot) {
		return this.damageReductionAmountArray[slot.getIndex()];
	}

	@Override
	public int getDurability(EquipmentSlotType slot) {
		return MAX_DAMAGE_ARRAY[slot.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getEnchantability() {
		 return this.enchantability;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}
	
	@Override
	public SoundEvent getSoundEvent() {
		return soundEvent;
	}

	@Override
	public float getToughness() {
		 return this.toughness;
	}
}
