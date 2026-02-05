package com.stal111.forbidden_arcanus.datagen.item

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.item.wand.BuiltinWandMaterials
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial
import com.stal111.forbidden_arcanus.common.item.wand.WandPart
import net.minecraft.data.worldgen.BootstrapContext
import net.valhelsia.dataforge.RegistryDataProvider

object WandMaterialProvider : RegistryDataProvider<WandMaterial> {

    override fun bootstrap(context: BootstrapContext<WandMaterial>) {
        context.register(BuiltinWandMaterials.AMETHYST, createPommel("amethyst", 0.0F))
        context.register(BuiltinWandMaterials.ARCANE_CRYSTAL, createPommel("arcane_crystal", 0.0F))
        context.register(BuiltinWandMaterials.CORRUPTED_ARCANE_CRYSTAL, createPommel("corrupted_arcane_crystal", 1.0F))
        context.register(BuiltinWandMaterials.DIAMOND, createPommel("diamond", 0.0F))
        context.register(BuiltinWandMaterials.ECHO_SHARD, createPommel("echo_shard", 3.0F))
        context.register(BuiltinWandMaterials.EMERALD, createPommel("emerald", 0.0F))
        context.register(BuiltinWandMaterials.ENDER_PEARL, createPommel("ender_pearl", 0.0F))
        context.register(BuiltinWandMaterials.LAPIS_LAZULI, createPommel("lapis_lazuli", 0.0F))
        context.register(BuiltinWandMaterials.NETHER_STAR, createPommel("nether_star", 5.0F))
        context.register(BuiltinWandMaterials.PRISMARINE, createPommel("prismarine", 0.0F))
        context.register(BuiltinWandMaterials.QUARTZ, createPommel("quartz", 0.0F))
        context.register(BuiltinWandMaterials.RESIN, createPommel("resin", 0.0F))
        context.register(BuiltinWandMaterials.RUNE, createPommel("rune", 0.0F))

        context.register(BuiltinWandMaterials.ALCHEMICAL_SILVER, createTransition("alchemical_silver", 3.5F))
        context.register(BuiltinWandMaterials.COPPER, createTransition("copper", 2.0F))
        context.register(BuiltinWandMaterials.DEORUM, createTransition("deorum", 3.5F))
        context.register(BuiltinWandMaterials.GOLD, createTransition("gold", 3.0F))
        context.register(BuiltinWandMaterials.IRON, createTransition("iron", 3.5F))
        context.register(BuiltinWandMaterials.NETHERITE, createTransition("netherite", 5.0F))
        context.register(BuiltinWandMaterials.OBSIDIANSTEEL, createTransition("obsidiansteel", 4.0F))
        context.register(BuiltinWandMaterials.STELLARITE, createTransition("stellarite", 6.5F))
    }

    private fun createPommel(texture: String, damage: Float): WandMaterial {
        return WandMaterial(WandPart.POMMEL, ForbiddenArcanus.identifier("item/wand/pommel/$texture"), damage)
    }

    private fun createTransition(texture: String, damage: Float): WandMaterial {
        return WandMaterial(WandPart.TRANSITION, ForbiddenArcanus.identifier("item/wand/transition/$texture"), damage)
    }
}