package com.stal111.forbidden_arcanus.datagen.item

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.item.wand.BuiltinWandMaterials
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial
import com.stal111.forbidden_arcanus.common.item.wand.WandPart
import net.minecraft.data.worldgen.BootstrapContext
import net.valhelsia.dataforge.RegistryDataProvider

object WandMaterialProvider : RegistryDataProvider<WandMaterial> {

    override fun bootstrap(context: BootstrapContext<WandMaterial>) {
        context.register(BuiltinWandMaterials.AMETHYST, createPommel("amethyst"))
        context.register(BuiltinWandMaterials.ARCANE_CRYSTAL, createPommel("arcane_crystal"))
        context.register(BuiltinWandMaterials.CORRUPTED_ARCANE_CRYSTAL, createPommel("corrupted_arcane_crystal"))
        context.register(BuiltinWandMaterials.DIAMOND, createPommel("diamond"))
        context.register(BuiltinWandMaterials.ECHO_SHARD, createPommel("echo_shard"))
        context.register(BuiltinWandMaterials.EMERALD, createPommel("emerald"))
        context.register(BuiltinWandMaterials.ENDER_PEARL, createPommel("ender_pearl"))
        context.register(BuiltinWandMaterials.LAPIS_LAZULI, createPommel("lapis_lazuli"))
        context.register(BuiltinWandMaterials.NETHER_STAR, createPommel("nether_star"))
        context.register(BuiltinWandMaterials.PRISMARINE, createPommel("prismarine"))
        context.register(BuiltinWandMaterials.QUARTZ, createPommel("quartz"))
        context.register(BuiltinWandMaterials.RESIN, createPommel("resin"))
        context.register(BuiltinWandMaterials.RUNE, createPommel("rune"))

        context.register(BuiltinWandMaterials.ALCHEMICAL_SILVER, createTransition("alchemical_silver"))
        context.register(BuiltinWandMaterials.COPPER, createTransition("copper"))
        context.register(BuiltinWandMaterials.DEORUM, createTransition("deorum"))
        context.register(BuiltinWandMaterials.GOLD, createTransition("gold"))
        context.register(BuiltinWandMaterials.IRON, createTransition("iron"))
        context.register(BuiltinWandMaterials.NETHERITE, createTransition("netherite"))
        context.register(BuiltinWandMaterials.OBSIDIANSTEEL, createTransition("obsidiansteel"))
        context.register(BuiltinWandMaterials.STELLARITE, createTransition("stellarite"))
    }

    private fun createPommel(texture: String): WandMaterial {
        return WandMaterial(WandPart.POMMEL, ForbiddenArcanus.identifier("item/wand/pommel/$texture"))
    }

    private fun createTransition(texture: String): WandMaterial {
        return WandMaterial(WandPart.TRANSITION, ForbiddenArcanus.identifier("item/wand/transition/$texture"))
    }
}