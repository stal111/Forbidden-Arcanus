package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Mod Armor Materials <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ModArmorMaterials
 *
 * @author stal111
 * @since 2021-12-11
 */
public enum ModArmorMaterials implements ArmorMaterial {
    DRACO_ARCANUS("draco_arcanus", 40, new int[]{6, 8, 10, 6}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 3.0F, 0.1F, () -> Ingredient.of(ModItems.DRAGON_SCALE.get())),
    TYR("tyr", 50, new int[]{8, 10, 12, 7}, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 4.0F, 0.2F, () -> Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get(), ModItems.AQUATIC_DRAGON_SCALE.get())),
    MORTEM("mortem", 40, new int[]{1, 4, 5, 1}, 6, SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 0.0F, () -> Ingredient.of(ModItems.CLOTH.get())),
    DEORUM("deorum", 38, new int[]{4, 6, 8, 4}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 1.5F, 0.0F, () -> Ingredient.of(ModItems.DEORUM_INGOT.get()));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final ResourceLocation name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredientSupplier) {
        this.name = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = ingredientSupplier;
    }

    @Override
    public int getDurabilityForSlot(@Nonnull EquipmentSlot slot) {
        return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(@Nonnull EquipmentSlot slot) {
        return this.slotProtections[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name.toString();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
