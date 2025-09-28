package com.stal111.forbidden_arcanus.common.item.equipment;

import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

/**
 * @author stal111
 * @since 25.04.2024
 */
public class FAArmorMaterials {

    //TODO: tags for repair ingredients
    public static final ArmorMaterial DRACO_ARCANUS = new ArmorMaterial(40, Util.make(new EnumMap<>(ArmorType.class), map ->  {
        map.put(ArmorType.BOOTS, 6);
        map.put(ArmorType.LEGGINGS, 8);
        map.put(ArmorType.CHESTPLATE, 10);
        map.put(ArmorType.HELMET, 6);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 2.0F, 0.0F, ModTags.Items.REPAIRS_DRACO_ARCANUS_ARMOR, FAEquipmentAssets.DRACO_ARCANUS);

    public static final ArmorMaterial TYR = new ArmorMaterial(42, Util.make(new EnumMap<>(ArmorType.class), map ->  {
        map.put(ArmorType.BOOTS, 7);
        map.put(ArmorType.LEGGINGS, 10);
        map.put(ArmorType.CHESTPLATE, 12);
        map.put(ArmorType.HELMET, 8);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 3.0F, 0.1F, ModTags.Items.REPAIRS_TYR_ARMOR, FAEquipmentAssets.TYR);
}
