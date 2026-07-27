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
            MoltenMaterialType(ItemStackTemplate(ModItems.ARCANE_CRYSTAL_DUST.get()), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.RUNE,
            MoltenMaterialType(ItemStackTemplate(ModItems.RUNE.get()), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.COAL,
            MoltenMaterialType(ItemStackTemplate(Items.COAL), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.IRON,
            MoltenMaterialType(ItemStackTemplate(Items.IRON_INGOT), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.GOLD,
            MoltenMaterialType(ItemStackTemplate(Items.GOLD_INGOT), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.COPPER,
            MoltenMaterialType(ItemStackTemplate(Items.COPPER_INGOT), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.LAPIS_LAZULI,
            MoltenMaterialType(ItemStackTemplate(Items.LAPIS_LAZULI), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.DIAMOND,
            MoltenMaterialType(ItemStackTemplate(Items.DIAMOND), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.EMERALD,
            MoltenMaterialType(ItemStackTemplate(Items.EMERALD), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.NETHERITE,
            MoltenMaterialType(ItemStackTemplate(Items.NETHERITE_SCRAP), 256 * 9)
        )
        context.register(
            BuiltinMoltenMaterialTypes.ASTERITE,
            MoltenMaterialType(ItemStackTemplate(ModItems.ASTERITE_INGOT.get()), 256 * 9)
        )
    }
}