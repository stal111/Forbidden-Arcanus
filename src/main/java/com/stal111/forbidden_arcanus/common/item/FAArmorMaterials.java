package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 25.04.2024
 */
public class FAArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> HELPER = DeferredRegister.create(Registries.ARMOR_MATERIAL, ForbiddenArcanus.MOD_ID);

    public static final Holder<ArmorMaterial> DRACO_ARCANUS = register("draco_arcanus", Util.make(new EnumMap<>(ArmorItem.Type.class), map ->  {
        map.put(ArmorItem.Type.BOOTS, 6);
        map.put(ArmorItem.Type.LEGGINGS, 8);
        map.put(ArmorItem.Type.CHESTPLATE, 10);
        map.put(ArmorItem.Type.HELMET, 6);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(ModItems.DRAGON_SCALE.get()), 2.0F, 0.0F);

    public static final Holder<ArmorMaterial> TYR = register("tyr", Util.make(new EnumMap<>(ArmorItem.Type.class), map ->  {
        map.put(ArmorItem.Type.BOOTS, 7);
        map.put(ArmorItem.Type.LEGGINGS, 10);
        map.put(ArmorItem.Type.CHESTPLATE, 12);
        map.put(ArmorItem.Type.HELMET, 8);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get(), ModItems.AQUATIC_DRAGON_SCALE.get()), 3.0F, 0.1F);

    public static final Holder<ArmorMaterial> MORTEM = register("mortem", Util.make(new EnumMap<>(ArmorItem.Type.class), map ->  {
        map.put(ArmorItem.Type.BOOTS, 1);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 6, SoundEvents.ARMOR_EQUIP_GENERIC, () -> Ingredient.of(ModItems.CLOTH.get()), 1.0F, 0.0F);

    public static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantmentValue, Holder<SoundEvent> sound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResistance) {
        return HELPER.register(name, () -> new ArmorMaterial(protectionFunctionForType, enchantmentValue, sound, repairIngredient, List.of(new ArmorMaterial.Layer(new ResourceLocation(ForbiddenArcanus.MOD_ID, name))), toughness, knockbackResistance));
    }
}
