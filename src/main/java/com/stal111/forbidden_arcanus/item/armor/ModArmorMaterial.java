package com.stal111.forbidden_arcanus.item.armor;

import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements IArmorMaterial {
	DRACO_ARCANUS(Main.MOD_ID + ":draco_arcanus", 40, new int[]{6, 8, 10, 6}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 3.0F, () -> {
	      return Ingredient.fromItems(ModItems.dragon_scale);
	}),
	TYR(Main.MOD_ID + ":tyr", 50, new int[]{8, 10, 12, 7}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 4.0F, () -> {
		return Ingredient.fromItems(ModItems.golden_dragon_scale, ModItems.aquatic_dragon_scale);
	}),
	MORTEM(Main.MOD_ID + ":mortem", 40, new int[]{1, 4, 5, 1}, 6, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, () -> {
		return Ingredient.fromItems(ModItems.skull);
	}),
	ARCANE_GOLD(Main.MOD_ID + ":arcane_gold", 38, new int[]{3, 6, 7, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0F, () -> {
		return Ingredient.fromItems(ModItems.arcane_gold_ingot);
	}),
	OBSIDIAN(Main.MOD_ID + ":obsidian", 36, new int[]{4, 3, 3, 4}, 5, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5F, () -> {
		return Ingredient.fromItems(ModItems.obsidian_ingot);
	});
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

	private String name;
	private int maxDamageFactor;
	private int[] damageReductionAmountArray;
	private int enchantability;
	private SoundEvent soundEvent;
	private float toughness;
	private LazyLoadBase<Ingredient> repairMaterial;

	private ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
			SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyLoadBase<>(repairMaterial);
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
