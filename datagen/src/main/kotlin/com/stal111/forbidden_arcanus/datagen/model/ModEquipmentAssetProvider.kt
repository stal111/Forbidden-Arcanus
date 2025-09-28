package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.common.item.equipment.FAEquipmentAssets
import net.minecraft.client.data.models.EquipmentAssetProvider
import net.minecraft.client.resources.model.EquipmentClientInfo
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.equipment.EquipmentAsset
import net.valhelsia.dataforge.DataProviderContext
import java.util.function.BiConsumer

class ModEquipmentAssetProvider(context: DataProviderContext) : EquipmentAssetProvider(context.packOutput) {

    override fun registerModels(output: BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo>) {
        output.accept(FAEquipmentAssets.DRACO_ARCANUS, onlyHumanoid("forbidden_arcanus:draco_arcanus"))
        output.accept(FAEquipmentAssets.TYR, onlyHumanoid("forbidden_arcanus:tyr"))
    }
}
