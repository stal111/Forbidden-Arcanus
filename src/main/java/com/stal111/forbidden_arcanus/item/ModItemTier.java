package com.stal111.forbidden_arcanus.item;

import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

public enum ModItemTier implements Tier {
	DRACO_ARCANUS(4, 2661, 12.0F, 7.0F, 20, () -> {
		return Ingredient.of(ModItems.DRAGON_SCALE.get());
	}),
	ARCANE_GOLDEN(3, 1861, 9.0F, 3.5F, 26, () -> {
		return Ingredient.of(ModItems.ARCANE_GOLD_INGOT.get());
	}),
	REINFORCED_ARCANE_GOLDEN(3, 2561, 9.0F, 3.5F, 26, () -> {
		return Ingredient.of(ModItems.ARCANE_GOLD_INGOT.get());
	}),
	BONE(1, 131, 4.0F, 1.0F, 5, () -> {
		return Ingredient.of(Items.BONE);
	}),
	SLIMEC(3, 2061, 13.0F, 2.5F, 20, () -> {
		return Ingredient.of(Items.BONE);
	}),
	MYSTICAL_DAGGER(1, 561, 4.0F, 1.0F, 5, () -> {
		return Ingredient.of(ModItems.DARK_RUNE.get());
	});

	private int harvestLevel;
	private int maxUses;
	private float efficiency;
	private float attackDamage;
	private int enchantability;
	private LazyLoadedValue<Ingredient> repairMaterial;

	ModItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
			int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
	}

	@Override
	public int getUses() {
		return this.maxUses;
	}

	public void setMaxUses(int maxUses) {
		this.maxUses = maxUses;
	}

	@Override
	public float getSpeed() {
		return this.efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return this.attackDamage;
	}

	@Override
	public int getLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
	}

}
