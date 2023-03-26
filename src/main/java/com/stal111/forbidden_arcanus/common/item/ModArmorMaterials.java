package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Mod Armor Materials <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ModArmorMaterials
 *
 * @author stal111
 * @since 2021-12-11
 */
public enum ModArmorMaterials implements ArmorMaterial {
    DRACO_ARCANUS("draco_arcanus", 40, 6, 8, 10, 6, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 3.0F, 0.1F, () -> Ingredient.of(ModItems.DRAGON_SCALE.get())),
    TYR("tyr", 50, 8, 10, 12, 7, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 4.0F, 0.2F, () -> Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get(), ModItems.AQUATIC_DRAGON_SCALE.get())),
    MORTEM("mortem", 40, 1, 4, 5, 1, 6, SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 0.0F, () -> Ingredient.of(ModItems.CLOTH.get()));

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final ResourceLocation name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int helmetProtection, int chestPlateProtection, int leggingsProtection, int bootsProtection, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredientSupplier) {
        this.name = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, helmetProtection);
            map.put(ArmorItem.Type.LEGGINGS, chestPlateProtection);
            map.put(ArmorItem.Type.CHESTPLATE, leggingsProtection);
            map.put(ArmorItem.Type.HELMET, bootsProtection);
        });
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = ingredientSupplier;
    }

    @Override
    public int getDurabilityForType(ArmorItem.@NotNull Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.@NotNull Type type) {
        return this.protectionFunctionForType.get(type);
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
