package com.stal111.forbidden_arcanus.datagen.block.clibano

import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.BuiltInResidueTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.valhelsia.dataforge.RegistryDataProvider

object ModResidueTypes : RegistryDataProvider<ResidueType> {
    override fun bootstrap(context: BootstrapContext<ResidueType>) {
        context.register(
            BuiltInResidueTypes.ARCANE_CRYSTAL,
            ResidueType.withDefaultKey("arcane_crystal", 9, ItemStackTemplate(ModBlocks.ARCANE_CRYSTAL_BLOCK.get().asItem()))
        )
        context.register(
            BuiltInResidueTypes.RUNE,
            ResidueType.withDefaultKey("rune", 9, ItemStackTemplate(ModBlocks.RUNE_BLOCK.get().asItem()))
        )
        context.register(
            BuiltInResidueTypes.COAL,
            ResidueType.withDefaultKey("coal", 9, ItemStackTemplate(Items.COAL_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.IRON,
            ResidueType.withDefaultKey("iron", 9, ItemStackTemplate(Items.IRON_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.GOLD,
            ResidueType.withDefaultKey("gold", 9, ItemStackTemplate(Items.GOLD_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.COPPER,
            ResidueType.withDefaultKey("copper", 9, ItemStackTemplate(Items.COPPER_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.LAPIS_LAZULI,
            ResidueType.withDefaultKey("lapis_lazuli", 9, ItemStackTemplate(Items.LAPIS_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.DIAMOND,
            ResidueType.withDefaultKey("diamond", 9, ItemStackTemplate(Items.DIAMOND_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.EMERALD,
            ResidueType.withDefaultKey("emerald", 9, ItemStackTemplate(Items.EMERALD_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.NETHERITE,
            ResidueType.withDefaultKey("netherite", 9, ItemStackTemplate(Items.NETHERITE_BLOCK))
        )
        context.register(
            BuiltInResidueTypes.DEORUM,
            ResidueType.withDefaultKey("deorum", 1, ItemStackTemplate(ModItems.DEORUM_NUGGET.get()))
        )
    }
}
