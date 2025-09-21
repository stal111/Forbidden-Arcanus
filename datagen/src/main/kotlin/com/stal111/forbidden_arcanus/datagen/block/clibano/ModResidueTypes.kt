package com.stal111.forbidden_arcanus.datagen.block.clibano

import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.BuiltInResidueTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.valhelsia.dataforge.RegistryDataProvider

object ModResidueTypes : RegistryDataProvider<ResidueType> {
    override fun bootstrap(context: BootstrapContext<ResidueType>) {
        context.register(
            BuiltInResidueTypes.ARCANE_CRYSTAL,
            ResidueType.withDefaultKey("arcane_crystal", 9, ItemStack(ModBlocks.ARCANE_CRYSTAL_BLOCK.get()))
        )
        context.register(
            BuiltInResidueTypes.RUNE,
            ResidueType.withDefaultKey("rune", 9, ItemStack(ModBlocks.RUNE_BLOCK.get()))
        )
        context.register(
            BuiltInResidueTypes.COAL,
            ResidueType.withDefaultKey("coal", 9, Items.COAL_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.IRON,
            ResidueType.withDefaultKey("iron", 9, Items.IRON_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.GOLD,
            ResidueType.withDefaultKey("gold", 9, Items.GOLD_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.COPPER,
            ResidueType.withDefaultKey("copper", 9, Items.COPPER_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.LAPIS_LAZULI,
            ResidueType.withDefaultKey("lapis_lazuli", 9, Items.LAPIS_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.DIAMOND,
            ResidueType.withDefaultKey("diamond", 9, Items.DIAMOND_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.EMERALD,
            ResidueType.withDefaultKey("emerald", 9, Items.EMERALD_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.NETHERITE,
            ResidueType.withDefaultKey("netherite", 9, Items.NETHERITE_BLOCK.defaultInstance)
        )
        context.register(
            BuiltInResidueTypes.DEORUM,
            ResidueType.withDefaultKey("deorum", 1, ItemStack(ModItems.DEORUM_NUGGET.get(), 2))
        )
    }
}
