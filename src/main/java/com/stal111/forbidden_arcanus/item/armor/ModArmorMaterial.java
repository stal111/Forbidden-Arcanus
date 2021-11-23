package com.stal111.forbidden_arcanus.item.armor;

import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum ModArmorMaterial implements ArmorMaterial {
	DRACO_ARCANUS(ForbiddenArcanus.MOD_ID + ":draco_arcanus", 40, new int[]{6, 8, 10, 6}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 3.0F, () -> {
	      return Ingredient.of(ModItems.DRAGON_SCALE.get());
	}),
	TYR(ForbiddenArcanus.MOD_ID + ":tyr", 50, new int[]{8, 10, 12, 7}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 4.0F, () -> {
		return Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get(), ModItems.AQUATIC_DRAGON_SCALE.get());
	}),
	MORTEM(ForbiddenArcanus.MOD_ID + ":mortem", 40, new int[]{1, 4, 5, 1}, 6, SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, () -> {
		return Ingredient.of(ModItems.CLOTH.get());
	}),
	ARCANE_GOLD(ForbiddenArcanus.MOD_ID + ":arcane_gold", 38, new int[]{4, 6, 8, 4}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 1.5F, () -> {
		return Ingredient.of(ModItems.ARCANE_GOLD_INGOT.get());
	});
	
	private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

	private String name;
	private int maxDamageFactor;
	private int[] damageReductionAmountArray;
	private int enchantability;
	private SoundEvent soundEvent;
	private float toughness;
	private LazyLoadedValue<Ingredient> repairMaterial;

	ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
					 SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial) {
		this.name = name;
		this.maxDamageFactor = maxDamageFactor;
		this.damageReductionAmountArray = damageReductionAmountArray;
		this.enchantability = enchantability;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.damageReductionAmountArray[slot.getIndex()];
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return MAX_DAMAGE_ARRAY[slot.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getEnchantmentValue() {
		 return this.enchantability;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}
	
	@Override
	public SoundEvent getEquipSound() {
		return soundEvent;
	}

	@Override
	public float getToughness() {
		 return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}
