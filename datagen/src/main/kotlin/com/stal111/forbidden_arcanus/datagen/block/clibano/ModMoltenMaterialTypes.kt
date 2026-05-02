package com.stal111.forbidden_arcanus.datagen.block.clibano

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.BuiltinMoltenMaterialTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.valhelsia.dataforge.RegistryDataProvider

object ModMoltenMaterialTypes : RegistryDataProvider<MoltenMaterialType> {
    override fun bootstrap(context: BootstrapContext<MoltenMaterialType>) {
        context.register(
            BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL,
            MoltenMaterialType(ItemStackTemplate(ModItems.ARCANE_CRYSTAL.get()))
        )
        context.register(
            BuiltinMoltenMaterialTypes.RUNE,
            MoltenMaterialType(ItemStackTemplate(ModItems.RUNE.get()))
        )
        context.register(
            BuiltinMoltenMaterialTypes.COAL,
            MoltenMaterialType(ItemStackTemplate(Items.COAL))
        )
        context.register(
            BuiltinMoltenMaterialTypes.IRON,
            MoltenMaterialType(ItemStackTemplate(Items.IRON_INGOT))
        )
        context.register(
            BuiltinMoltenMaterialTypes.GOLD,
            MoltenMaterialType(ItemStackTemplate(Items.GOLD_INGOT))
        )
        context.register(
            BuiltinMoltenMaterialTypes.COPPER,
            MoltenMaterialType(ItemStackTemplate(Items.COPPER_INGOT))
        )
        context.register(
            BuiltinMoltenMaterialTypes.LAPIS_LAZULI,
            MoltenMaterialType(ItemStackTemplate(Items.LAPIS_LAZULI))
        )
        context.register(
            BuiltinMoltenMaterialTypes.DIAMOND,
            MoltenMaterialType(ItemStackTemplate(Items.DIAMOND))
        )
        context.register(
            BuiltinMoltenMaterialTypes.EMERALD,
            MoltenMaterialType(ItemStackTemplate(Items.EMERALD))
        )
    }
}