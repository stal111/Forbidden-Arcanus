package com.stal111.forbidden_arcanus.common.item.equipment;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class FAEquipmentAssets {

    private static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> DRACO_ARCANUS = createId("draco_arcanus");
    public static final ResourceKey<EquipmentAsset> TYR = createId("tyr");

    private static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(ROOT_ID, ForbiddenArcanus.identifier(name));
    }
}
