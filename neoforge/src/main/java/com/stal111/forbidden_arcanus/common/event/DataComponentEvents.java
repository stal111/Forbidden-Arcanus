package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.entity.attribute.FAAttributes;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

/**
 * @author stal111
 * @since 07.05.2024
 */
@EventBusSubscriber
public class DataComponentEvents {

    public static final ResourceLocation ARMOR_HELMET = ForbiddenArcanus.location("armor.helmet");
    public static final ResourceLocation ARMOR_CHESTPLATE = ForbiddenArcanus.location("armor.chestplate");
    public static final ResourceLocation ARMOR_LEGGINGS = ForbiddenArcanus.location("armor.leggings");
    public static final ResourceLocation ARMOR_BOOTS = ForbiddenArcanus.location("armor.boots");

    @SubscribeEvent
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.EXPERIENCE_BOTTLE, builder -> builder.set(ModDataComponents.ESSENCE_VALUE.get(), EssenceValue.of(EssenceType.EXPERIENCE, 15)));
    }

    @SubscribeEvent
    public static void modifyAttributes(ItemAttributeModifierEvent event) {
        if (event.getItemStack().is(ModItems.DRACO_ARCANUS_HELMET)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_HELMET, 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD);
        } else if (event.getItemStack().is(ModItems.DRACO_ARCANUS_CHESTPLATE)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_CHESTPLATE, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST);
        } else if (event.getItemStack().is(ModItems.DRACO_ARCANUS_LEGGINGS)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_LEGGINGS, 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.LEGS);
        } else if (event.getItemStack().is(ModItems.DRACO_ARCANUS_BOOTS)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_BOOTS, 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET);
        } else if (event.getItemStack().is(ModItems.TYR_HELMET)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_HELMET, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD);
        } else if (event.getItemStack().is(ModItems.TYR_CHESTPLATE)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_CHESTPLATE, 4, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.CHEST);
        } else if (event.getItemStack().is(ModItems.TYR_LEGGINGS)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_LEGGINGS, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.LEGS);
        } else if (event.getItemStack().is(ModItems.TYR_BOOTS)) {
            event.addModifier(FAAttributes.AUREAL_REGENERATION, new AttributeModifier(ARMOR_BOOTS, 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET);
        }
    }
}
