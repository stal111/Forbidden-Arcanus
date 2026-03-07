package com.stal111.forbidden_arcanus.datagen.item

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.item.wand.BuiltinWandMaterials
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial
import com.stal111.forbidden_arcanus.common.item.wand.WandPart
import net.minecraft.data.worldgen.BootstrapContext
import net.valhelsia.dataforge.RegistryDataProvider

object WandMaterialProvider : RegistryDataProvider<WandMaterial> {

    override fun bootstrap(context: BootstrapContext<WandMaterial>) {
        context.register(
            BuiltinWandMaterials.AMETHYST,
            createPommel("amethyst", projectileSpeed = 0.5F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.ARCANE_CRYSTAL,
            createPommel("arcane_crystal", projectileSpeed = 0.2F, accuracy = 0.2F)
        )
        context.register(
            BuiltinWandMaterials.CORRUPTED_ARCANE_CRYSTAL,
            createPommel("corrupted_arcane_crystal", damage = 1.0F, projectileSpeed = 0.3F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.DIAMOND,
            createPommel("diamond", projectileSpeed = 0.3F, accuracy = 0.2F)
        )
        context.register(
            BuiltinWandMaterials.ECHO_SHARD,
            createPommel("echo_shard", damage = 3.0F, projectileSpeed = 0.6F, accuracy = 0.4F)
        )
        context.register(
            BuiltinWandMaterials.EMERALD,
            createPommel("emerald", projectileSpeed = 0.2F, accuracy = 0.1F)
        )
        context.register(
            BuiltinWandMaterials.ENDER_PEARL,
            createPommel("ender_pearl", projectileSpeed = 0.5F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.LAPIS_LAZULI,
            createPommel("lapis_lazuli", projectileSpeed = 0.4F, accuracy = 0.2F)
        )
        context.register(
            BuiltinWandMaterials.NETHER_STAR,
            createPommel("nether_star", damage = 5.0F, projectileSpeed = -0.2F, accuracy = -0.3F)
        )
        context.register(
            BuiltinWandMaterials.PRISMARINE,
            createPommel("prismarine", projectileSpeed = 0.2F, accuracy = 0.1F)
        )
        context.register(
            BuiltinWandMaterials.QUARTZ,
            createPommel("quartz", projectileSpeed = 0.2F, accuracy = 0.2F)
        )
        context.register(
            BuiltinWandMaterials.RESIN,
            createPommel("resin", projectileSpeed = 0.4F, accuracy = -0.1F)
        )
        context.register(
            BuiltinWandMaterials.RUNE,
            createPommel("rune", projectileSpeed = 0.1F, accuracy = 0.3F)
        )

        context.register(
            BuiltinWandMaterials.ALCHEMICAL_SILVER,
            createTransition("alchemical_silver", damage = 3.5F, projectileSpeed = 0.1F, accuracy = 0.1F)
        )
        context.register(
            BuiltinWandMaterials.COPPER,
            createTransition("copper", damage = 2.0F, projectileSpeed = 0.1F, accuracy = 0.2F)
        )
        context.register(
            BuiltinWandMaterials.DEORUM,
            createTransition("deorum", damage = 3.5F, projectileSpeed = 0.1F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.GOLD,
            createTransition("gold", damage = 3.0F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.IRON,
            createTransition("iron", damage = 3.5F, accuracy = 0.1F)
        )
        context.register(
            BuiltinWandMaterials.NETHERITE,
            createTransition("netherite", damage = 5.0F, projectileSpeed = -0.1F, accuracy = 0.3F)
        )
        context.register(
            BuiltinWandMaterials.OBSIDIANSTEEL,
            createTransition("obsidiansteel", damage = 4.0F, projectileSpeed = 0.3F, accuracy = 0.4F)
        )
        context.register(
            BuiltinWandMaterials.STELLARITE,
            createTransition("stellarite", damage = 6.5F, accuracy = 0.3F)
        )
    }

    private fun createPommel(
        texture: String,
        damage: Float = 0.0F,
        projectileSpeed: Float = 0.0F,
        accuracy: Float = 0.0F
    ) = WandMaterial(
        WandPart.POMMEL,
        ForbiddenArcanus.identifier("item/wand/pommel/$texture"),
        damage,
        projectileSpeed,
        accuracy
    )

    private fun createTransition(
        texture: String,
        damage: Float = 0.0F,
        projectileSpeed: Float = 0.0F,
        accuracy: Float = 0.0F
    ) = WandMaterial(
        WandPart.TRANSITION,
        ForbiddenArcanus.identifier("item/wand/transition/$texture"),
        damage,
        projectileSpeed,
        accuracy
    )
}