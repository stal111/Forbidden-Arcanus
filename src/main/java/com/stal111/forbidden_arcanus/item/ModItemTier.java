package com.stal111.forbidden_arcanus.item;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;

public enum ModItemTier implements IItemTier {
	DRACO_ARCANUS(4, 2861, 12.0F, 7.0F, 20, () -> {
		return Ingredient.fromItems(ModItems.dragon_scale);
	}),
	ARCANE_GOLDEN(3, 1861, 8.0F, 3.0F, 2, () -> {
		return Ingredient.fromItems(ModItems.arcane_gold_ingot);
	}),
	REINFORCED_ARCANE_GOLDEN(3, 1861, 8.0F, 3.0F, 2, () -> {
		return Ingredient.fromItems(ModItems.arcane_gold_ingot);
	}),
	OBSIDIAN(3, 1861, 8.0F, 3.0F, 2, () -> {
		return Ingredient.fromItems(ModItems.obsidian_ingot);
	}),
	BONE(1, 131, 4.0F, 1.0F, 5, () -> {
		return Ingredient.fromItems(ModItems.dragon_scale);
	});

	private int harvestLevel;
	private int maxUses;
	private float efficiency;
	private float attackDamage;
	private int enchantability;
	private LazyLoadBase<Ingredient> repairMaterial;

	private ModItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
			int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadBase<>(repairMaterialIn);
	}

	@Override
	public int getMaxUses() {
		return this.maxUses;
	}

	public void setMaxUses(int maxUses) {
		this.maxUses = maxUses;
	}

	@Override
	public float getEfficiency() {
		return this.efficiency;
	}

	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

}
