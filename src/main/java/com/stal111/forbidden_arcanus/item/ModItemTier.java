package com.stal111.forbidden_arcanus.item;

import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum ModItemTier implements IItemTier {
	DRACO_ARCANUS(4, 2661, 12.0F, 7.0F, 20, () -> {
		return Ingredient.fromItems(ModItems.DRAGON_SCALE.getItem());
	}),
	ARCANE_GOLDEN(3, 1861, 8.0F, 3.0F, 26, () -> {
		return Ingredient.fromItems(ModItems.ARCANE_GOLD_INGOT.getItem());
	}),
	REINFORCED_ARCANE_GOLDEN(3, 2561, 8.0F, 3.0F, 26, () -> {
		return Ingredient.fromItems(ModItems.ARCANE_GOLD_INGOT.getItem());
	}),
	OBSIDIAN(3, 2861, 5.5F, 3.0F, 2, () -> {
		return Ingredient.fromItems(ModItems.OBSIDIAN_INGOT.getItem());
	}),
	BONE(1, 131, 4.0F, 1.0F, 5, () -> {
		return Ingredient.fromItems(Items.BONE);
	}),
	INFERNUM(3, 1261, 7.0F, 3.0F, 5, () -> {
		return Ingredient.fromItems(Items.BONE);
	}),
	SLIMEC(3, 2061, 13.0F, 2.5F, 20, () -> {
		return Ingredient.fromItems(Items.BONE);
	}),
	MYSTICAL_DAGGER(1, 561, 4.0F, 1.0F, 5, () -> {
		return Ingredient.fromItems(ModItems.DARK_RUNE.getItem());
	});

	private int harvestLevel;
	private int maxUses;
	private float efficiency;
	private float attackDamage;
	private int enchantability;
	private LazyValue<Ingredient> repairMaterial;

	ModItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
			int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyValue<>(repairMaterialIn);
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
